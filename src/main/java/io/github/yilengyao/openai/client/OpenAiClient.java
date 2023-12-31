package io.github.yilengyao.openai.client;

import java.io.IOException;

import io.github.yilengyao.openai.model.OpenAiResponse;
import io.github.yilengyao.openai.model.TextResponse;
import io.github.yilengyao.openai.model.audio.TranscriptionPayload;
import io.github.yilengyao.openai.model.audio.TranslationPayload;
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
import reactor.core.publisher.Flux;

public interface OpenAiClient {

  public TextResponse createTranscription(TranscriptionPayload payload) throws IOException;
 
  public TextResponse createTranslation(TranslationPayload payload) throws IOException;

  public Model models(String id);

  public OpenAiResponse<Model> models();

  public CompletionResponse completion(CompletionPayload payload);

  public ChatCompletion createChatCompletion(ChatCompletionPayload payload);

  public Flux<ChatCompletionChunk> streamChatCompletion(ChatCompletionPayload payload);

  public EditResponse edit(EditPayload payload);

  public ImageResponse createImage(CreateImagePayload payload);

  public ImageResponse editImage(EditImagePayload payload) throws IOException;

}
