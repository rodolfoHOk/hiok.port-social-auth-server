package dev.hiok.portfoliosocialauthserver.api.user.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsResponse {

	private UUID id;
	private String name;
	private String email;
	private String imageUrl;
	private List<SocialIdResponse> socialIds = new ArrayList<>();
	private Set<GroupNameResponse> groups;
	
}
