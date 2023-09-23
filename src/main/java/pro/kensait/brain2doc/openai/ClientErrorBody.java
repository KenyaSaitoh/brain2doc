package pro.kensait.brain2doc.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientErrorBody {
    @JsonProperty(value = "error")
    private Error error;

    public ClientErrorBody() {}

    public ClientErrorBody(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ClientErrorBody [error=" + error + "]";
    }
}