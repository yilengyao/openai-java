package io.github.yilengyao.openai.model.edit;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.yilengyao.openai.graphql.generated.types.EditInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EditPayload {
  @JsonProperty("model")
  String model;
  @JsonProperty("input")
  String input;
  @JsonProperty("instruction")
  String instruction;
  @JsonProperty("n")
  Integer n;
  @JsonProperty("temperature")
  Double temperature;
  @JsonProperty("top_p")
  Double topP;

  public static EditPayload fromGraphQl(EditInput input) {
    var payload = new EditPayload();
    payload.setModel(input.getModel());
    payload.setInstruction(input.getInstruction());
    if (input.getInput() != null) {
      payload.setInput(input.getInput());
    }
    if (input.getN() != null) {
      payload.setN(input.getN());
    }
    if (input.getTemperature() != null) {
      payload.setTemperature(input.getTemperature());
    }
    if (input.getTop_p() != null) {
      payload.setTopP(input.getTop_p());
    }
    return payload;
  }

  public Map<String, Object> getPayload() {
    Map<String, Object> requestParams = new HashMap<>();
    requestParams.put("model", this.getModel());
    requestParams.put("instruction", this.getInstruction());
    if (this.getInput() != null) {
      requestParams.put("input", this.getInput());
    }
    if (this.getN() != null) {
      requestParams.put("n", this.getN());
    }
    if (this.getTemperature() != null) {
      requestParams.put("temperature", this.getTemperature());
    }
    if (this.getTopP() != null) {
      requestParams.put("top_p", this.getTopP());
    }
    return requestParams;
  }

}
