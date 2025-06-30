package com.customer_service.customer_service.ErrorHandler;

import java.time.LocalDateTime;


public class ErrorResponse {
    private String message;
    private LocalDateTime time;

    public ErrorResponse(LocalDateTime time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


}
