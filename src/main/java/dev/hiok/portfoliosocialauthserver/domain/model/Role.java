package dev.hiok.portfoliosocialauthserver.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  public static Role getRole(RoleType roleType) {
    Role role = new Role();
    role.setId(roleType.getId());
    role.setName(roleType.getName());
    role.setDescription(roleType.getDescription());
    return role;
  }

}
