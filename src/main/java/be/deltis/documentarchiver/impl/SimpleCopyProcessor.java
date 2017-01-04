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
package be.deltis.documentarchiver.impl;

import be.deltis.documentarchiver.Processor;
import be.deltis.documentarchiver.context.Context;
import be.deltis.documentarchiver.exception.DocArchiverException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleCopyProcessor implements Processor {

    private Path targetDirectory ;

    public SimpleCopyProcessor(Path targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    @Override
    public void processFile(Path filename, Context context) {
        try {
            Path source = context.getDirectory().resolve(filename);
            Files.copy(source, targetDirectory.resolve(source.getFileName()));
        } catch (IOException ioe) {
            throw new DocArchiverException(String.format("Failed to move file %s", filename), ioe);
        }
    }
}
