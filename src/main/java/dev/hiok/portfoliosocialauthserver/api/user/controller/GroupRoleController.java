package dev.hiok.portfoliosocialauthserver.api.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.hiok.portfoliosocialauthserver.api.user.assembler.RoleResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.dto.response.RoleResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.Group;
import dev.hiok.portfoliosocialauthserver.domain.model.Role;
import dev.hiok.portfoliosocialauthserver.domain.service.GroupRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/groups/{groupId}/roles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupRoleController {
	
	private final GroupRegistrationService groupRegistrationService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
	public List<RoleResponse> getRolesByGroupId(@PathVariable Long groupId) {
		Group group = groupRegistrationService.getById(groupId);
		Set<Role> roles = group.getRoles();
		return RoleResponseAssembler.toCollectionModel(new ArrayList<>(roles));
	}

	@PutMapping("/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public void associateGroupAndRole(@PathVariable Long groupId, @PathVariable Long roleId) {
		groupRegistrationService.associateRole(groupId, roleId);
	}
	
	@DeleteMapping("/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public void desassociateGroupAndRole(@PathVariable Long groupId, @PathVariable Long roleId) {
		groupRegistrationService.desassociateRole(groupId, roleId);
	}
	
}
