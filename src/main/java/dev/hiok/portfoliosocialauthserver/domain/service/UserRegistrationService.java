package dev.hiok.portfoliosocialauthserver.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hiok.portfoliosocialauthserver.domain.exception.ResourceNotFoundException;
import dev.hiok.portfoliosocialauthserver.domain.model.Group;
import dev.hiok.portfoliosocialauthserver.domain.model.User;
import dev.hiok.portfoliosocialauthserver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRegistrationService {

	private final UserRepository userRepository;
	private final GroupRegistrationService groupRegistrationService;
	
	public User getById(UUID id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}
	
	public User getByEmail(String email) {
		return userRepository.findByEmail(email)
	  		.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
	}
	
	@Transactional
	public void associateGroup(UUID userId, Long groupId) {
		User user = getById(userId);
		Group group = groupRegistrationService.getById(groupId);
		user.addGroup(group);
	}
	
	@Transactional
	public void desassociateGroup(UUID userId, Long groupId) {
		User user = getById(userId);
		Group group = groupRegistrationService.getById(groupId);
		user.removeGroup(group);
	}
	
}
