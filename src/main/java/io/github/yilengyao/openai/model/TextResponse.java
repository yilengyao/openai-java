package io.github.yilengyao.openai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextResponse {
  private String text;

  /**
   * @return
   */
  public io.github.yilengyao.openai.graphql.generated.types.TextResponse toGraphQl() {
    return io.github.yilengyao.openai.graphql.generated.types.TextResponse.newBuilder()
        .text(this.getText())
        .build();
  }
}
