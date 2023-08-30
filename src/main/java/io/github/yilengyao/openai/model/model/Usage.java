package io.github.yilengyao.openai.model.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Usage {
  @JsonProperty("prompt_tokens")
  Integer promptTokens;
  @JsonProperty("completion_tokens")
  Integer completionTokens;
  @JsonProperty("total_tokens")
  Integer totalTokens;

  public io.github.yilengyao.openai.graphql.generated.types.Usage toGraphQl() {
    var output = io.github.yilengyao.openai.graphql.generated.types.Usage.newBuilder();
    if (this.getPromptTokens() != null) {
      output.prompt_tokens(this.getPromptTokens());
    }
    if (this.getCompletionTokens() != null) {
      output.completion_tokens(this.getCompletionTokens());
    }
    if (this.getTotalTokens() != null) {
      output.total_tokens(this.getTotalTokens());
    }
    return output.build();
  }

}
