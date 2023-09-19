package pro.kensait.brain2doc.openai;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usage {
    @JsonProperty(value = "prompt_tokens")
    private Integer promptTokens;

    @JsonProperty(value = "completion_tokens")
    private Integer completionTokens;

    @JsonProperty(value = "total_tokens")
    private Integer totalTokens;

    // デフォルトコンストラクタ
    public Usage() {}

    // 全フィールドの値を引数に取るコンストラクタ
    public Usage(Integer promptTokens, Integer completionTokens, Integer totalTokens) {
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }

    // getters and setters
    public Integer getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(Integer promptTokens) {
        this.promptTokens = promptTokens;
    }

    public Integer getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(Integer completionTokens) {
        this.completionTokens = completionTokens;
    }

    public Integer getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(Integer totalTokens) {
        this.totalTokens = totalTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usage usage = (Usage) o;
        return Objects.equals(promptTokens, usage.promptTokens) &&
               Objects.equals(completionTokens, usage.completionTokens) &&
               Objects.equals(totalTokens, usage.totalTokens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promptTokens, completionTokens, totalTokens);
    }

    @Override
    public String toString() {
        return "Usage{" +
                "promptTokens=" + promptTokens +
                ", completionTokens=" + completionTokens +
                ", totalTokens=" + totalTokens +
                '}';
    }
}