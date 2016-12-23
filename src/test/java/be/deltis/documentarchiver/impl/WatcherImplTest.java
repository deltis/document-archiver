package be.deltis.documentarchiver.impl;

import be.deltis.documentarchiver.Context;
import be.deltis.documentarchiver.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

/**
 * Created by benoit on 18/03/14.
 */

@Test
public class WatcherImplTest {

    private final String PREFIX = "AccountingDocs" + this.getClass().getSimpleName();
    private Logger logger = LoggerFactory.getLogger(getClass());
    private WatcherImpl watcher = new WatcherImpl();

    public void startProcessing() throws Exception {
        Path testDir = Files.createTempDirectory(PREFIX);
        logger.debug("Creating dir {}", testDir);

        watcher.setContexts(Collections.singletonList(new Context(Source.EMAIL, testDir.toString())));
        watcher.setSuffix(".pdf");

        Runnable createFile = () -> {
            try {
                Thread.sleep(1000L);
                Path testFile = Files.createTempFile(testDir, PREFIX, ".pdf");
                logger.debug("Creating file {}", testFile);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create file", e);
            }
        } ;
        new Thread(createFile).start();

        watcher.startProcessing();

        // Files.deleteIfExists(testFile);
        Files.deleteIfExists(testDir);
    }
}
