package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import dev.hiok.portfoliosocialauthserver.api.user.dto.response.UserResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.User;

public class UserResponseAssembler {
  
  public static UserResponse toModel(User user) {
    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setName(user.getName());
    userResponse.setEmail(user.getEmail());
    userResponse.setImageUrl(user.getImageUrl());
    return userResponse;
  }
  
}
