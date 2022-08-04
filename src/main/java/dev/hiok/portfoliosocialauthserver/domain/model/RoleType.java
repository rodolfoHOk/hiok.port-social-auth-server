package dev.hiok.portfoliosocialauthserver.domain.model;

import lombok.Getter;

@Getter
public enum RoleType {
  
  ROLE_USER (1l, "ROLE_USER", "role user"),
  ROLE_ADMIN (2l, "ROLE_ADMIN", "role admin user"),
  SCOPE_READ (3l, "SCOPE_READ", "scope read only"),
  SCOPE_WRITE (4l, "SCOPE_WRITE", "scope write only");
  
  private Long id;
  private String name;
  private String description;
  
  private RoleType(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

}
