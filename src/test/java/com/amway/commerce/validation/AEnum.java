package com.amway.commerce.validation;

import lombok.AllArgsConstructor;

/**
 * @author: Jason.Hu
 * @date: 2023-08-07
 * @desc:
 */
public enum AEnum {
    A("a", 1),
    B("b", 2);
    private String name;
    private Integer id;

    AEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

}
