package br.com.file.service.error;

import java.util.List;

public class ErrorResponse {
	
	private List<ErrorField> errors;
	 
    public ErrorResponse(int size) {
    	
    }

	public List<ErrorField> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorField> errors) {
		this.errors = errors;
	}

}
