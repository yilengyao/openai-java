package com.yilengyao.openai.model.image;

import java.util.HashMap;
import java.util.Map;

import com.yilengyao.openai.graphql.generated.types.CreateImageInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateImagePayload {

  @NonNull
  String prompt;
  Integer n;
  String size;
  String responseFormat;
  String user;

  public static CreateImagePayload fromGraphQl(CreateImageInput input) {
    var payload = new CreateImagePayload();
    payload.setPrompt(input.getPrompt());
    if (input.getN() != null) {
      payload.setN(input.getN());
    }
    if (input.getSize() != null) {
      switch (input.getSize()) {
        case X256:
          payload.setSize("256x256");
          break;
        case X512:
          payload.setSize("512x512");
          break;
        case X1024:
          payload.setSize("1024x1024");
      }
    }
    if (input.getResponseFormat() != null) {
      payload.setResponseFormat(input.getResponseFormat().name().toLowerCase());
    }
    if (input.getUser() != null) {
      payload.setUser(input.getUser());
    }
    return payload;
  }

  public Map<String, Object> getPayload() {
    Map<String, Object> requestParams = new HashMap<>();
    requestParams.put("prompt", this.getPrompt());
    if (this.getN() != null) {
      requestParams.put("n", this.getN());
    }
    if (this.getSize() != null) {
      requestParams.put("size", this.getSize());
    }
    if (this.getResponseFormat() != null) {
      requestParams.put("response_format", this.getResponseFormat());
    }
    if (this.getUser() != null) {
      requestParams.put("user", this.getUser());
    }
    return requestParams;
  }

}
