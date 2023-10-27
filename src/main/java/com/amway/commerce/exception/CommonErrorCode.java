package com.amway.commerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 415-沿用现有状态吗
 * 470-499 业务异常分类
 * 570-599 非业务异常分类
 */
@Getter
@AllArgsConstructor
public enum CommonErrorCode implements IErrorCode{

    SUCCESS("0","Success"),
    CLIENT_UNSUPPORTED_MEDIA_TYPE("990102P001", "Unsupported Media Type"),
    CLIENT_PARAMETER_PARSE_ERROR("990102P002","Incorrect parameter parsing"),
    CLIENT_JSON_PARSE_ERROR("990102P003","Incorrect json parsing"),
    CLIENT_METHOD_ERROR("990102P004","RequestMethodNotSupported"),
    SYSTEM_RUNTIME_ERROR("990102U001","System Runtime Error"),
    SYSTEM_EXTENSION_ERROR("990102U002","ExtensionPointBizException"),
    SYSTEM_OTHER_ERROR("990102U999","System Other Error");

    private final String code;
    private final String message;

}
