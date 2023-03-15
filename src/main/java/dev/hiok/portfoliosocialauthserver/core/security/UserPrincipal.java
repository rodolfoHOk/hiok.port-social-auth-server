package dev.hiok.portfoliosocialauthserver.core.security;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import dev.hiok.portfoliosocialauthserver.domain.model.User;
import lombok.Getter;

@Getter
public class UserPrincipal implements OAuth2User {

  private Map<String, Object> attributes;
  private Collection<? extends GrantedAuthority> authorities;
  private UUID id;
  private String email;

  private UserPrincipal(Map<String, Object> attributes,
    Collection<? extends GrantedAuthority> authorities, UUID id, String email) {
    this.attributes = attributes;
    this.authorities = authorities;
    this.id = id;
    this.email = email;
  }

  public static UserPrincipal create(User user, Map<String, Object> attributes,
  		Collection<? extends GrantedAuthority> authorities) {
  	return new UserPrincipal(attributes, authorities, user.getId(), user.getEmail());
  }

  @Override
  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getName() {
    return this.email;
  }
  
}
