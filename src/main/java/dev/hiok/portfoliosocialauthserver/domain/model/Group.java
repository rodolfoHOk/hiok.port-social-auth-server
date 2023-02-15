package dev.hiok.portfoliosocialauthserver.domain.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "groups")
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@ManyToMany
	@JoinTable(name = "group_roles", joinColumns = @JoinColumn(name = "group_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	public void addRole(Role role) {
		getRoles().add(role);
	}
	
	public void removeRole(Role role) {
		getRoles().remove(role);
	}
	
	public static Group getGroup(CommonsGroup commonGroup) {
		Group group = new Group();
		group.setId(commonGroup.getId());
		group.setName(commonGroup.getName());
		group.setDescription(commonGroup.getDescription());
		group.setRoles(commonGroup.getRoles());
		return group;
	}
	
}
