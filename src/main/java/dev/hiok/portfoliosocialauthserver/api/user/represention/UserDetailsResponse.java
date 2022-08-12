package dev.hiok.portfoliosocialauthserver.api.user.represention;

import java.util.Set;

import dev.hiok.portfoliosocialauthserver.domain.model.AuthProvider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsResponse {

	private long id;
	private String name;
	private String email;
	private String imageUrl;
	private AuthProvider provider;
	private Set<GroupNameResponse> groups;
	
}
