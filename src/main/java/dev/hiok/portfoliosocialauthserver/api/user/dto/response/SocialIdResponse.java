package dev.hiok.portfoliosocialauthserver.api.user.dto.response;

import dev.hiok.portfoliosocialauthserver.domain.model.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialIdResponse {

  private AuthProvider provider;
  private String socialId;

}
