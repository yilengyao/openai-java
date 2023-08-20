package com.yilengyao.openai.model.image;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Image {

  String url;
  @JsonProperty("b64_json")
  String b64Json;

  com.yilengyao.openai.graphql.generated.types.Image toGraphQl() {
    var output = com.yilengyao.openai.graphql.generated.types.Image.newBuilder();
    if (this.getUrl() != null) {
      output.url(this.getUrl());
    }
    if (this.getB64Json() != null) {
      output.b64Json(this.getB64Json());
    }
    return output.build();
  }

}
