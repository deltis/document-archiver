package be.deltis.documentarchiver;

import java.io.IOException;

/**
 * Created by benoit on 18/03/14.
 */
public interface Watcher {

    void startProcessing() throws IOException;
}
