package com.amway.commerce.http;

import com.amway.commerce.exception.CommonErrorCode;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ResultResponse<T> {

    private String requestId;

    private String code = CommonErrorCode.SUCCESS.getCode();

    private String message;

    private Boolean success = true;

    private List<Object> msgData;

    private T data;

    public ResultResponse() {
        this.requestId = UUID.randomUUID().toString();
    }

    public ResultResponse(String reqId) {
        this.requestId = reqId;
    }
}

