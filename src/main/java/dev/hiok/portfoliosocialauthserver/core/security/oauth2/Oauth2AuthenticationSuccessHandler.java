package dev.hiok.portfoliosocialauthserver.core.security.oauth2;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import dev.hiok.portfoliosocialauthserver.core.config.AppProperties;
import dev.hiok.portfoliosocialauthserver.core.security.TokenProvider;
import dev.hiok.portfoliosocialauthserver.core.security.oauth2.exception.AuthBadRequestException;
import dev.hiok.portfoliosocialauthserver.core.utils.CookieUtils;

@Component
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  
  @Autowired
  private CustomOAuth2AuthorizationRequestRepository customOAuth2AuthorizationRequestRepository;

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private TokenProvider tokenProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    Authentication authentication) throws IOException, ServletException {

    String redirectUri = CookieUtils.getCookie(request, 
      CustomOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
        .map(Cookie::getValue).orElse(getDefaultTargetUrl());

    if (!isAuthorizedRedirectUri(redirectUri)) {
      throw new AuthBadRequestException(
        "Unauthorized Redirect URI and can't proceed with the authentication");
    }

    String token = tokenProvider.createToken(authentication);

    String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
      .queryParam("token", token)
      .build().toUriString();

    if (response.isCommitted()) {
      logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
      return;
    }

    clearAuthenticationAttributes(request);
    customOAuth2AuthorizationRequestRepository.removeAuthorizationRequest(request, response);

    getRedirectStrategy().sendRedirect(request, response, targetUrl);  
  }

  private boolean isAuthorizedRedirectUri(String redirectUri) {
    URI clientRedirectUri = URI.create(redirectUri);

    return appProperties.getOauth2().getAuthorizedRedirectUris().stream()
      .anyMatch(authorizedRedirectUri -> {
        URI authorizedUri = URI.create(authorizedRedirectUri);
        if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) 
          && authorizedUri.getPort() == clientRedirectUri.getPort()) {
            return true;
        }
        return false;
      });
  }
}
