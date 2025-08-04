package exceptions;

public class IndexingNotSupportedException extends RuntimeException {
    public IndexingNotSupportedException(String collection) {
        super(collection + " doesn't support indexing!");
    }
}