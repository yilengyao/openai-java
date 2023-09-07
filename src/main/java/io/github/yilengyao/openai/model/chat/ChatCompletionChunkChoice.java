package io.github.yilengyao.openai.model.chat;

import io.github.yilengyao.openai.model.AbstractCompletionChoice;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ChatCompletionChunkChoice extends AbstractCompletionChoice {
  private ChatContent delta;

      /**
     * Converts this ChatCompletionChunkChoice to a GraphQL representation.
     * 
     * @return the GraphQL representation of this ChatCompletionChunkChoice.
     */
    public io.github.yilengyao.openai.graphql.generated.types.ChatCompletionChunkChoice toGraphQl() {
        var outputBuilder = io.github.yilengyao.openai.graphql.generated.types.ChatCompletionChunkChoice.newBuilder();

        // The index and delta are mandatory based on your GraphQL schema, 
        // so no need for null checks.
        outputBuilder.index(this.getIndex());
        outputBuilder.delta(this.delta.toGraphQl()); // Assumes ChatContent has a toGraphQl method

        if (this.finishReason != null) {
            outputBuilder.finish_reason(this.getFinishReason());
        }

        return outputBuilder.build();
    }
}
