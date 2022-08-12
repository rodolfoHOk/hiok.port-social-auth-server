package dev.hiok.portfoliosocialauthserver.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
		roles.add(role);
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
