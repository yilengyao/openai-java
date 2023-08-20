package com.yilengyao.openai.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.yilengyao.openai.model.OpenAiResponse;
import com.yilengyao.openai.model.completion.CompletionPayload;
import com.yilengyao.openai.model.completion.CompletionResponse;
import com.yilengyao.openai.model.edit.EditPayload;
import com.yilengyao.openai.model.edit.EditResponse;
import com.yilengyao.openai.model.image.CreateImagePayload;
import com.yilengyao.openai.model.image.EditImagePayload;
import com.yilengyao.openai.model.image.ImageResponse;
import com.yilengyao.openai.model.model.Model;

public class OpenAiClientImpl implements OpenAiClient {

  public static final String MODELS_ENDPOINT = "/v1/models";
  public static final String COMPLETION_ENDPOINT = "/v1/completions";
  public static final String EDIT_ENDPOINT = "/v1/edits";
  public static final String CREATE_IMAGE_ENDPOINT = "/v1/images/generations";
  public static final String EDIT_IMAGE_ENDPOINT = "/v1/images/edits";

  private final WebClient openAiWebClient;
  private final WebClient largeBufferOpenAiWebClient;

  @Autowired
  public OpenAiClientImpl(
      WebClient openAiWebClient,
      WebClient largeBufferOpenAiWebClient) {
    this.openAiWebClient = openAiWebClient;
    this.largeBufferOpenAiWebClient = largeBufferOpenAiWebClient;
  }

  @Override
  public Model models(String id) {
    return openAiWebClient
        .get()
        .uri(String.join("/", MODELS_ENDPOINT, id))
        .retrieve()
        .bodyToMono(Model.class)
        .block();
  }

  @Override
  public OpenAiResponse<Model> models() {
    return openAiWebClient
        .get()
        .uri(MODELS_ENDPOINT)
        .retrieve()
        .bodyToMono(OpenAiResponse.class)
        .block();
  }

  @Override
  public CompletionResponse completion(CompletionPayload payload) {
    return openAiWebClient
        .post()
        .uri(COMPLETION_ENDPOINT)
        .body(BodyInserters.fromValue(payload.getPayload()))
        .retrieve()
        .bodyToMono(CompletionResponse.class)
        .block();
  }

  @Override
  public EditResponse edit(EditPayload payload) {
    return openAiWebClient
        .post()
        .uri(EDIT_ENDPOINT)
        .body(BodyInserters.fromValue(payload.getPayload()))
        .retrieve()
        .bodyToMono(EditResponse.class)
        .block();
  }

  @Override
  public ImageResponse createImage(CreateImagePayload payload) {
    if (payload.getPayload().containsKey("response_format") &&
        payload.getPayload().get("response_format").equals("b64_json")) {
      return largeBufferOpenAiWebClient
          .post()
          .uri(CREATE_IMAGE_ENDPOINT)
          .body(BodyInserters.fromValue(payload.getPayload()))
          .retrieve()
          .bodyToMono(ImageResponse.class)
          .block();
    }
    return openAiWebClient
        .post()
        .uri(CREATE_IMAGE_ENDPOINT)
        .body(BodyInserters.fromValue(payload.getPayload()))
        .retrieve()
        .bodyToMono(ImageResponse.class)
        .block();
  }

  @Override
  public ImageResponse editImage(EditImagePayload payload) throws IOException {
    return largeBufferOpenAiWebClient
        .post()
        .uri(EDIT_IMAGE_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(payload.getPayload()))
        .retrieve()
        .bodyToMono(ImageResponse.class)
        .block();
  }

}
