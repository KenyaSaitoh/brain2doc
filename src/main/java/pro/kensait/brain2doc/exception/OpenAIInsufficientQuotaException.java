package pro.kensait.brain2doc.exception;

import pro.kensait.brain2doc.openai.ClientErrorBody;

public class OpenAIInsufficientQuotaException extends OpenAIClientException {
    private ClientErrorBody clientErrorBody;
    
    public OpenAIInsufficientQuotaException() {
        super();
    }

    public OpenAIInsufficientQuotaException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OpenAIInsufficientQuotaException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenAIInsufficientQuotaException(String message) {
        super(message);
    }

    public OpenAIInsufficientQuotaException(Throwable cause) {
        super(cause);
    }

    public OpenAIInsufficientQuotaException(ClientErrorBody clientErrorBody) {
        this.clientErrorBody = clientErrorBody;
    }

    public ClientErrorBody getClientErrorBody() {
        return clientErrorBody;
    }
}