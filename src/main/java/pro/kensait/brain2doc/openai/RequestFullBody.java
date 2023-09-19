package pro.kensait.brain2doc.openai;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestFullBody {
    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "messages")
    private List<Message> messages;

    @JsonProperty(value = "temperature")
    private Float temperature;

    @JsonProperty(value = "top_p")
    private Float topP;

    @JsonProperty(value = "n")
    private Integer n;

    @JsonProperty(value = "stream")
    private Boolean stream;

    @JsonProperty(value = "stop")
    private List<String> stop;

    @JsonProperty(value = "max_tokens")
    private Integer maxTokens;

    @JsonProperty(value = "presence_penalty")
    private Float presencePenalty;

    @JsonProperty(value = "frequency_penalty")
    private Float frequencyPenalty;

    @JsonProperty(value = "logit_bias")
    private Map<Object, Object> logitBias;

    public RequestFullBody() {}

    public RequestFullBody(String model, List<Message> messages, Float temperature, Float topP, Integer n, Boolean stream,
                           List<String> stop, Integer maxTokens, Float presencePenalty, Float frequencyPenalty, Map<Object, Object> logitBias) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.topP = topP;
        this.n = n;
        this.stream = stream;
        this.stop = stop;
        this.maxTokens = maxTokens;
        this.presencePenalty = presencePenalty;
        this.frequencyPenalty = frequencyPenalty;
        this.logitBias = logitBias;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTopP() {
        return topP;
    }

    public void setTopP(Float topP) {
        this.topP = topP;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public List<String> getStop() {
        return stop;
    }

    public void setStop(List<String> stop) {
        this.stop = stop;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Float getPresencePenalty() {
        return presencePenalty;
    }

    public void setPresencePenalty(Float presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public Float getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(Float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public Map<Object, Object> getLogitBias() {
        return logitBias;
    }

    public void setLogitBias(Map<Object, Object> logitBias) {
        this.logitBias = logitBias;
    }

    @Override
    public String toString() {
        return "RequestFullBody [model=" + model + ", messages=" + messages
                + ", temperature=" + temperature + ", topP=" + topP + ", n=" + n
                + ", stream=" + stream + ", stop=" + stop + ", maxTokens=" + maxTokens
                + ", presencePenalty=" + presencePenalty + ", frequencyPenalty="
                + frequencyPenalty + ", logitBias=" + logitBias + "]";
    }
}
