package dev.hiok.portfoliosocialauthserver.domain.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum CommonsGroup {
	
	COMMON_USER(1l, "COMMON_USER", "common user group", Stream.of(Role.getRole(RoleType.ROLE_USER),
			Role.getRole(RoleType.SCOPE_READ)).collect(Collectors.toSet())),
	COMMON_ADMIN(2l, "COMMON_ADMIN", "common admin group", Stream.of(Role.getRole(RoleType.ROLE_ADMIN),
			Role.getRole(RoleType.SCOPE_READ), Role.getRole(RoleType.SCOPE_WRITE)).collect(Collectors.toSet())),
	COMMON_CLIENT(3l, "COMMON_CLIENT", "common client group", Stream.of(Role.getRole(RoleType.ROLE_CLIENT),
			Role.getRole(RoleType.SCOPE_READ), Role.getRole(RoleType.SCOPE_WRITE)).collect(Collectors.toSet()));
	
	private Long id;
	private String name;
	private String description;
	private Set<Role> roles;
	
	private CommonsGroup(Long id, String name, String description, Set<Role> roles) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.roles = roles;
	}
	
}
