package io.github.yilengyao.openai.model.chat;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ChatCompletionChunk extends ChatCompletionResponse {
  private List<ChatCompletionChunkChoice> choices;

  /**
   * Converts this ChatCompletionChunk to a GraphQL representation.
   * 
   * @return the GraphQL representation of this ChatCompletionChunk.
   */
  public io.github.yilengyao.openai.graphql.generated.types.ChatCompletionChunk toGraphQl() {
    var outputBuilder = io.github.yilengyao.openai.graphql.generated.types.ChatCompletionChunk.newBuilder();

    // Since fields like id, object, created, and model are required based on your
    // GraphQL schema,
    // we don't need to check for nulls for these fields.
    outputBuilder.id(this.getId());
    outputBuilder.object(this.getObject());
    outputBuilder.created(this.getCreated());
    outputBuilder.model(this.getModel());

    if (this.getChoices() != null) {
      outputBuilder.choices(this.getChoices()
          .stream()
          .map(ChatCompletionChunkChoice::toGraphQl)
          .toList());
    }

    return outputBuilder.build();
  }
}
