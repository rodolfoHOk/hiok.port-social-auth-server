package dev.hiok.portfoliosocialauthserver.core.security.oauth2.userInfo;

import java.util.Map;

import dev.hiok.portfoliosocialauthserver.core.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import dev.hiok.portfoliosocialauthserver.domain.model.AuthProvider;

public class OAuth2UserInfoFactory {

  public static OAuth2UserInfo getOauth2UserInfo (String registrationId, Map<String, Object> attributes) {
    if (registrationId.equals(AuthProvider.facebook.toString())) {
      return new FacebookOAuth2UserInfo(attributes);
    } else if (registrationId.equals(AuthProvider.github.toString())) {
      return new GithubOAuth2UserInfo(attributes);
    } else if (registrationId.equals(AuthProvider.google.toString())) {
      return new GoogleOAuth2UserInfo(attributes);
    } else {
      throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported.");
    }
  }
  
}
