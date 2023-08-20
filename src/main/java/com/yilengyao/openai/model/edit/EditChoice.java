package com.yilengyao.openai.model.edit;

import lombok.Data;

@Data
public class EditChoice {
  String text;
  Integer index;

  public com.yilengyao.openai.graphql.generated.types.EditChoice toGraphQl() {
    var output = com.yilengyao.openai.graphql.generated.types.EditChoice.newBuilder();
    if (this.getText() != null) {
      output.text(this.getText());
    }
    if (this.getIndex() != null) {
      output.index(this.getIndex());
    }
    return output.build();
  }

}
