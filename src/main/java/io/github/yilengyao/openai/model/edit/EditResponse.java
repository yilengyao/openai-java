package io.github.yilengyao.openai.model.edit;

import java.util.List;

import io.github.yilengyao.openai.model.model.Usage;

import lombok.Data;

@Data
public class EditResponse {

  public String object;
  public Long created;
  public List<EditChoice> choices;
  public Usage usage;

  public io.github.yilengyao.openai.graphql.generated.types.EditResponse toGraphQl() {
    var output = io.github.yilengyao.openai.graphql.generated.types.EditResponse.newBuilder();
    if (this.getObject() != null) {
      output.object(this.getObject());
    }
    if (this.getCreated() != null) {
      output.created(this.getCreated());
    }
    if (this.getChoices() != null) {
      output.choices(this.getChoices()
          .stream()
          .map(editChoice -> editChoice.toGraphQl())
          .toList());
    }
    if (this.getUsage() != null) {
      output.usage(this.getUsage().toGraphQl());
    }
    return output.build();
  }

}
