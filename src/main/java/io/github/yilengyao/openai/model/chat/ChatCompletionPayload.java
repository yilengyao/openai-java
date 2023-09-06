package io.github.yilengyao.openai.model.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.yilengyao.openai.graphql.generated.types.ChatCompletionInput;
import io.github.yilengyao.openai.graphql.generated.types.LogitBiasInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatCompletionPayload {
  private String model;
  private List<Message> messages;
  private List<Function> functions;
  @JsonProperty("function_call")
  private String functionCall;
  private Double temperature;
  @JsonProperty("top_p")
  private Double topP;
  private Integer n;
  private Boolean stream;
  private List<String> stop;
  @JsonProperty("max_tokens")
  private Integer maxTokens;
  @JsonProperty("presence_penalty")
  private Double presencePenalty;
  @JsonProperty("frequency_penalty")
  private Double frequencyPenalty;
  @JsonProperty("logit_bias")
  private Map<String, Integer> logitBias;
  private String user;

  /**
   * @param input Input from graphql
   *
   * @return ChatCompletionPayload
   */
  public static ChatCompletionPayload fromGraphQl(ChatCompletionInput input) {
    var payload = new ChatCompletionPayload();
    payload.setModel(input.getModel());
    payload.setMessages(input.getMessages().stream().map(Message::fromGraphQl).collect(Collectors.toList()));

    if (input.getFunctions() != null) {
      payload.setFunctions(input.getFunctions().stream().map(Function::fromGraphQl).collect(Collectors.toList()));
    }
    if (input.getFunction_call() != null) {
      payload.setFunctionCall(input.getFunction_call());
    }
    if (input.getTemperature() != null) {
      payload.setTemperature(input.getTemperature());
    }
    if (input.getTop_p() != null) {
      payload.setTopP(input.getTop_p());
    }
    if (input.getN() != null) {
      payload.setN(input.getN());
    }
    if (input.getStream() != null) {
      payload.setStream(input.getStream());
    }
    if (input.getStop() != null) {
      payload.setStop(input.getStop());
    }
    if (input.getMax_tokens() != null) {
      payload.setMaxTokens(input.getMax_tokens());
    }
    if (input.getPresence_penalty() != null) {
      payload.setPresencePenalty(input.getPresence_penalty());
    }
    if (input.getFrequency_penalty() != null) {
      payload.setFrequencyPenalty(input.getFrequency_penalty());
    }
    if (input.getLogit_bias() != null) {
      // Assuming LogitBiasInput can be converted directly or has a method like
      // `toJavaObject()`
      Map<String, Integer> logitBias = input.getLogit_bias().stream()
          .collect(Collectors.toMap(LogitBiasInput::getKey, LogitBiasInput::getValue));
      payload.setLogitBias(logitBias);
    }
    if (input.getUser() != null) {
      payload.setUser(input.getUser());
    }
    return payload;
  }

   /**
   * Retrieves the payload as a map of key-value pairs.
   *
   * @return A map containing the payload data.
   */
  public Map<String, Object> getPayload() {
    Map<String, Object> payloadMap = new HashMap<>();

    payloadMap.put("model", this.model);
    if (this.messages != null) {
      List<Map<String, Object>> messagesPayload = this.messages.stream()
          .map(Message::getPayload)
          .collect(Collectors.toList());
      payloadMap.put("messages", messagesPayload);
    }

    if (this.functions != null) {
      List<Map<String, Object>> functionsPayload = this.functions.stream()
          .map(Function::getPayload)
          .collect(Collectors.toList());
      payloadMap.put("functions", functionsPayload);
    }
    if (this.temperature != null) {
      payloadMap.put("temperature", this.temperature);
    }
    if (this.topP != null) {
      payloadMap.put("top_p", this.topP);
    }
    if (this.n != null) {
      payloadMap.put("n", this.n);
    }
    if (this.stream != null) {
      payloadMap.put("stream", this.stream);
    }
    if (this.stop != null) {
      payloadMap.put("stop", this.stop);
    }
    if (this.maxTokens != null) {
      payloadMap.put("max_tokens", this.maxTokens);
    }
    if (this.presencePenalty != null) {
      payloadMap.put("presence_penalty", this.presencePenalty);
    }
    if (this.frequencyPenalty != null) {
      payloadMap.put("frequency_penalty", this.frequencyPenalty);
    }
    if (this.logitBias != null) {
      payloadMap.put("logit_bias", this.logitBias);
    }
    if (this.user != null) {
      payloadMap.put("user", this.user);
    }

    return payloadMap;
  }

}
