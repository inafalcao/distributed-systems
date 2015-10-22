package br.com.file.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.file.service.model.RemoteFile;

@Component
public class RemoteFileValidator implements Validator {

	@Override
    public boolean supports(Class<?> clazz) {
        return RemoteFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	RemoteFile object = (RemoteFile) target;
        ValidationUtils.rejectIfEmpty(errors, "content", "remoteFile.content");
    }
}
