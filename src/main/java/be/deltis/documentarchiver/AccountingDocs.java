package be.deltis.documentarchiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created by benoit on 17/03/14.
 */
public class AccountingDocs {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String... args) {
        new AccountingDocs().start();
    }

    private void start() {
        try {
            watch();
        } catch (IOException ioe) {
            logger.error("Failed to start watching", ioe);
        }
    }

    private void watch() throws IOException {
    }
}
