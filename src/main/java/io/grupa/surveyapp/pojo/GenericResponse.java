package io.grupa.surveyapp.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

public @Data class GenericResponse implements Response {

	private String message;
	private String code;
	private boolean success;


	public GenericResponse(String message) {
		super();
		this.message = message;
	}

	public GenericResponse(String message, boolean success, String error) {
		super();
		this.message = message;
		this.success = success;
		this.code = error;
	}



}
