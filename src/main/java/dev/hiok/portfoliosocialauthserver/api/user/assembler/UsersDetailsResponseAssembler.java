package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import dev.hiok.portfoliosocialauthserver.api.user.represention.UserResponse;
import dev.hiok.portfoliosocialauthserver.api.user.represention.UsersResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.User;

public class UsersDetailsResponseAssembler {

	public static UsersResponse toModel(Page<User> pagedUsers) {
		List<UserResponse> content = pagedUsers.getContent().stream()
				.map(user -> UserResponseAssembler.toModel(user)).collect(Collectors.toList());
		var response = new UsersResponse();
		response.setContent(content);
		response.setTotalElements(pagedUsers.getTotalElements());
		response.setTotalPages(pagedUsers.getTotalPages());
		response.setSize(pagedUsers.getSize());
		response.setNumber(pagedUsers.getNumber());
		return response;
	}
	
}
