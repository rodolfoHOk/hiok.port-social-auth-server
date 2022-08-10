package dev.hiok.portfoliosocialauthserver.api.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hiok.portfoliosocialauthserver.api.user.assembler.UserDetailsResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.assembler.UserResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.represention.UserDetailsResponse;
import dev.hiok.portfoliosocialauthserver.api.user.represention.UserResponse;
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
  public List<UserDetailsResponse> getAllUsers() {
	  List<User> users = userRepository.findAll();
	  return UserDetailsResponseAssembler.toCollectionModel(users);
  }

}
