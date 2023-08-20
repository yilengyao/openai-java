package com.yilengyao.openai.client;

import java.io.IOException;

import com.yilengyao.openai.model.OpenAiResponse;
import com.yilengyao.openai.model.completion.CompletionPayload;
import com.yilengyao.openai.model.completion.CompletionResponse;
import com.yilengyao.openai.model.edit.EditPayload;
import com.yilengyao.openai.model.edit.EditResponse;
import com.yilengyao.openai.model.image.CreateImagePayload;
import com.yilengyao.openai.model.image.EditImagePayload;
import com.yilengyao.openai.model.image.ImageResponse;
import com.yilengyao.openai.model.model.Model;

public interface OpenAiClient {

  public Model models(String id);

  public OpenAiResponse<Model> models();

  public CompletionResponse completion(CompletionPayload payload);

  public EditResponse edit(EditPayload payload);

  public ImageResponse createImage(CreateImagePayload payload);

  public ImageResponse editImage(EditImagePayload payload) throws IOException;

}
