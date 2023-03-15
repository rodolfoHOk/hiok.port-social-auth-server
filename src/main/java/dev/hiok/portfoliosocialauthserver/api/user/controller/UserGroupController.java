package dev.hiok.portfoliosocialauthserver.api.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

import dev.hiok.portfoliosocialauthserver.api.user.assembler.GroupResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.dto.response.GroupResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.Group;
import dev.hiok.portfoliosocialauthserver.domain.model.User;
import dev.hiok.portfoliosocialauthserver.domain.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/{userId}/groups")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserGroupController {

	private final UserRegistrationService userRegistrationService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
	public List<GroupResponse> getGroupsByUser(@PathVariable String userId) {
		User user = userRegistrationService.getById(UUID.fromString(userId));
		Set<Group> groups = user.getGroups();
		return GroupResponseAssembler.toCollectionModel(new ArrayList<>(groups));
	}
	
	@PutMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public void associateUserAndGroup(@PathVariable String userId, @PathVariable Long groupId) {
		userRegistrationService.associateGroup(UUID.fromString(userId), groupId);
	}
	
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public void disassociateUserAndGroup(@PathVariable String userId, @PathVariable Long groupId) {
		userRegistrationService.desassociateGroup(UUID.fromString(userId), groupId);
	}
	
}
