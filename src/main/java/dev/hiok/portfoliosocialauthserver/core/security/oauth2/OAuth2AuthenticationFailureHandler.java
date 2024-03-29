package dev.hiok.portfoliosocialauthserver.core.security.oauth2;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import dev.hiok.portfoliosocialauthserver.core.utils.CookieUtils;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Autowired
  private CustomOAuth2AuthorizationRequestRepository customOAuth2AuthorizationRequestRepository;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    
    String redirectUri = CookieUtils.getCookie(request, 
      CustomOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
        .map(Cookie::getValue)
        .orElse("/");
    
    String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
      .queryParam("error", exception.getLocalizedMessage())
      .build().toUriString();

    customOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
  
}
