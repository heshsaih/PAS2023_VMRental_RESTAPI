package com.example.vmrentalrest;

import com.example.vmrentalrest.exceptions.ErrorMessages;
import com.example.vmrentalrest.exceptions.IllegalOperationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomValidator {
    private final Validator validator;
    public void validate(Object object) {
        if(object == null) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.BODY_IS_NULL_MESSAGE);
        }
        var violations = validator.validate(object);
        if(violations.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            violations.forEach(violation -> {
                stringBuilder
                        .append("\n")
                        .append(violation.getMessage());
            });
            throw new IllegalOperationException(stringBuilder.toString());
        }
    }

}
