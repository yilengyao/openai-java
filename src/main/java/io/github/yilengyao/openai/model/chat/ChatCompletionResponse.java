package io.github.yilengyao.openai.model.chat;

import lombok.Data;

@Data
public abstract class ChatCompletionResponse {
  protected String id;
  protected String object;
  protected Long created;
  protected String model;
}
