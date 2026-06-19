package gesimmo.nekaso.exception;

public class PhotoCountExceededException extends RuntimeException {
    public PhotoCountExceededException(String message) {
        super(message);
    }
}