package srg.exceptions;

public class InvalidPropertiesException extends Exception{
    public InvalidPropertiesException(){
        super();
    }

    public InvalidPropertiesException(String msg){
        super(msg);
    }

    public InvalidPropertiesException(String msg, Throwable cause){
        super(msg,cause);
    }

    public InvalidPropertiesException(Throwable cause){
        super(cause);
    }
}
