package dev.hiok.portfoliosocialauthserver.core.security.oauth2.exception;

public class TokenProcessingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TokenProcessingException(String arg0) {
    super(arg0);
  }

  public TokenProcessingException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }
  
}
