package dev.hiok.portfoliosocialauthserver.api.user.represention.Input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleInputRequest {
	
	@NotBlank
	@Size(min = 6, max = 255)
	private String name;
	
	@NotBlank
	private String description;
	
}
