package be.deltis.documentarchiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created by benoit on 17/07/14.
 */
@Test
public class LoggerTest {

    private Logger logger = LoggerFactory.getLogger(LoggerTest.class) ;

    public void info() {
        logger.info("Info:{}", logger.isInfoEnabled());
    }

    public void debug() {
        logger.debug("Debug:{}", logger.isDebugEnabled());
    }
}
