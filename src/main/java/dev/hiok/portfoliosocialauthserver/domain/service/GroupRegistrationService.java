package dev.hiok.portfoliosocialauthserver.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hiok.portfoliosocialauthserver.domain.exception.EntityInUseException;
import dev.hiok.portfoliosocialauthserver.domain.exception.ResourceNotFoundException;
import dev.hiok.portfoliosocialauthserver.domain.model.Group;
import dev.hiok.portfoliosocialauthserver.domain.model.Role;
import dev.hiok.portfoliosocialauthserver.domain.repository.GroupRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupRegistrationService {

	private final GroupRepository groupRepository;
	private final RoleRegistrationService roleRegistrationService;
	
	public Group getById(Long id) {
		return groupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + id));
	}
	
	@Transactional
	public Group save(Group group) {
		return groupRepository.save(group);
	}
	
	@Transactional
	public void remove(Long groupId) {;
		try {
			groupRepository.deleteById(groupId);
			groupRepository.flush();
		} catch (EmptyResultDataAccessException ex) {
			throw new ResourceNotFoundException("Group not found with id: " + groupId);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityInUseException("Group is in use and cannot be removed");
		}
	}
	
	@Transactional
	public void associateRole(Long groupId, Long roleId) {
		Group group = getById(groupId);
		Role role = roleRegistrationService.getById(roleId);
		group.addRole(role);
	}
	
	@Transactional
	public void desassociateRole(Long groupId, Long roleId) {
		Group group = getById(groupId);
		Role role = roleRegistrationService.getById(roleId);
		group.removeRole(role);
	}
	
}
