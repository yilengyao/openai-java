package io.github.yilengyao.openai.model.image;

import java.util.List;

import lombok.Data;

@Data
public class ImageResponse {
  Long createdAt;
  List<Image> data;

  public io.github.yilengyao.openai.graphql.generated.types.ImageResponse toGraphQl() {
    var output = io.github.yilengyao.openai.graphql.generated.types.ImageResponse.newBuilder();
    if (this.getCreatedAt() != null) {
      output.createdAt(this.getCreatedAt());
    }
    if (this.getData() != null) {
      output.data(data.stream().map(Image::toGraphQl).toList());
    }
    return output.build();
  }

}
