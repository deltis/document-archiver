/*
 *  Copyright 2016-2017 DELTIS Engineering sprl
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package be.deltis.documentarchiver.helper;

import be.deltis.documentarchiver.exception.DocArchiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by benoit on 29/12/16 - 14:52.
 */
public class FileHelper {

    public static Path createTempDirectory(Object context) {
        try {
            Path dir = Files.createTempDirectory("document-archiver-" + context.getClass().getSimpleName() + "-");
            logger(context).debug("Creating dir {}", dir);
            return dir;
        } catch (IOException ioe) {
            throw new DocArchiverException("Failed to create temp dir", ioe);
        }
    }

    public static Path createTempFile(Path dir, Object context) {
        try {
            Path file = Files.createTempFile(dir, "test-", ".pdf");
            logger(context).debug("Creating file {}", file);
            return file;
        } catch (IOException ioe) {
            throw new DocArchiverException("Failed to create temp file", ioe);
        }
    }

    public static void deleteDir(Path directoryToDelete, Object context) {
        try {
            for (Path p : Files.walk(directoryToDelete).
                    sorted((a, b) -> b.compareTo(a)). // reverse; files before dirs
                    toArray(Path[]::new)) {
                Files.delete(p);
                logger(context).debug("Deleted {}", p);
            }
        } catch (IOException ioe) {
            throw new DocArchiverException("Failed to delete dir", ioe);
        }
    }

    private static Logger logger(Object context) {
        Class clazz = context.getClass();
        return LoggerFactory.getLogger(clazz);
    }
}
