package com.belvinard.blog_api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MyErrorResponses {
    private String code;
    private String message;

    public MyErrorResponses() {
    }

    // Add this convenience constructor if you want to keep using the two-parameter version
    public MyErrorResponses(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
