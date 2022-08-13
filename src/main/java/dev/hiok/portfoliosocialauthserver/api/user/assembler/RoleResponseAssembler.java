package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import java.util.List;
import java.util.stream.Collectors;

import dev.hiok.portfoliosocialauthserver.api.user.dto.response.RoleResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.Role;

public class RoleResponseAssembler {

	public static RoleResponse toModel (Role role) {
		var response = new RoleResponse();
		response.setId(role.getId());
		response.setName(role.getName());
		response.setDescription(role.getDescription());
		return response;
	}
	
	public static List<RoleResponse> toCollectionModel (List<Role> roles) {
		return roles.stream().map(role -> toModel(role)).collect(Collectors.toList());
	}
	
}
