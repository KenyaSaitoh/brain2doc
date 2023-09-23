package pro.kensait.brain2doc.exception;

import pro.kensait.brain2doc.openai.ClientErrorBody;

public class OpenAITokenLimitOverException extends RuntimeException {
    private ClientErrorBody clientErrorBody;
    private int tokenCount;
    
    public OpenAITokenLimitOverException() {
        super();
    }

    public OpenAITokenLimitOverException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OpenAITokenLimitOverException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenAITokenLimitOverException(String message) {
        super(message);
    }

    public OpenAITokenLimitOverException(Throwable cause) {
        super(cause);
    }

    public OpenAITokenLimitOverException(ClientErrorBody clientErrorBody) {
        this.clientErrorBody = clientErrorBody;
    }

    public OpenAITokenLimitOverException(ClientErrorBody clientErrorBody,
            int tokenCount) {
        this.clientErrorBody = clientErrorBody;
        this.tokenCount = tokenCount;
    }

    public ClientErrorBody getClientErrorBody() {
        return clientErrorBody;
    }

    public int getTokenCount() {
        return tokenCount;
    }
}