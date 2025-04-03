package com.belvinard.blog_api.responses;

public class MyErrorResponses {
    private String status;
    private String message;

    // No-args constructor (required by JPA/Jackson in some cases)
    public MyErrorResponses() {
    }

    // All-args constructor
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