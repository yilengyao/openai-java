package io.github.yilengyao.openai.model.completion;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.yilengyao.openai.graphql.generated.types.TopLogProb;

import lombok.Data;

@Data
public class LogProbResult {
  List<String> tokens;
  @JsonProperty("token_lobprobs")
  List<Double> tokenLogprobs;
  @JsonProperty("top_logprobs")
  List<Map<String, Double>> topLogprobs;
  @JsonProperty("text_offset")
  List<Integer> textOffset;

  public io.github.yilengyao.openai.graphql.generated.types.LogProbResult toGraphQl() {
    var output = io.github.yilengyao.openai.graphql.generated.types.LogProbResult.newBuilder();
    if (this.getTokens() != null) {
      output.tokens(this.getTokens());
    }
    if (this.getTokenLogprobs() != null) {
      output.token_lobprobs(this.getTokenLogprobs());
    }
    if (this.getTopLogprobs() != null) {
      output.top_logprobs(this.getTopLogprobs().stream().map(
          topLogProb -> topLogProb
              .keySet()
              .stream()
              .map(key -> TopLogProb.newBuilder()
                  .key(key)
                  .value(topLogProb.get(key))
                  .build())
              .toList())
          .toList());
    }
    if (this.getTextOffset() != null) {
      output.text_offset(this.getTextOffset());
    }
    return output.build();
  }

}
