package pro.kensait.brain2doc.exception;

public class OpenAIRetryCountOverException extends RuntimeException {

    public OpenAIRetryCountOverException() {
        super();
    }

    public OpenAIRetryCountOverException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OpenAIRetryCountOverException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenAIRetryCountOverException(String message) {
        super(message);
    }

    public OpenAIRetryCountOverException(Throwable cause) {
        super(cause);
    }
}
