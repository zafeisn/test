package com.amway.commerce.serialization;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author: Jason.Hu
 * @date: 2023-08-09
 * @desc:
 */
@Data
public class A {
    private Integer id;
    private Date date;
    private LocalDateTime localDateTime;
    private LocalDate localDate;
    private LocalTime localTime;
    private String desc;
}
