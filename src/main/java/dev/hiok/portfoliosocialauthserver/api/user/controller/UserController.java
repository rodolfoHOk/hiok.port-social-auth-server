package dev.hiok.portfoliosocialauthserver.api.user.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hiok.portfoliosocialauthserver.api.user.assembler.UserDetailsResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.assembler.UserResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.assembler.UsersDetailsResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.represention.UserDetailsResponse;
import dev.hiok.portfoliosocialauthserver.api.user.represention.UserResponse;
import dev.hiok.portfoliosocialauthserver.api.user.represention.UsersResponse;
import dev.hiok.portfoliosocialauthserver.core.security.TokenProvider;
import dev.hiok.portfoliosocialauthserver.domain.exception.ResourceNotFoundException;
import dev.hiok.portfoliosocialauthserver.domain.model.User;
import dev.hiok.portfoliosocialauthserver.domain.repository.UserRepository;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenProvider tokenProvider;

  @PreAuthorize("hasAuthority('ROLE_USER') and hasAuthority('SCOPE_READ')")
  @GetMapping("/user/me")
  public UserResponse getUserInfo(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");
    String token = bearer.substring(7);
    Long userId = tokenProvider.getUserIdFromToken(token);
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("Resource user info not found"));
    return UserResponseAssembler.toModel(user);
  }
  
  @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
  @GetMapping("/users")
  public UsersResponse getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
	  Page<User> users = userRepository.findAll(pageable);
	  return UsersDetailsResponseAssembler.toModel(users);
  }
  
  @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
  @GetMapping("/users/{id}")
  public ResponseEntity<UserDetailsResponse> getUserById(@PathVariable Long id) {
	  Optional<User> user = userRepository.findById(id);
	  if (user.isEmpty()) {
		  return ResponseEntity.notFound().build();
	  }
	  return ResponseEntity.ok(UserDetailsResponseAssembler.toModel(user.get()));
  }
  
  @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
  @GetMapping("/users/email")
  public ResponseEntity<UserDetailsResponse> getUserByEmail(@RequestParam String email) {
	  Optional<User> user = userRepository.findByEmail(email);
	  if (user.isEmpty()) {
		  return ResponseEntity.notFound().build();
	  }
	  return ResponseEntity.ok(UserDetailsResponseAssembler.toModel(user.get()));
  }

}
