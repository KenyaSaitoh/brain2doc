package pro.kensait.brain2doc.exception;

public class RetryCountOverException extends RuntimeException {
    public RetryCountOverException() {
        super();
    }

    public RetryCountOverException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RetryCountOverException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryCountOverException(String message) {
        super(message);
    }

    public RetryCountOverException(Throwable cause) {
        super(cause);
    }
}
