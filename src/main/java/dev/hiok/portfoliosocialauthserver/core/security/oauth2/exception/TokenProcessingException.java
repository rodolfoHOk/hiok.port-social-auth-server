package dev.hiok.portfoliosocialauthserver.core.security.oauth2.exception;

public class TokenProcessingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TokenProcessingException(String message) {
    super(message);
  }

  public TokenProcessingException(String message, Throwable cause) {
    super(message, cause);
  }
  
}
