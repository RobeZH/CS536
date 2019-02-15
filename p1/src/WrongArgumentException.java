/**
 * This exception is thrown when there is something wrong with the argument
 */
public class WrongArgumentException extends Exception {
    /**
     * The default constructor for WrongArgumentException
     */
    public WrongArgumentException() {
    }

    /**
     * The constructor for WrongArgumentException
     *
     * @param message the message you want to put
     */
    public WrongArgumentException(String message) {
        super(message);
    }
}
