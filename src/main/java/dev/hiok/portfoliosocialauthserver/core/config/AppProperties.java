package dev.hiok.portfoliosocialauthserver.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private final Auth auth = new Auth();
  private final Cors cors = new Cors();
  private final OAuth2 oauth2 = new OAuth2();

  @Getter
  @Setter
  public static class Auth {

    private String jwtBase64Jks;
    private String jwtJksPassword;
    private String jwtJksAlias;
    private long tokenExpiration;
   
  }

  @Getter
  @Setter
  public static class Cors {

    private List<String> allowedOrigins = new ArrayList<>();

  }

  @Getter
  @Setter
  public static class OAuth2 {

    private List<String> authorizedRedirectUris = new ArrayList<>();

  }
  
}
