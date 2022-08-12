package dev.hiok.portfoliosocialauthserver.api.user.assembler;

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
		userDetails.setGroups(GroupNameResponseAssembler.toCollectionModel(user.getGroups()));
		return userDetails;
	}
	
}
