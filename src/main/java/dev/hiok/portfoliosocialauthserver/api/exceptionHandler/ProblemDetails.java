package dev.hiok.portfoliosocialauthserver.api.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class ProblemDetails {

  private String type;
  private String title;
  private int status;
  private String detail;
  private OffsetDateTime timestamp;
  private List<InvalidParams> invalidParams;

  @Getter
  @Builder
  public static class InvalidParams {
  
    private String name;
    private String reason;
  
  }
}
