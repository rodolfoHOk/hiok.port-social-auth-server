package dev.hiok.portfoliosocialauthserver.api.user.represention;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersResponse {

	List<UserResponse> content;
	private Long totalElements;
	private int totalPages;
	private int size;
	private int number;
	
}
