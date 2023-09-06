package io.github.yilengyao.openai.model.chat;

import java.util.List;

import io.github.yilengyao.openai.model.model.Usage;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ChatCompletion extends ChatCompletionResponse {
  private List<ChatCompletionChoice> choices;
  private Usage usage;

    /**
   * Converts this ChatCompletion to a GraphQL representation.
   * 
   * @return the GraphQL representation of this ChatCompletion.
   */
  public io.github.yilengyao.openai.graphql.generated.types.ChatCompletion toGraphQl() {
    var output = io.github.yilengyao.openai.graphql.generated.types.ChatCompletion.newBuilder();
    output.id(this.getId());
    output.object(this.getObject());
    output.create(this.getCreated());
    output.model(this.getModel());

    if (this.getChoices() != null) {
      output.choices(this.getChoices()
          .stream()
          .map(ChatCompletionChoice::toGraphQl)
          .toList());
    }

    output.usage(this.usage.toGraphQl());
    return output.build();
  }
}
