package dev.hiok.portfoliosocialauthserver.api.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import dev.hiok.portfoliosocialauthserver.domain.model.User;
import dev.hiok.portfoliosocialauthserver.domain.repository.UserRepository;
import dev.hiok.portfoliosocialauthserver.domain.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserRepository userRepository;
  private final UserRegistrationService userRegistrationService;
  private final TokenProvider tokenProvider;

	@PreAuthorize("hasAuthority('ROLE_USER') and hasAuthority('SCOPE_READ')")
  @GetMapping("/user/me")
  public UserResponse getUserInfo(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");
    String token = bearer.substring(7);
    Long userId = tokenProvider.getUserIdFromToken(token);
    User user = userRegistrationService.getById(userId);
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
  public UserDetailsResponse getUserById(@PathVariable Long id) {
	  User user = userRegistrationService.getById(id);
	  return UserDetailsResponseAssembler.toModel(user);
  }
  
  @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('SCOPE_READ')")
  @GetMapping("/users/email")
  public UserDetailsResponse getUserByEmail(@RequestParam String email) {
	  User user = userRegistrationService.getByEmail(email);
	  return UserDetailsResponseAssembler.toModel(user);
  }

}
