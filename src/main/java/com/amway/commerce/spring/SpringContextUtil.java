package com.amway.commerce.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: Jason.Hu
 * @date: 2023-08-14
 * @desc: Spring上下文工具类 - v5.3.18
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * IOC容器
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取 applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过 name获取 Bean对象
     *
     * @param name 名称
     * @return Bean对象
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过 Class获取 Bean对象
     *
     * @param clazz 类型
     * @param <T>
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过 name和 Class获取 Bean对象
     *
     * @param name  名称
     * @param clazz 类型
     * @param <T>
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
