package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import java.util.Set;
import java.util.stream.Collectors;

import dev.hiok.portfoliosocialauthserver.api.user.represention.GroupNameResponse;
import dev.hiok.portfoliosocialauthserver.domain.model.Group;

public class GroupNameResponseAssembler {

	public static GroupNameResponse toModel(Group group) {
		var response = new GroupNameResponse();
		response.setName(group.getName());
		return response;
	}
	
	public static Set<GroupNameResponse> toCollectionModel(Set<Group> groups) {
		return groups.stream().map(group -> toModel(group)).collect(Collectors.toSet());
	}
	
}
