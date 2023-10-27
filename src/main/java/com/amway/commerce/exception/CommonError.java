package com.amway.commerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Jason.Hu
 * @date: 2023-09-07
 */
@Getter
@AllArgsConstructor
public enum CommonError {

    NotNull("参数不能为空"),

    SizeWrong("参数长度不符合要求"),

    DividedByZero("被除数为0"),

    ContentFormatInvalid("内容格式错误"),

    ArrayIndexOutOfBoundsException("数组越界"),

    ClassMatchError("类型不匹配"),

    ParamLengthInvalid("参数长度不正确"),

    ParamValueExceedRange("参数值超过指定范围"),

    ListContainNullValue("集合中存在空值"),

    ;

    private String message;


}
