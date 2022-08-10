package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import java.util.List;
import java.util.stream.Collectors;

import dev.hiok.portfoliosocialauthserver.api.user.represention.UserDetailsResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.User;

public class UserDetailsResponseAssembler {

	public static UserDetailsResponse toModel (User user) {
		var userDetails = new UserDetailsResponse();
		userDetails.setId(user.getId());
		userDetails.setName(user.getName());
		userDetails.setEmail(user.getEmail());
		userDetails.setImageUrl(user.getImageUrl());
		userDetails.setProvider(user.getProvider());
		userDetails.setGroups(user.getGroups());
		return userDetails;
	}
	
	public static List<UserDetailsResponse> toCollectionModel (List<User> users) {
		return users.stream().map(user -> toModel(user)).collect(Collectors.toList());
	}
	
}
