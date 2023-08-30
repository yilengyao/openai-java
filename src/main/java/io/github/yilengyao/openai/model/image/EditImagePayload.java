package io.github.yilengyao.openai.model.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import io.github.yilengyao.openai.graphql.generated.types.EditImageInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EditImagePayload {
  MultipartFile image;
  String prompt;
  MultipartFile mask;
  Integer n;
  String size;
  String responseFormat;
  String user;

  public static EditImagePayload fromGraphQl(MultipartFile image, MultipartFile mask, EditImageInput input) {
    var payload = new EditImagePayload();
    payload.setImage(image);
    payload.setPrompt(input.getPrompt());
    if (mask != null) {
      payload.setMask(mask);
    }
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

  public MultiValueMap<String, Object> getPayload() throws IOException {
    MultiValueMap<String, Object> requestParam = new LinkedMultiValueMap<>();
    requestParam.add("image", new FileSystemResource(multipartFileToFile(this.getImage())));
    requestParam.add("prompt", this.getPrompt());
    if (this.getMask() != null) {
      requestParam.add("mask", new FileSystemResource(multipartFileToFile(this.getMask())));
    }
    if (this.getN() != null) {
      requestParam.add("n", this.getN());
    }
    if (this.getSize() != null) {
      requestParam.add("size", this.getSize());
    }
    if (this.getResponseFormat() != null) {
      requestParam.add("response_format", this.getResponseFormat());
    }
    if (this.getUser() != null) {
      requestParam.add("user", this.getUser());
    }
    return requestParam;
  }

  private File multipartFileToFile(MultipartFile multipartFile) throws IOException {
    File file = new File(multipartFile.getOriginalFilename());
    file.createNewFile();
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(multipartFile.getBytes());
    fos.close();
    return file;
  }

}
