package io.github.yilengyao.openai.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.yilengyao.openai.exceptions.OpenAiException;
import io.github.yilengyao.openai.model.OpenAiResponse;
import io.github.yilengyao.openai.model.completion.CompletionPayload;
import io.github.yilengyao.openai.model.completion.CompletionResponse;
import io.github.yilengyao.openai.model.edit.EditPayload;
import io.github.yilengyao.openai.model.edit.EditResponse;
import io.github.yilengyao.openai.model.image.CreateImagePayload;
import io.github.yilengyao.openai.model.image.EditImagePayload;
import io.github.yilengyao.openai.model.image.ImageResponse;
import io.github.yilengyao.openai.model.model.Model;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
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
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handleErrorResponse)
        .bodyToMono(Model.class)
        .doOnError(ex -> {
          if (ex instanceof OpenAiException) {
            OpenAiException openAiEx = (OpenAiException) ex;
            log.error("Error calling OpenAI API: Status Code: {} - Message: {}", openAiEx.getStatusCode(),
                openAiEx.getErrorMessage());
          }
        })
        .block();
  }

  @Override
  public OpenAiResponse<Model> models() {
    return openAiWebClient
        .get()
        .uri(MODELS_ENDPOINT)
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handleErrorResponse)
        .bodyToMono(OpenAiResponse.class)
        .doOnError(ex -> {
          if (ex instanceof OpenAiException) {
            OpenAiException openAiEx = (OpenAiException) ex;
            log.error("Error calling OpenAI API: Status Code: {} - Message: {}", openAiEx.getStatusCode(),
                openAiEx.getErrorMessage());
          }
        })
        .block();
  }

  @Override
  public CompletionResponse completion(CompletionPayload payload) {
    return openAiWebClient
        .post()
        .uri(COMPLETION_ENDPOINT)
        .body(BodyInserters.fromValue(payload.getPayload()))
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handleErrorResponse)
        .bodyToMono(CompletionResponse.class)
        .doOnError(ex -> {
          if (ex instanceof OpenAiException) {
            OpenAiException openAiEx = (OpenAiException) ex;
            log.error("Error calling OpenAI API: Status Code: {} - Message: {}", openAiEx.getStatusCode(),
                openAiEx.getErrorMessage());
          }
        })
        .block();
  }

  @Deprecated
  @Override
  public EditResponse edit(EditPayload payload) {
    return openAiWebClient
        .post()
        .uri(EDIT_ENDPOINT)
        .body(BodyInserters.fromValue(payload.getPayload()))
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handleErrorResponse)
        .bodyToMono(EditResponse.class)
        .doOnError(ex -> {
          if (ex instanceof OpenAiException) {
            OpenAiException openAiEx = (OpenAiException) ex;
            log.error("Error calling OpenAI API: Status Code: {} - Message: {}", openAiEx.getStatusCode(),
                openAiEx.getErrorMessage());
          }
        })
        .block();
  }

  @Override
  public ImageResponse createImage(CreateImagePayload payload) {
    WebClient client = openAiWebClient;

    if (payload.getPayload().containsKey("response_format") &&
        payload.getPayload().get("response_format").equals("b64_json")) {
      client = largeBufferOpenAiWebClient;
    }

    return client
        .post()
        .uri(CREATE_IMAGE_ENDPOINT)
        .body(BodyInserters.fromValue(payload.getPayload()))
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handleErrorResponse)
        .bodyToMono(ImageResponse.class)
        .doOnError(ex -> {
          if (ex instanceof OpenAiException) {
            OpenAiException openAiEx = (OpenAiException) ex;
            log.error("error calling OpenAI API: Status Code: {} - Message: {}", openAiEx.getStatusCode(),
                openAiEx.getErrorMessage());
          }
        })
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
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handleErrorResponse)
        .bodyToMono(ImageResponse.class)
        .doOnError(ex -> {
          if (ex instanceof OpenAiException) {
            OpenAiException openAiEx = (OpenAiException) ex;
            log.error("Error calling OpenAI API: Status Code: {} - Message: {}", openAiEx.getStatusCode(),
                openAiEx.getErrorMessage());
          }
        })
        .block();
  }

  private Mono<Throwable> handleErrorResponse(ClientResponse clientResponse) {
    return clientResponse.bodyToMono(String.class).flatMap(errorMessage -> {
      return Mono.error(new OpenAiException(clientResponse.statusCode().value(), errorMessage));
    });
  }
}
