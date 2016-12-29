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

import be.deltis.documentarchiver.Processor;
import be.deltis.documentarchiver.context.Context;
import be.deltis.documentarchiver.context.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.testng.Assert.assertTrue;

/**
 * Created by benoit on 29/12/16 - 11:44.
 */
@Test
public class SimpleCopyProcessorTest {

    private final String PREFIX = "UnitTestDocs" + this.getClass().getSimpleName();
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Processor processor ;

    public void copy() throws IOException {
        Path targetDirectory = Files.createTempDirectory(PREFIX);
        logger.debug("Creating target dir {}", targetDirectory);

        processor = new SimpleCopyProcessor(targetDirectory);

        Path sourceDirectory = Files.createTempDirectory(PREFIX);
        logger.debug("Creating source dir {}", sourceDirectory);


        Context context = new Context(Source.SCANNER, sourceDirectory);

        Path file = Files.createTempFile(sourceDirectory, PREFIX, ".pdf");
        logger.debug("Creating file {}", file);

        processor.processFile(file.getFileName(), context);

        assertTrue(Files.exists(context.getDirectory().resolve(file.getFileName())));
        assertTrue(Files.exists(targetDirectory.resolve(file.getFileName())));

        // Clean-up
        Files.deleteIfExists(file);
        Files.deleteIfExists(sourceDirectory);
        Files.deleteIfExists(targetDirectory.resolve(file.getFileName()));
        Files.deleteIfExists(targetDirectory);
    }
}
