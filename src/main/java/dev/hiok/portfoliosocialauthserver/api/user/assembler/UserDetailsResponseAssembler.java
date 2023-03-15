package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import dev.hiok.portfoliosocialauthserver.api.user.dto.response.SocialIdResponse;
import dev.hiok.portfoliosocialauthserver.api.user.dto.response.UserDetailsResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.User;

import java.util.stream.Collectors;

public class UserDetailsResponseAssembler {

	public static UserDetailsResponse toModel (User user) {
		var userDetails = new UserDetailsResponse();
		userDetails.setId(user.getId());
		userDetails.setName(user.getName());
		userDetails.setEmail(user.getEmail());
		userDetails.setImageUrl(user.getImageUrl());
		userDetails.setSocialIds(user.getSocialIds().stream()
			.map(socialId -> new SocialIdResponse(socialId.getProvider(), socialId.getSocialId()))
			.collect(Collectors.toList())
		);
		userDetails.setGroups(GroupNameResponseAssembler.toCollectionModel(user.getGroups()));
		return userDetails;
	}
	
}
