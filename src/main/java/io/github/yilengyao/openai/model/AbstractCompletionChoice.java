package io.github.yilengyao.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public abstract class AbstractCompletionChoice {
  protected Integer index;
  @JsonProperty("finish_reason")
  protected String finishReason;
}
