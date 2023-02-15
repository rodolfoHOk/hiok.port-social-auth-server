package dev.hiok.portfoliosocialauthserver.domain.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  private String imageUrl;

  @Enumerated(EnumType.STRING)
  private AuthProvider provider;

  private String providerId;

  @ManyToMany
  @JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<Group> groups = new HashSet<>();

  public void addGroup(Group group) {
	  getGroups().add(group);
  }
  
  public void removeGroup(Group group) {
  	getGroups().remove(group);
  }

}
