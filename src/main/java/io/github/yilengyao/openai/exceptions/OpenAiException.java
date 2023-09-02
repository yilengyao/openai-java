package io.github.yilengyao.openai.exceptions;

public class OpenAiException extends RuntimeException {
  private final Integer statusCode;
  private final String errorMessage;

  public OpenAiException(Integer statusCode, String errorMessage) {
    super(errorMessage);
    this.statusCode = statusCode;
    this.errorMessage = errorMessage;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
