package io.github.yilengyao.openai.model.chat;

import io.github.yilengyao.openai.model.AbstractCompletionChoice;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ChatCompletionChoice extends AbstractCompletionChoice {
  private ChatContent message;

  /**
   * Converts this choice to a GraphQL representation.
   * 
   * @return the GraphQL representation of this choice.
   */
  public io.github.yilengyao.openai.graphql.generated.types.ChatCompletionChoice toGraphQl() {
    var output = io.github.yilengyao.openai.graphql.generated.types.ChatCompletionChoice.newBuilder();
    output.index(this.getIndex());
    output.message(this.message.toGraphQl());
    output.finish_reason(this.getFinishReason());
    return output.build();
  }
}
