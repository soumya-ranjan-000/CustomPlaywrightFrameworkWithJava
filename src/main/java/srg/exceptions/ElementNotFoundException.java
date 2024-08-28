package srg.exceptions;

public class ElementNotFoundException extends Exception {
    // Default constructor
    public ElementNotFoundException() {
        super();
    }

    // Constructor that accepts a message
    public ElementNotFoundException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public ElementNotFoundException(Throwable cause) {
        super(cause);
    }

}
