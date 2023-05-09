package com.clever.myproductapi.apiconfig.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {
    private final Random random = new SecureRandom();
    private final String alphaNum = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateStringId(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for(int i = 0; i < length; i++) {

            returnValue.append(alphaNum.charAt(random.nextInt(alphaNum.length())));
        }
        return new String(returnValue);
    }
}
