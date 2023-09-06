package io.github.yilengyao.openai.model.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ChatContent {
  private String role;
  private String content;
  @JsonProperty("function_call")
  private FunctionCall functionCall;

  /**
   * Converts this content to a GraphQL representation.
   * 
   * @return the GraphQL representation of this content.
   */
  public io.github.yilengyao.openai.graphql.generated.types.ChatContent toGraphQl() {
    var output = io.github.yilengyao.openai.graphql.generated.types.ChatContent.newBuilder();

    output.role(this.getRole());
    if (this.content != null) {
      output.content(this.getContent());
    }
    if (this.functionCall != null) {
      output.function_call(this.functionCall.toGraphQl());
    }
    return output.build();
  }

}
