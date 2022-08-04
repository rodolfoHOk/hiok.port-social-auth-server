package dev.hiok.portfoliosocialauthserver.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
