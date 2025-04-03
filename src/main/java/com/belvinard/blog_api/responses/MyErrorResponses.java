package com.belvinard.blog_api.responses;

import java.util.Map;

public class MyErrorResponses {
    private String status;
    private String message;
    //private Map<String, String> errors;

    // No-args constructor (required by JPA/Jackson in some cases)
    public MyErrorResponses() {
    }

    public MyErrorResponses(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}