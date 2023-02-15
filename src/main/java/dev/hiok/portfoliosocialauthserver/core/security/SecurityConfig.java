package dev.hiok.portfoliosocialauthserver.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import dev.hiok.portfoliosocialauthserver.core.security.oauth2.CustomOAuth2AuthorizationRequestRepository;
import dev.hiok.portfoliosocialauthserver.core.security.oauth2.CustomOAuth2UserService;
import dev.hiok.portfoliosocialauthserver.core.security.oauth2.OAuth2AuthenticationFailureHandler;
import dev.hiok.portfoliosocialauthserver.core.security.oauth2.Oauth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  public CustomOAuth2AuthorizationRequestRepository customOAuth2AuthorizationRequestRepository;

  @Autowired
  private CustomOAuth2UserService customOAuth2UserService;

  @Autowired
  private Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

  @Autowired
  private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @Bean
  public SecurityFilterChain authServerFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .cors().and()
      .csrf().disable()
      .exceptionHandling()
        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .and()
      .authorizeRequests(requests -> {
        requests.requestMatchers("/oauth2/**", "/.well-known/jwks.json").permitAll();
      })
      .oauth2Login()
        .authorizationEndpoint()
          .baseUri("/oauth2/authorize")
          .authorizationRequestRepository(customOAuth2AuthorizationRequestRepository)
          .and()
        .redirectionEndpoint()
          .baseUri("/oauth2/callback/*")
          .and()
        .userInfoEndpoint()
          .userService(customOAuth2UserService)
          .and()
        .successHandler(oauth2AuthenticationSuccessHandler)
        .failureHandler(oAuth2AuthenticationFailureHandler)
        .and()
      .oauth2ResourceServer()
        .jwt()
          .jwtAuthenticationConverter(jwtAuthenticationConverter());

    return httpSecurity.build();
  }

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

}
