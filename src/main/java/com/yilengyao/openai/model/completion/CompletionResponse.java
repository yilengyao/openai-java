package com.yilengyao.openai.model.completion;

import java.util.List;

import com.yilengyao.openai.graphql.generated.types.CompletionOutput;
import com.yilengyao.openai.model.model.Usage;

import lombok.Data;

/**
 * Represents a response from the completion API.
 */
@Data
public class CompletionResponse {
  /**
   * A unique id assigned to this completion.
   */
  String id;

  /**
   * The type of object returned, should be "text_completion"
   */
  String object;

  /**
   * The creation time in epoch seconds.
   */
  Long created;

  /**
   * The GPT-3 model used.
   */
  String model;

  /**
   * A list of generated completions.
   */
  List<CompletionChoice> choices;

  /**
   * The API usage for this request
   */
  Usage usage;

  /**
   * Converts this CompletionResponse to its GraphQL representation.
   *
   * @return the GraphQL representation of this CompletionResponse
   */
  public CompletionOutput toGraphQl() {
    var output = CompletionOutput.newBuilder();
    output.id(this.getId());
    if (this.getObject() != null) {
      output.object(this.getObject());
    }
    if (this.getCreated() != null) {
      output.created(this.getCreated());
    }
    if (this.getModel() != null) {
      output.model(this.getModel());
    }
    if (this.getChoices() != null) {
      output.choices(this.getChoices()
          .stream()
          .map(completionChoice -> completionChoice.toGraphQl())
          .toList());
    }
    if (this.getUsage() != null) {
      output.usage(this.getUsage().toGraphQl());
    }
    return output.build();
  }

}
