package io.github.yilengyao.openai.model;

import java.util.List;

import io.github.yilengyao.openai.model.model.Model;

import lombok.Data;

@Data
public class OpenAiResponse<T extends Model> {

  public List<T> data;
  public String object;

  public io.github.yilengyao.openai.graphql.generated.types.OpenAiResponse toGraphQl() {
    return io.github.yilengyao.openai.graphql.generated.types.OpenAiResponse.newBuilder()
        .data(this.getData().stream().map(T::toGraphQl).toList())
        .object(this.getObject())
        .build();
  }

}
