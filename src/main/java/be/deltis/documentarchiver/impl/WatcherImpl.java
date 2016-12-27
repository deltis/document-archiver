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
    private Processor processor;
    private WatchService watcher ;

    private Map<Path, Context> contextMap = new HashMap<>();

    public WatcherImpl() {
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to create new watch service", ioe);
        }
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setContexts(List<Context> contexts) {
        for (Context context : contexts) {
            logger.info("Starting to watch directory {}", context.getDirectory());
            Path dirPath = FileSystems.getDefault().getPath(context.getDirectory());
            contextMap.put(dirPath, context);
            try {
                dirPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            } catch (IOException ioe) {
                RuntimeException re = new RuntimeException("Failed to register the directory", ioe);
                logger.error("Failed to register the directory", re);
                throw re;
            }
        }
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public boolean takeOneFile() {
        // wait for key to be signaled
        WatchKey key;
        try {
            key = watcher.take();
        } catch (InterruptedException x) {
            return false;
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
                processor.processFile(filename.toString());
            }
        }
        return key.reset();
    }
    @Override
    public void startProcessing() throws IOException {
        for (;;) {
            boolean valid = takeOneFile() ;
            if (!valid) {
                break ;
            }
        }
    }
}
