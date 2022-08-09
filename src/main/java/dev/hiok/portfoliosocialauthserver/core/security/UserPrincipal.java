package dev.hiok.portfoliosocialauthserver.core.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import dev.hiok.portfoliosocialauthserver.domain.model.Role;
import dev.hiok.portfoliosocialauthserver.domain.model.User;
import lombok.Getter;

@Getter
public class UserPrincipal implements OAuth2User {

  private Map<String, Object> attributes;
  private Collection<? extends GrantedAuthority> authorities;
  private Long id;
  private String email;

  private UserPrincipal(Map<String, Object> attributes,
    Collection<? extends GrantedAuthority> authorities, Long id, String email) {
    this.attributes = attributes;
    this.authorities = authorities;
    this.id = id;
    this.email = email;
  }

  public static UserPrincipal create(User user, Map<String, Object> attributes) {
	Set<Role> userRoles = new HashSet<>();
	user.getGroups().stream().forEach(group -> userRoles.addAll(group.getRoles()));
    List<GrantedAuthority> authorities = userRoles.stream()
      .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

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
