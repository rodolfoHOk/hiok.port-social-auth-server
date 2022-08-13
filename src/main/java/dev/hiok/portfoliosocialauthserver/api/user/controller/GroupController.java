package dev.hiok.portfoliosocialauthserver.api.user.controller;

import java.util.List;

import javax.validation.Valid;

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

import dev.hiok.portfoliosocialauthserver.api.user.assembler.GroupInputDisassembler;
import dev.hiok.portfoliosocialauthserver.api.user.assembler.GroupResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.dto.request.GroupInputRequest;
import dev.hiok.portfoliosocialauthserver.api.user.dto.response.GroupResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.Group;
import dev.hiok.portfoliosocialauthserver.domain.repository.GroupRepository;
import dev.hiok.portfoliosocialauthserver.domain.service.GroupRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupController {
	
	private final GroupRepository groupRepository;
	private final GroupRegistrationService groupRegistrationService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
	public List<GroupResponse> getAllGroups() {
		List<Group> groups = groupRepository.findAll();
		return GroupResponseAssembler.toCollectionModel(groups);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
	public GroupResponse getGroupById(@PathVariable Long id) {
		Group group = groupRegistrationService.getById(id);
		return GroupResponseAssembler.toModel(group);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public GroupResponse createGroup(@RequestBody @Valid GroupInputRequest groupInput) {
		Group newGroup = GroupInputDisassembler.toEntity(groupInput);
		Group savedGroup = groupRegistrationService.save(newGroup);
		return GroupResponseAssembler.toModel(savedGroup);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public GroupResponse updateGroup(@PathVariable Long id, @RequestBody @Valid GroupInputRequest groupInput) {
		Group existingGroup = groupRegistrationService.getById(id);
		GroupInputDisassembler.copyToEntity(existingGroup, groupInput);
		Group updatedGroup = groupRegistrationService.save(existingGroup);
		return GroupResponseAssembler.toModel(updatedGroup);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_WRITE')")
	public void deleteGroup(@PathVariable Long id) {
		groupRegistrationService.remove(id);
	}

}
