package pro.kensait.brain2doc.exception;

public class OpenAITimeoutException extends RuntimeException {

    public OpenAITimeoutException() {
        super();
    }

    public OpenAITimeoutException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OpenAITimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenAITimeoutException(String message) {
        super(message);
    }

    public OpenAITimeoutException(Throwable cause) {
        super(cause);
    }
}
