package dev.hiok.portfoliosocialauthserver.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleInputRequest {
	
	@NotBlank
	@Size(min = 6, max = 255)
	private String name;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
}
