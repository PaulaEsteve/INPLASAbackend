package org.inplasa.mantenimineto.inplasa.exception;

import lombok.Getter;

@Getter
public class EntityIllegalArgumentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public EntityIllegalArgumentException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
