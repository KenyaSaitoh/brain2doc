package pro.kensait.brain2doc.openai;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestBody {
    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "messages")
    private List<Message> messages;

    @JsonProperty(value = "temperature")
    private Float temperature;

    public RequestBody() {}

    public RequestBody(String model, List<Message> messages, Float temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
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

    @Override
    public String toString() {
        return "RequestBody [model=" + model + ", messages=" + messages + ", temperature="
                + temperature + "]";
    }
}
