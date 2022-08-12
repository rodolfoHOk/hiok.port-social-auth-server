package dev.hiok.portfoliosocialauthserver.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hiok.portfoliosocialauthserver.domain.exception.EntityInUseException;
import dev.hiok.portfoliosocialauthserver.domain.exception.ResourceNotFoundException;
import dev.hiok.portfoliosocialauthserver.domain.model.Role;
import dev.hiok.portfoliosocialauthserver.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleRegistrationService {
	
	private final RoleRepository roleRepository;
	
	public Role getById(Long id) {
		return roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
	}
	
	@Transactional
	public Role save(Role role) {
		return roleRepository.save(role);
	}
	
	@Transactional
	public void remove(Long roleId) {
		try {
			roleRepository.deleteById(roleId);
			roleRepository.flush();
		} catch (EmptyResultDataAccessException ex) {
			throw new ResourceNotFoundException("Role not found with id: " + roleId);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityInUseException("Role is in use and cannot be removed");
		}
	}
	
}
