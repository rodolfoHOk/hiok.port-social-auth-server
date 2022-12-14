package dev.hiok.portfoliosocialauthserver.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
  @Order(1)
  public SecurityFilterChain authServerFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .cors().and()
      .csrf().disable()
      .exceptionHandling()
        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .and()
      .requestMatchers()
        .antMatchers("/oauth2/**")
        .antMatchers("/.well-known/jwks.json")
        .and()
      .authorizeRequests()
        .antMatchers("/oauth2/**").permitAll()
        .antMatchers("/.well-known/jwks.json").permitAll()
        .anyRequest().authenticated()
        .and()
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
        .failureHandler(oAuth2AuthenticationFailureHandler);
    
    return httpSecurity.build();    
  }

  @Bean
  @Order(2)
  public SecurityFilterChain resourceServerFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .cors().and()
      .csrf().disable()
      .requestMatchers()
        .antMatchers("/user/**")
        .antMatchers("/users/**")
        .antMatchers("/groups/**")
        .antMatchers("/roles/**")
        .and()
      .authorizeRequests()
        .anyRequest().authenticated()
        .and()
      .oauth2ResourceServer()
        .jwt();

    return httpSecurity.build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
    grantedAuthoritiesConverter.setAuthorityPrefix("");

    var jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

}
