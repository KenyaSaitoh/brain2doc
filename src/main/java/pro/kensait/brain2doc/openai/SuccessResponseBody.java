package pro.kensait.brain2doc.openai;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessResponseBody {
    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "object")
    private String object;

    @JsonProperty(value = "created")
    private Long created;

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "choices")
    private List<Choice> choices;

    @JsonProperty(value = "usage")
    private Usage usage;

    @JsonProperty(value = "error")
    private Error error;

    @JsonProperty(value = "system_fingerprint")
    private String systemFingerprint;

    public SuccessResponseBody() {}

    public SuccessResponseBody(String id, String object, Long created, String model,
            List<Choice> choices, Usage usage, Error error, String systemFingerprint) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
        this.usage = usage;
        this.error = error;
        this.systemFingerprint = systemFingerprint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getSystemFingerprint() {
        return systemFingerprint;
    }

    public void setSystemFingerprint(String systemFingerprint) {
        this.systemFingerprint = systemFingerprint;
    }

    @Override
    public String toString() {
        return "SuccessResponseBody [id=" + id + ", object=" + object + ", created="
                + created + ", model=" + model + ", choices=" + choices + ", usage="
                + usage + ", error=" + error + ", systemFingerprint=" + systemFingerprint
                + "]";
    }
}
