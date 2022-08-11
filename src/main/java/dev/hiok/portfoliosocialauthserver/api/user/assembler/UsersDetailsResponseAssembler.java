package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import dev.hiok.portfoliosocialauthserver.api.user.represention.UserDetailsResponse;
import dev.hiok.portfoliosocialauthserver.api.user.represention.UsersDetailsResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.User;

public class UsersDetailsResponseAssembler {

	public static UsersDetailsResponse toModel(Page<User> pagedUsers) {
		List<UserDetailsResponse> content = pagedUsers.getContent().stream()
				.map(user -> UserDetailsResponseAssembler.toModel(user)).collect(Collectors.toList());
		var response = new UsersDetailsResponse();
		response.setContent(content);
		response.setTotalElements(pagedUsers.getTotalElements());
		response.setTotalPages(pagedUsers.getTotalPages());
		response.setSize(pagedUsers.getSize());
		response.setNumber(pagedUsers.getNumber());
		return response;
	}
	
}
