package com.amway.commerce.serialization;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * @author: Jason.Hu
 * @date: 2023-08-11
 * @desc:
 */
@Data
public class B {
    private String desc;
    private Integer id;
    private OffsetDateTime offsetDateTime;
    private Date date;
    private LocalDateTime localDateTime;
}
