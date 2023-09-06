package io.github.yilengyao.openai.model.chat;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.yilengyao.openai.graphql.generated.types.MessageInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {
  private String role;
  private String content;
  @JsonProperty("function_call")
  private FunctionCall functionCall;

  private String name;

  public static Message fromGraphQl(MessageInput input) {
    var message = new Message();
    message.setRole(input.getRole());
    if (input.getContent() != null) {
      message.setContent(input.getContent());
    }
    if (input.getName() != null) {
      message.setName(input.getName());
    }
    if (input.getFunction_call() != null) {
      message.setFunctionCall(FunctionCall.fromGraphQl(input.getFunction_call()));
    }
    return message;
  }

  public Map<String, Object> getPayload() {
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("role", this.getRole());
    if (this.content != null) {
      payloadMap.put("content", this.getContent());
    }
    if (this.name != null) {
      payloadMap.put("name", this.getName());
    }
    if (this.functionCall != null) {
      payloadMap.put("function_call", this.functionCall.getPayload());
    }
    return payloadMap;
  }
}
