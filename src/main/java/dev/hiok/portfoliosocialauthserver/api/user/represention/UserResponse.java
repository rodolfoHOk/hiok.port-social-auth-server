package dev.hiok.portfoliosocialauthserver.api.user.represention;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
  
  private long id;
  private String name;
  private String email;
  private String imageUrl;

}
