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
import be.deltis.documentarchiver.Source;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

/**
 * Created by benoit on 18/03/14.
 */

@Test
public class WatcherImplTest {

    private final String PREFIX = "UnitTestDocs" + this.getClass().getSimpleName();
    private Logger logger = LoggerFactory.getLogger(getClass());

    @InjectMocks
    private WatcherImpl watcher = new WatcherImpl();

    @Mock
    private Processor processor ;

    @BeforeMethod
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
    }

    public void takeOneFile() throws Exception {
        Path testDir = Files.createTempDirectory(PREFIX);
        logger.debug("Creating dir {}", testDir);

        watcher.setContexts(Collections.singletonList(new Context(Source.EMAIL, testDir.toString())));
        watcher.setSuffix(".pdf");


        Path testFile = Files.createTempFile(testDir, PREFIX, ".pdf");
        logger.debug("Creating file {}", testFile);

        watcher.takeOneFile();
        Mockito.verify(processor).processFile(Mockito.anyString());

        Files.deleteIfExists(testFile);
        Files.deleteIfExists(testDir);
    }
}
