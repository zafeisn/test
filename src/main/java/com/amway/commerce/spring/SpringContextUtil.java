package com.amway.commerce.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author: Jason.Hu
 * @date: 2023-08-14
 */
public class SpringContextUtil  {

    private static ApplicationContext applicationContext;

    /**
     * 注入 ApplicationContext。
     *
     * @param applicationContext ApplicationContext对象
     * @throws BeansException
     */
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取 ApplicationContext。
     *
     * @return ApplicationContext对象
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取 Bean对象。
     *
     * @param name Bean名称
     * @return Bean对象
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 获取 Bean对象。
     *
     * @param clazz Class类对象
     * @param <T> 泛型
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 获取 Bean对象。
     *
     * @param name  Bean名称
     * @param clazz Class类对象
     * @param <T> 泛型
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
