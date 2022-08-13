package dev.hiok.portfoliosocialauthserver.api.user.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupInputRequest {

	@NotBlank
	@Size(min = 3, max = 255)
	private String name;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
}
