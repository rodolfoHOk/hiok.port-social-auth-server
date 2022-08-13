package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import java.util.List;
import java.util.stream.Collectors;

import dev.hiok.portfoliosocialauthserver.api.user.dto.response.GroupResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.Group;

public class GroupResponseAssembler {

	public static GroupResponse toModel (Group group) {
		var response = new GroupResponse();
		response.setId(group.getId());
		response.setName(group.getName());
		response.setDescription(group.getDescription());
		return response;
	}
	
	public static List<GroupResponse> toCollectionModel (List<Group> groups) {
		return groups.stream()
				.map(group -> toModel(group)).collect(Collectors.toList());
	}
	
}
