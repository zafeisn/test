package com.amway.commerce.http;

/**
 * 415-沿用现有状态吗
 * 470-499 业务异常分类
 * 570-599 非业务异常分类
 */
public enum CommonHttpStatus {

    CLIENT_UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    CLIENT_PARAMETER_PARSE_ERROR(470,"Incorrect parameter parsing"),
    CLIENT_JSON_PARSE_ERROR(471,"Incorrect json parsing"),
    CLIENT_METHOD_ERROR(472,"RequestMethodNotSupported"),
    CLIENT_BUSINESS_ERROR(480,"Business Error"),
    SYSTEM_RUNTIME_ERROR(570,"System Runtime Error"),
    SYSTEM_EXTENSION_ERROR(571,"ExtensionPointBizException"),
    SYSTEM_OTHER_ERROR(599,"System Other Error");

    private final int value;
    private final String reasonPhrase;

    CommonHttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }
    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

}
