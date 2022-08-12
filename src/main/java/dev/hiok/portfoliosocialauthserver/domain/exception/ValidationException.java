package dev.hiok.portfoliosocialauthserver.domain.exception;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

  static final long serialVersionUID = 1L;

  private BindingResult bindingResult;

  public ValidationException(BindingResult bindingResult) {
    this.bindingResult = bindingResult;
  }

  public ValidationException(String message) {
    super(message);
    this.bindingResult = null;
  }
  
}
