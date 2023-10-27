package com.amway.commerce.exception;

import lombok.Data;

import java.util.List;

@Data
public class CommonException extends RuntimeException{

    private IErrorCode iErrorCode;

    private String errorCode;

    private List<String> errorParams;

    private String errorMessage;

    public CommonException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public CommonException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonException(String errorCode, String errorMessage, List<String> errorParams) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorParams = errorParams;
        this.errorMessage = errorMessage;
    }

    public CommonException(IErrorCode iErrorCode){
        super(iErrorCode.getMessage());
        this.iErrorCode = iErrorCode;
        this.errorCode = iErrorCode.getCode();
        this.errorMessage = iErrorCode.getMessage();
    }

}
