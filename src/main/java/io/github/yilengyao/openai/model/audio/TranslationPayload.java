package io.github.yilengyao.openai.model.audio;

import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import io.github.yilengyao.openai.graphql.generated.types.TranslationInput;

import io.github.yilengyao.openai.util.FileUtility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TranslationPayload {
    private MultipartFile file;
    private String model;
    private String prompt;
    private String responseFormat;
    private Double temperature;

/**
 * @param file
 * @param input
 * @return
 */
public static TranslationPayload fromGraphQl(MultipartFile file, TranslationInput input) {
    var payload = new TranslationPayload();
    payload.setFile(file);
    payload.setModel(input.getModel());
    if (input.getPrompt() != null) {
        payload.setPrompt(input.getPrompt());
    }
    if (input.getResponseFormat() != null) {
        payload.setResponseFormat(input.getResponseFormat());
    }
    if (input.getTemperature() != null) {
        payload.setTemperature(input.getTemperature());
    }
    return payload;
}

    public MultiValueMap<String, Object> getPayload() throws IOException {
        MultiValueMap<String, Object> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("file", new FileSystemResource(FileUtility.multipartFileToFile(this.getFile())));
        requestParam.add("model", this.getModel());
        if (this.prompt != null) {
            requestParam.add("prompt", this.getPrompt());
        }
        if (this.responseFormat != null) {
            requestParam.add("response_format", this.getResponseFormat());
        }
        if (this.temperature != null) {
            requestParam.add("temperature", this.getTemperature());
        }
        return requestParam;
    }
}
