package com.clever.myproductapi.exceptions;

import java.io.Serial;

public class CategoryException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -5871574332367780242L;

    public CategoryException(String message) {
        super(message);
    }
}
