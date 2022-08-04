package dev.hiok.portfoliosocialauthserver.domain.model;

import lombok.Getter;

@Getter
public enum RoleType {
  
  ROLE_USER (1l, "ROLE_USER", "role user"),
  ROLE_ADMIN (2l, "ROLE_ADMIN", "role admin user");
  
  private Long id;
  private String name;
  private String description;
  
  private RoleType(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

}
