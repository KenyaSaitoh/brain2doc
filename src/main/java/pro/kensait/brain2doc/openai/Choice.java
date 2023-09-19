package pro.kensait.brain2doc.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Choice {
    @JsonProperty(value = "index")
    private Integer index;

    @JsonProperty(value = "message")
    private Message message;

    @JsonProperty(value = "finish_reason")
    private String finishReason;

    public Choice() {
    }

    public Choice(Integer index, Message message, String finishReason) {
        this.index = index;
        this.message = message;
        this.finishReason = finishReason;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    @Override
    public String toString() {
        return "Choice [index=" + index + ", message=" + message + ", finishReason="
                + finishReason + "]";
    }
}
