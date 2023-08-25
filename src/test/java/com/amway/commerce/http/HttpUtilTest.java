package com.amway.commerce.http;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-23
 * @desc:
 */
public class HttpUtilTest {

    /**
     * get请求
     */
    @Test
    public void doGet() {
        System.out.println(HttpUtil.doGet("http://www.baidu.com"));
        System.out.println(HttpUtil.doGet("http://www.baidu.com", new HashMap<>()));
    }

    /**
     * post请求
     */
    @Test
    public void doPost() {
        System.out.println(HttpUtil.doPost("http://www.baidu.com"));
    }
}