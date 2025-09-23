package com.example.authentication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ErrorModel implements Serializable {
    private Long statusCode;
    private String message;

    // Private constructor thực sự
    private ErrorModel(Long statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // Static factory methods
    public static ErrorModel of(Long statusCode, String message) {
        return new ErrorModel(statusCode, message);
    }

    public static ErrorModel of(Long statusCode) {
        return new ErrorModel(statusCode, "");
    }

    public static ErrorModel of(String message) {
        return new ErrorModel(null, message);
    }

}
