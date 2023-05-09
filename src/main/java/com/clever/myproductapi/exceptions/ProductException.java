package com.clever.myproductapi.exceptions;

import java.io.Serial;

public class ProductException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -5490934770795422671L;

    public ProductException(String message) {
        super(message);
    }
}
