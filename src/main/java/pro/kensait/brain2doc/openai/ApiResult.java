package pro.kensait.brain2doc.openai;

public class ApiResult {
    private final SuccessResponseBody responseBody;
    private final long interval;

    public ApiResult(SuccessResponseBody responseBody, long interval) {
        this.responseBody = responseBody;
        this.interval = interval;
    }

    public SuccessResponseBody getResponseBody() {
        return responseBody;
    }

    public long getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        return "ApiResult [responseBody=" + responseBody + ", interval=" + interval + "]";
    }
}
