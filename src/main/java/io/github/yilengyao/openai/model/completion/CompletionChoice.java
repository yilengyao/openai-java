package io.github.yilengyao.openai.model.completion;

import io.github.yilengyao.openai.model.AbstractCompletionChoice;
import lombok.Data;

/**
 * Represents a choice in a completion result.
 */
@Data
public class CompletionChoice extends AbstractCompletionChoice {
  String text;
  LogProbResult logprobs;

  /**
   * Converts this choice to a GraphQL representation.
   * 
   * @return the GraphQL representation of this choice.
   */
  public io.github.yilengyao.openai.graphql.generated.types.CompletionChoice toGraphQl() {
    var output = io.github.yilengyao.openai.graphql.generated.types.CompletionChoice.newBuilder();
    if (this.getText() != null) {
      output.text(this.getText());
    }
    if (this.getIndex() != null) {
      output.index(this.getIndex());
    }
    if (this.getLogprobs() != null) {
      output.logprobs(this.getLogprobs().toGraphQl());
    }
    if (this.getFinishReason() != null) {
      output.finish_reason(this.getFinishReason());
    }
    return output.build();
  }

}
