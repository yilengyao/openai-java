package io.github.yilengyao.openai.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.yilengyao.openai.exceptions.OpenAiException;
import io.github.yilengyao.openai.model.OpenAiResponse;
import io.github.yilengyao.openai.model.chat.ChatCompletion;
import io.github.yilengyao.openai.model.chat.ChatCompletionChunk;
import io.github.yilengyao.openai.model.chat.ChatCompletionPayload;
import io.github.yilengyao.openai.model.completion.CompletionPayload;
import io.github.yilengyao.openai.model.completion.CompletionResponse;
import io.github.yilengyao.openai.model.edit.EditPayload;
import io.github.yilengyao.openai.model.edit.EditResponse;
import io.github.yilengyao.openai.model.image.CreateImagePayload;
import io.github.yilengyao.openai.model.image.EditImagePayload;
import io.github.yilengyao.openai.model.image.ImageResponse;
import io.github.yilengyao.openai.model.model.Model;
import io.github.yilengyao.openai.validation.ValidateFieldValue;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class OpenAiClientImpl implements OpenAiClient {

  public static final String MODELS_ENDPOINT = "/v1/models";
  public static final String COMPLETION_ENDPOINT = "/v1/completions";
  public static final String CHAT_ENDPOINT = "/v1/chat/completions";
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
        .doOnError(this::handleErrorLogging)
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
        .doOnError(this::handleErrorLogging)
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
        .doOnError(this::handleErrorLogging)
        .block();
  }

  @Override
  @ValidateFieldValue(fieldName="stream", expectedValue="false", errorMessage="Invalid stream value for ChatCompletionPayload, should be false but was true")
  public ChatCompletion createChatCompletion(ChatCompletionPayload payload) {
    return openAiWebClient
    .post()
    .uri(CHAT_ENDPOINT)
    .body(BodyInserters.fromValue(payload.getPayload()))
    .retrieve()
    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handleErrorResponse)
    .bodyToMono(ChatCompletion.class)
    .doOnError(this::handleErrorLogging)
    .block();
  }

  @Override
  @ValidateFieldValue(fieldName="stream", expectedValue="true", errorMessage="Invalid stream value for ChatCompletionPayload, should be true but was false")
  public Flux<ChatCompletionChunk> streamChatCompletion(ChatCompletionPayload payload) {
    return openAiWebClient
    .post()
    .uri(CHAT_ENDPOINT)
    .body(BodyInserters.fromValue(payload.getPayload()))
    .retrieve()
    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handleErrorResponse)
    .bodyToFlux(ChatCompletionChunk.class)
    .doOnError(this::handleErrorLogging);
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
        .doOnError(this::handleErrorLogging)
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
        .doOnError(this::handleErrorLogging)
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
        .doOnError(this::handleErrorLogging)
        .block();
  }

  private Mono<Throwable> handleErrorResponse(ClientResponse clientResponse) {
    return clientResponse.bodyToMono(String.class).flatMap(errorMessage -> {
      return Mono.error(new OpenAiException(clientResponse.statusCode().value(), errorMessage));
    });
  }

  private void handleErrorLogging(Throwable ex) {
    if (ex instanceof OpenAiException) {
      OpenAiException openAiEx = (OpenAiException) ex;
      log.error("Error calling OpenAI API: Status Code: {} - Message: {}", openAiEx.getStatusCode(),
          openAiEx.getErrorMessage());
    }
  }
}
