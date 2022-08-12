package dev.hiok.portfoliosocialauthserver.core.security.oauth2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthBadRequestException extends AuthenticationException {

  private static final long serialVersionUID = 1L;

  public AuthBadRequestException(String message) {
    super(message);
  }

  public AuthBadRequestException(String message, Throwable cause) {
    super(message, cause);
  }
  
}
