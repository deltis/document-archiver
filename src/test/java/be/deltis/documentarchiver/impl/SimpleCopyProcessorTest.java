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
import be.deltis.documentarchiver.context.Source;
import be.deltis.documentarchiver.helper.FileHelper;
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

    public SimpleCopyProcessorTest() {
    }

    public void copy() throws IOException {
        Path targetDirectory = FileHelper.createTempDirectory(this);

        Processor processor = new SimpleCopyProcessor(targetDirectory);

        Path sourceDirectory = FileHelper.createTempDirectory(this);
        Context context = new Context(Source.SCANNER, sourceDirectory);

        Path file = FileHelper.createTempFile(sourceDirectory, this);

        processor.processFile(file.getFileName(), context);

        assertTrue(Files.exists(context.getDirectory().resolve(file.getFileName())));
        assertTrue(Files.exists(targetDirectory.resolve(file.getFileName())));

        // Clean-up
        FileHelper.deleteDir(sourceDirectory, this);
        FileHelper.deleteDir(targetDirectory, this);
    }
}
