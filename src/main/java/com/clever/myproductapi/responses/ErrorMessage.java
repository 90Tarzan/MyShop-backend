package com.clever.myproductapi.responses;

import lombok.Data;

import java.util.Date;
@Data

public class ErrorMessage {
    private Date timeStamp;
    private String message;

    public ErrorMessage(Date timeStamp, String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }
}
