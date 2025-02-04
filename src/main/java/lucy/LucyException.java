package lucy;

/**
 * Represents a specific exception.
 */
public class LucyException extends Exception {
    /**
     * Constructs a LucyException with a message.
     * @param message The error message.
     */
    public LucyException(String message) {
        super(message);
    }
}
