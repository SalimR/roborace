package net.skhome.common.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {
	
	private final String resourceName;
	
	public ResourceNotFoundException(final String resourceName) {
		this.resourceName = resourceName;
	}
	
	public String getUsername() {
		return resourceName;
	}
	
}