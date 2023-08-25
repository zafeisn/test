package com.amway.commerce.string;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.xml.soap.SOAPElementFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-04
 * @desc:
 */
@Slf4j
public class StringUtilTest {

    private static String nullStr = "";
    private static String null_Str = null;

    /**
     * 判断字符串是否为 null或者空字符串
     */
    @Test
    public void isBlank() {
        // true
        System.out.println(StringUtil.isBlank(nullStr));
        // true
        System.out.println(StringUtil.isBlank(null_Str));
        // false
        System.out.println(StringUtil.isBlank("w"));
    }

    /**
     * 判断字符串是否不为 null或空字符串
     */
    @Test
    public void isNotBlank() {
        // false
        System.out.println(StringUtil.isNotBlank(nullStr));
        // false
        System.out.println(StringUtil.isNotBlank(null_Str));
        // true
        System.out.println(StringUtil.isNotBlank("w"));
    }

    /**
     * 去除字符串开头和结尾的空格
     */
    @Test
    public void trim() {
        // 32 3 4
        System.out.println(StringUtil.trim("  32 3 4 "));
    }

    /**
     * 获取字符串的长度，一个中文算 2个字符
     */
    @Test
    public void length() {
        String text = "123zxc中文字";
        String text1 = null;
        // 12
        System.out.println(StringUtil.length(text));
        // 0
        System.out.println(StringUtil.length(text1));
    }

    /**
     * 判断字符串是否是纯数字，正则表达式 [-+]?\d+(\.\d+)?([eE][-+]?\d+)?
     */
    @Test
    public void isNumeric() {
        // true
        System.out.println(StringUtil.isNumeric("0.00009"));
        // true
        System.out.println(StringUtil.isNumeric("999999E0"));
        // true
        System.out.println(StringUtil.isNumeric("999999E-0"));
        // true
        System.out.println(StringUtil.isNumeric("0909"));
        // true
        System.out.println(StringUtil.isNumeric("+0909"));
        // true
        System.out.println(StringUtil.isNumeric("-0909"));
        // false
        System.out.println(StringUtil.isNumeric("222a222"));
        // false
        System.out.println(StringUtil.isNumeric("222222a"));
        // false
        System.out.println(StringUtil.isNumeric("E222222"));
        // false
        System.out.println(StringUtil.isNumeric("222222E"));
        // true
        System.out.println(StringUtil.isNumeric("22222.2"));
        // true
        System.out.println(StringUtil.isNumeric("22222.2E0"));
        // false
        System.out.println(StringUtil.isNumeric("22222.2E"));
    }

    /**
     * 数字格式化，例如 123456789.0 ###,###.### -> 123,456,789.0
     */
    @Test
    public void format() {
        // 10,000,023,456,789,012,000.000
        System.out.println(StringUtil.format("###,###.000", 10000023456789012320.0));
    }

    /**
     * 字节流转换为 String
     */
    @Test
    public void streamToStr() {

    }
}