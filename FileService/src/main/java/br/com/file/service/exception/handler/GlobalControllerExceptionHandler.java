package br.com.file.service.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.file.service.error.ErrorField;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends
		ResponseEntityExceptionHandler {
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<ErrorField> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        
        ErrorField error;
        for (FieldError fieldError : fieldErrors) {
            error = new ErrorField();
        	error.setField(fieldError.getField());
        	error.setMessage(fieldError.getDefaultMessage());
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
        	error = new ErrorField();
        	error.setField(objectError.getObjectName());
        	error.setMessage(objectError.getDefaultMessage());
            errors.add(error);
        }

        return new ResponseEntity<Object>(errors, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
