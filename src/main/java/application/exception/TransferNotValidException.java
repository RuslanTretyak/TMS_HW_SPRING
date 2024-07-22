package application.exception;

public class TransferNotValidException extends RuntimeException {
    public TransferNotValidException(String message) {
        super(message);
    }
}
