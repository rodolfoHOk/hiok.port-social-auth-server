package dev.hiok.portfoliosocialauthserver.core.security.oauth2.userInfo;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FacebookOAuth2UserInfo extends OAuth2UserInfo {

  public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return (String) attributes.get("id");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getImageUrl() {
    if (attributes.containsKey("picture")) {
      @SuppressWarnings("unchecked")
      Map<String, Object> pictureObject =  (Map<String, Object>) attributes.get("picture");
      if (pictureObject.containsKey("data")) {
        @SuppressWarnings("unchecked")
        Map<String, Object> dataObject = (Map<String, Object>) pictureObject.get("data");
        if (dataObject.containsKey("url")) {
          return (String) dataObject.get("url");
        }
      }
    }
    return null;
  }
  
}
