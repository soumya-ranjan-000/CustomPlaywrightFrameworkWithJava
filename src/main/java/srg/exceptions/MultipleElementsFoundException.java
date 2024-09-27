package srg.exceptions;

public class MultipleElementsFoundException extends Exception {
    // Default constructor
    public MultipleElementsFoundException() {
        super();
    }

    // Constructor that accepts a message
    public MultipleElementsFoundException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public MultipleElementsFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public MultipleElementsFoundException(Throwable cause) {
        super(cause);
    }
}
