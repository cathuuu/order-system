package com.example.authentication.exceptions;

import com.example.authentication.models.ErrorModel;
import lombok.Data;
import lombok.Getter;

@Data
public class AppException extends RuntimeException {
    @Getter
    private final ErrorModel errorModel;

    public AppException(String message) {

        super(message);
        errorModel = null;
    }
    public AppException(ErrorModel errorModel){

        super(errorModel.getMessage());
        this.errorModel = errorModel;
    }
    public static AppException of(ErrorModel errorModel){
        return new AppException(errorModel);
    }
}
