package zbot.exceptions;

/**
 * Thrown when the ZBOT file cannot be loaded.
 * This exception is thrown when the system fails to read
 * the ZBOT file.
 * The exception message may provide further details on the cause of failure.
 */
public class ZBOTFileNotFoundException extends Exception {
    public ZBOTFileNotFoundException (String message) {
        super(message);
    }
}
