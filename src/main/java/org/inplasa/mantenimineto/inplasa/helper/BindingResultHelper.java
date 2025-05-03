package org.inplasa.mantenimineto.inplasa.helper;

import java.util.HashMap;
import java.util.Map;

import org.inplasa.mantenimineto.inplasa.exception.BindingResultException;
import org.springframework.validation.BindingResult;

public class BindingResultHelper {
    private BindingResultHelper(){
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada");
    }

    public static Map<String, String> extractErrors(BindingResult bindingResult){
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    public static void validateBindingResult(BindingResult bindingResult, String errorCode){
        if (bindingResult.hasErrors()) {
            throw new BindingResultException(errorCode, extractErrors(bindingResult));
        }
    }
}
