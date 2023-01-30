package com.winterfoodies.ceo.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class BasicException extends RuntimeException{
    private String redirect;

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
