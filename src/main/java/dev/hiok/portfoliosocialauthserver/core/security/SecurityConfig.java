package dev.hiok.portfoliosocialauthserver.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authServerFilterChain(HttpSecurity http) throws Exception {
    http
      .cors().and()
      .csrf().disable()
      .exceptionHandling(exceptionHandling -> exceptionHandling
        .authenticationEntryPoint(new RestAuthenticationEntryPoint()))
      .authorizeHttpRequests(requests -> requests
        .requestMatchers("/oauth2/**", "/.well-known/jwks.json").permitAll()
        .anyRequest().authenticated()
      )
      .oauth2Login(oauth2 -> oauth2
        .authorizationEndpoint(authorization -> authorization
          .baseUri("/oauth2/authorize")
          .authorizationRequestRepository(customOAuth2AuthorizationRequestRepository)
        )
        .redirectionEndpoint(redirection -> redirection
          .baseUri("/oauth2/callback/*")
        )
        .userInfoEndpoint(userInfo -> userInfo
          .userService(customOAuth2UserService)
        )
        .successHandler(oauth2AuthenticationSuccessHandler)
        .failureHandler(oAuth2AuthenticationFailureHandler)
      );

    return http.build();
  }

  @Bean
  @Order
  public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(requests -> requests
        .requestMatchers("/user/**", "/users/**", "/groups/**", "/roles/**").authenticated()
        .anyRequest().authenticated()
      )
      .oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwt -> jwt
          .jwtAuthenticationConverter(jwtAuthenticationConverter())
        )
      );

    return http.build();
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
