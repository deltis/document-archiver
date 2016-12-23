package be.deltis.documentarchiver;

/**
 * Created by benoit on 17/07/14.
 */
public class Context {

    private Source source ;
    private String directory;

    public Context() {
    }

    public Context(Source source, String directory) {
        this.source = source;
        this.directory = directory;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
