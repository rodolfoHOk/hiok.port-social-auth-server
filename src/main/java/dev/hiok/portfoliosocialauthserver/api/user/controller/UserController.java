package dev.hiok.portfoliosocialauthserver.api.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hiok.portfoliosocialauthserver.api.user.assembler.UserResponseAssembler;
import dev.hiok.portfoliosocialauthserver.api.user.represention.UserResponse;
import dev.hiok.portfoliosocialauthserver.core.security.TokenProvider;
import dev.hiok.portfoliosocialauthserver.domain.exception.ResourceNotFoundException;
import dev.hiok.portfoliosocialauthserver.domain.model.User;
import dev.hiok.portfoliosocialauthserver.domain.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenProvider tokenProvider;

  @GetMapping("/me")
  public UserResponse getUserInfo(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");
    String token = bearer.substring(7);
    Long userId = tokenProvider.getUserIdFromToken(token);
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("Resource user info not found"));
    return UserResponseAssembler.toModel(user);
  }

}
