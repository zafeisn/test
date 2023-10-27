package com.amway.commerce.exception;

import lombok.Data;

import java.util.List;

@Data
public class CommonBusinessException extends RuntimeException{

    private IErrorCode iErrorCode;

    private String errorCode;

    private List<String> errorParams;

    private String errorMessage;

    public CommonBusinessException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public CommonBusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonBusinessException(String errorCode,  String errorMessage,List<String> errorParams) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorParams = errorParams;
        this.errorMessage = errorMessage;
    }

    public CommonBusinessException(IErrorCode iErrorCode){
        super(iErrorCode.getMessage());
        this.iErrorCode = iErrorCode;
        this.errorCode = iErrorCode.getCode();
        this.errorMessage = iErrorCode.getMessage();
    }

}
