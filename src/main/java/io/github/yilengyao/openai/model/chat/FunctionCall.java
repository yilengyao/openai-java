package io.github.yilengyao.openai.model.chat;

import java.util.HashMap;
import java.util.Map;

import io.github.yilengyao.openai.graphql.generated.types.FunctionCallInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FunctionCall {
  private String name;
  private String arguments;

  public static FunctionCall fromGraphQl(FunctionCallInput input) {
    var functionCall = new FunctionCall();
    functionCall.setName(input.getName());
    functionCall.setArguments(input.getArguments());
    return functionCall;
  }

  public Map<String, Object> getPayload() {
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("name", this.getName());
    payloadMap.put("arguments", this.getArguments());
    return payloadMap;
  }

    /**
     * Converts this FunctionCall to a GraphQL representation.
     * 
     * @return the GraphQL representation of this FunctionCall.
     */
    public io.github.yilengyao.openai.graphql.generated.types.FunctionCall toGraphQl() {
        var output = io.github.yilengyao.openai.graphql.generated.types.FunctionCall.newBuilder();

        // Since both fields `name` and `arguments` are mandatory in your GraphQL schema, 
        // we don't need to check for nulls.
        output.name(this.getName());
        output.arguments(this.getArguments());

        return output.build();
    }}
