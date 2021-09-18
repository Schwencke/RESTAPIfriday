package errorhandling;

public class MissingFieldsException extends Throwable{
    public MissingFieldsException(String message) {
        super(message);
    }
}
