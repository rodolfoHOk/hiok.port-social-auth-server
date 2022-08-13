package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import dev.hiok.portfoliosocialauthserver.api.user.dto.request.RoleInputRequest;
import dev.hiok.portfoliosocialauthserver.domain.model.Role;

public class RoleInputDisassembler {

	public static Role toEntity (RoleInputRequest roleInput) {
		var role = new Role();
		role.setName(roleInput.getName());
		role.setDescription(roleInput.getDescription());
		return role;
	}
	
	public static void copyToEntity (Role role, RoleInputRequest roleInput) {
		role.setName(roleInput.getName());
		role.setDescription(roleInput.getDescription());
	}
	
}
