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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by benoit on 18/03/14.
 */

@Test
@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
public class WatcherImplTest {

    @Autowired
    @InjectMocks
    private WatcherImpl watcher;

    @Mock
    private Processor processor;

    @BeforeMethod
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
    }

    public void takeOneFile() throws Exception {
        Path testDir = FileHelper.createTempDirectory(this);

        watcher.setContexts(Collections.singletonList(new Context(Source.EMAIL, testDir)));
        watcher.setSuffix(".pdf");

        FileHelper.createTempFile(testDir, this);

        watcher.takeOneFile();
        verify(processor).processFile(any(Path.class), any(Context.class));

        FileHelper.deleteDir(testDir, this);
    }
}
