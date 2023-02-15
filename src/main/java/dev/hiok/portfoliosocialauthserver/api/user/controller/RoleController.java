package dev.hiok.portfoliosocialauthserver.api.user.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.hiok.portfoliosocialauthserver.api.user.assembler.RoleInputDisassembler;
import dev.hiok.portfoliosocialauthserver.api.user.assembler.RoleResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.dto.request.RoleInputRequest;
import dev.hiok.portfoliosocialauthserver.api.user.dto.response.RoleResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.Role;
import dev.hiok.portfoliosocialauthserver.domain.repository.RoleRepository;
import dev.hiok.portfoliosocialauthserver.domain.service.RoleRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

	private final RoleRepository roleRepository;
	private final RoleRegistrationService roleRegistrationService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
	public List<RoleResponse> getAllRoles() {
		List<Role> roles = roleRepository.findAll();
		return RoleResponseAssembler.toCollectionModel(roles);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
	public RoleResponse getRoleById(@PathVariable Long id) {
		Role role = roleRegistrationService.getById(id);
		return RoleResponseAssembler.toModel(role);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public RoleResponse createRole(@RequestBody @Valid RoleInputRequest roleInput) {
		Role newRole = RoleInputDisassembler.toEntity(roleInput);
		Role savedRole = roleRegistrationService.save(newRole);
		return RoleResponseAssembler.toModel(savedRole);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public RoleResponse updateRole(@PathVariable Long id, @RequestBody @Valid RoleInputRequest roleInput) {
		Role existingRole = roleRegistrationService.getById(id);
		RoleInputDisassembler.copyToEntity(existingRole, roleInput);
		Role updatedRole = roleRegistrationService.save(existingRole);
		return RoleResponseAssembler.toModel(updatedRole);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public void removeRole(@PathVariable Long id) {
		roleRegistrationService.remove(id);
	}
	
}
