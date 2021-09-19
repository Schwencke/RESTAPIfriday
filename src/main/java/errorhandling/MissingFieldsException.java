package errorhandling;

public class MissingFieldsException extends Throwable{
    int errCode;

    public MissingFieldsException(String message) {
        super(message);
        errCode = 404;
    }

    public int getErrCode() {
        return errCode;
    }
}
