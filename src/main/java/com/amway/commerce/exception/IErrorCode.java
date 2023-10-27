package com.amway.commerce.exception;

import java.io.Serializable;

public interface IErrorCode extends Serializable {

        /**
        * 消息
        * @return String
        */
        String getMessage();

        /**
        * 状态码
        * @return String
        */
        String getCode();
}
