package io.github.yilengyao.openai.model.chat;

import java.util.HashMap;
import java.util.Map;

import io.github.yilengyao.openai.graphql.generated.types.FunctionInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Function {
  private String name;
  private String description;
  private Map<String, Object> parameters;

  /**
   * @param input
   * @return
   */
  public static Function fromGraphQl(FunctionInput input) {
    var function = new Function();

    // Setting the name and description
    function.setName(input.getName());
    if (input.getDescription() != null) {
      function.setDescription(input.getDescription());
    }

    // As for the parameters, we are assuming the `Object` in the GraphQL schema
    // translates to a `Map<String, Object>` in the Java representation
    // Note: Ensure that ParameterInput's Object translates correctly to Java's Map
    Map<String, Object> parametersMap = new HashMap<>();
    parametersMap.put(input.getParameters().getType(), input.getParameters().getProperties());
    function.setParameters(parametersMap);
    return function;
  }

  /**
   * Retrieves the payload as a map of key-value pairs.
   * 
   * @return
   */
  public Map<String, Object> getPayload() {
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("name", this.getName());
    if (this.description != null) {
      payloadMap.put("description", this.getDescription());
    }
    payloadMap.put("parameters", this.getParameters());
    return payloadMap;
  }

}
