package com.amway.commerce.infrastructure;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * @author: Jason.Hu
 * @date: 2023-08-10
 * @desc:
 */
public class PropertiesUtilTest {

    /**
     * 根据配置文件中的 key获取对应的 value
     */
    @Test
    public void getProperty() {
        PropertiesUtil log4j = PropertiesUtil.getInstance("log4j.properties");
        // debug, stdout, R
        System.out.println(log4j.getProperty("log4j.rootLogger"));
        PropertiesUtil test = PropertiesUtil.getInstance("test.properties");
        // abc
        System.out.println(test.getProperty("A"));
        // default
        System.out.println(test.getProperty("E", "default"));
        // 中文
        System.out.println(test.getProperty("D"));
        // #4$5%@^&*9()!
        System.out.println(test.getProperty("F"));

    }
}