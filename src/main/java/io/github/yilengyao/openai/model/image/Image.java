package io.github.yilengyao.openai.model.image;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Image {

  String url;
  @JsonProperty("b64_json")
  String b64Json;

  io.github.yilengyao.openai.graphql.generated.types.Image toGraphQl() {
    var output = io.github.yilengyao.openai.graphql.generated.types.Image.newBuilder();
    if (this.getUrl() != null) {
      output.url(this.getUrl());
    }
    if (this.getB64Json() != null) {
      output.b64Json(this.getB64Json());
    }
    return output.build();
  }

}
