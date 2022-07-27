package dev.hiok.portfoliosocialauthserver.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private final Auth auth = new Auth();
  private final Cors cors = new Cors();
  private final OAuth2 oauth2 = new OAuth2();

  public Auth getAuth() {
    return auth;
  }

  public Cors getCors() {
    return cors;
  }

  public OAuth2 getOauth2() {
    return oauth2;
  }

  public static class Auth {

    private String tokenSecret;
    private long tokenExpiration;
   
    public String getTokenSecret() {
      return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
      this.tokenSecret = tokenSecret;
    }

    public long getTokenExpiration() {
      return tokenExpiration;
    }

    public void setTokenExpiration(long tokenExpiration) {
      this.tokenExpiration = tokenExpiration;
    }
  }

  public static class Cors {

    private List<String> allowedOrigins = new ArrayList<>();

    public List<String> getAllowedOrigins() {
      return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
      this.allowedOrigins = allowedOrigins;
    }
  }

  public static class OAuth2 {

    private List<String> authorizedRedirectUris = new ArrayList<>();

    public List<String> getAuthorizedRedirectUris() {
      return authorizedRedirectUris;
    }

    public void setAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
      this.authorizedRedirectUris = authorizedRedirectUris;
    }
  }
  
}
