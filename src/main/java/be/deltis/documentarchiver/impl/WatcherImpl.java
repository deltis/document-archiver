/*
 * Copyright 2016 DELTIS Engineering sprl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.deltis.documentarchiver.impl;

import be.deltis.documentarchiver.Context;
import be.deltis.documentarchiver.Processor;
import be.deltis.documentarchiver.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

/**
 * Created by benoit on 18/03/14.
 */
public class WatcherImpl implements Watcher {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String suffix;
    private List<Context> contexts;
    private Processor processor;

    private Map<Path, Context> contextMap = new HashMap<>();

    public WatcherImpl() {
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }

    @Override
    public void startProcessing() throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();

        for (Context context : contexts) {
            logger.info("Starting to watch directory {}", context.getDirectory());
            Path dirPath = FileSystems.getDefault().getPath(context.getDirectory());
            contextMap.put(dirPath, context);

            try {
                dirPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            } catch (IOException ioe) {
                logger.error("Failed to register the directory", ioe);
                throw ioe;
            }
        }

        for (; ; ) {

            // wait for key to be signaled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }


            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // This key is registered only
                // for ENTRY_CREATE events,
                // but an OVERFLOW event can
                // occur regardless if events
                // are lost or discarded.
                if (kind == OVERFLOW) {
                    continue;
                }

                // The filename is the
                // context of the event.
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path filename = ev.context();
                if (filename.toString().endsWith(suffix)) {
                    Path dirPath = filename.getParent();
                    processor.processFile(contextMap.get(dirPath), filename.toString());
                }
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }

        }
    }
}
