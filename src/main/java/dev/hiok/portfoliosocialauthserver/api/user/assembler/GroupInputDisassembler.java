package dev.hiok.portfoliosocialauthserver.api.user.assembler;

import dev.hiok.portfoliosocialauthserver.api.user.dto.request.GroupInputRequest;
import dev.hiok.portfoliosocialauthserver.domain.model.Group;

public class GroupInputDisassembler {

	public static Group toEntity (GroupInputRequest groupInput) {
		var group = new Group();
		group.setName(groupInput.getName());
		group.setDescription(groupInput.getDescription());
		return group;
	}
	
	public static void copyToEntity (Group group, GroupInputRequest groupInput) {
		group.setName(groupInput.getName());
		group.setDescription(groupInput.getDescription());
	}
	
}
