package io.github.yilengyao.openai.model.completion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.yilengyao.openai.graphql.generated.types.CompletionInput;
import io.github.yilengyao.openai.graphql.generated.types.LogitBiasInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the payload for a completion request.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompletionPayload {
  @JsonProperty("model")
  String model;
  @JsonProperty("prompt")
  String prompt;
  @JsonProperty("suffix")
  String suffix;
  @JsonProperty("max_tokens")
  Integer maxTokens;
  @JsonProperty("temperature")
  Double temperature;
  @JsonProperty("top_p")
  Double topP;
  @JsonProperty("n")
  Integer n;
  @JsonProperty("stream")
  Boolean stream;
  @JsonProperty("logprobs")
  Integer logprobs;
  @JsonProperty("echo")
  Boolean echo;
  @JsonProperty("stop")
  List<String> stop;
  @JsonProperty("presence_penalty")
  Double presencePenalty;
  @JsonProperty("frequency_penalty")
  Double frequencyPenalty;
  @JsonProperty("best_of")
  Integer bestOf;
  @JsonProperty("logit_bias")
  Map<String, Integer> logitBias;
  @JsonProperty("user")
  String user;

  /**
   * Converts a GraphQL input to a CompletionPayload instance.
   *
   * @param input The GraphQL input representation.
   * @return A CompletionPayload instance.
   */
  public static CompletionPayload fromGraphQl(CompletionInput input) {
    var payload = new CompletionPayload();
    payload.setModel(input.getModel());
    if (input.getPrompt() != null) {
      payload.setPrompt(input.getPrompt());
    }
    if (input.getSuffix() != null) {
      payload.setSuffix(input.getSuffix());
    }
    if (input.getMax_tokens() != null) {
      payload.setMaxTokens(input.getMax_tokens());
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
    if (input.getLogprobs() != null) {
      payload.setLogprobs(input.getLogprobs());
    }
    if (input.getEcho() != null) {
      payload.setEcho(input.getEcho());
    }
    if (input.getStop() != null) {
      payload.setStop(input.getStop());
    }
    if (input.getPresence_penalty() != null) {
      payload.setPresencePenalty(input.getPresence_penalty());
    }
    if (input.getFrequency_penalty() != null) {
      payload.setFrequencyPenalty(input.getFrequency_penalty());
    }
    if (input.getBest_of() != null) {
      payload.setBestOf(input.getBest_of());
    }
    if (input.getLogit_bias() != null) {
      payload.setLogitBias(input.getLogit_bias().stream().collect(Collectors.toMap(LogitBiasInput::getKey,
          LogitBiasInput::getValue)));
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
    Map<String, Object> requestParams = new HashMap<>();
    requestParams.put("model", this.getModel());
    if (this.getPrompt() != null) {
      requestParams.put("prompt", this.getPrompt());
    }
    if (this.getSuffix() != null) {
      requestParams.put("suffix", this.getSuffix());
    }
    if (this.getMaxTokens() != null) {
      requestParams.put("max_tokens", this.getMaxTokens());
    }
    if (this.getTemperature() != null) {
      requestParams.put("temperature", this.getTemperature());
    }
    if (this.getTopP() != null) {
      requestParams.put("top_p", this.getTopP());
    }
    if (this.getN() != null) {
      requestParams.put("n", this.getN());
    }
    if (this.getStream() != null) {
      requestParams.put("stream", this.getStream());
    }
    if (this.getLogprobs() != null) {
      requestParams.put("logprobs", this.getLogprobs());
    }
    if (this.getEcho() != null) {
      requestParams.put("echo", this.getEcho());
    }
    if (this.getStop() != null) {
      requestParams.put("stop", this.getStop());
    }
    if (this.getPresencePenalty() != null) {
      requestParams.put("presence_penalty", this.getPresencePenalty());
    }
    if (this.getFrequencyPenalty() != null) {
      requestParams.put("frequency_penalty", this.getFrequencyPenalty());
    }
    if (this.getBestOf() != null) {
      requestParams.put("best_of", this.getBestOf());
    }
    if (this.getLogitBias() != null) {
      requestParams.put("logit_bias", this.getLogitBias());
    }
    if (this.getUser() != null) {
      requestParams.put("user", this.getUser());
    }
    return requestParams;
  }

}
