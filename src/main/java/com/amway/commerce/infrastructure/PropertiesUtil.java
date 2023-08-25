package com.amway.commerce.infrastructure;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author: Jason.Hu
 * @date: 2023-08-10
 * @desc: Properties工具类
 */
@Slf4j
public class PropertiesUtil {

    /**
     * 默认字符编码格式
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * Properties
     */
    private static Properties properties;

    /**
     * 待读取的配置文件名
     */
    private final String fileName;

    /**
     * 私有含参构造器，用于获取单例以及加载配置文件
     *
     * @param fileName 待读取的配置文件名
     */
    private PropertiesUtil(String fileName) {
        this.fileName = fileName;
        loadProperties();
    }

    /**
     * 配置文件实例集合
     */
    private static Map<String, PropertiesUtil> instanceMap = new HashMap<>();

    /**
     * 获取 PropertiesUtil实例对象
     *
     * @param fileName 待读取的配置文件名（.properties）
     * @return 返回实例对象
     */
    public static PropertiesUtil getInstance(String fileName) {
        if (instanceMap.get(fileName) != null) {
            return instanceMap.get(fileName);
        }
        // 实例化
        PropertiesUtil instance = new PropertiesUtil(fileName);
        instanceMap.put(fileName, instance);
        return instance;
    }

    /**
     * 加载配置文件
     */
    private void loadProperties() {
        InputStream inputStream = null;
        InputStreamReader reader = null;
        try {
            this.properties = new Properties();
            // 从类加载路径中获取文件的输入流
            inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            // 将输入字节流转换为输入字符流，并设置字符编码格式
            reader = new InputStreamReader(inputStream, DEFAULT_CHARSET);
            // 从输入字符流中读取属性列表
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("InputStream close fail", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("InputStreamReader close fail", e);
                }
            }
        }
    }

    /**
     * 根据配置文件中的 key获取对应的 value，需设置默认值
     *
     * @param key          配置文件中的 key
     * @param defaultValue 当 key对应的 value为 null时的默认值
     * @return 文件属性值
     */
    public String getProperty(String key, String defaultValue) {
        if (this.properties == null) {
            loadProperties();
        }
        return this.properties.getProperty(key.trim(), defaultValue).trim();
    }

    /**
     * 根据配置文件中的 key获取对应的 value
     *
     * @param key
     * @return 文件属性值，默认为 null
     */
    public String getProperty(String key) {
        if (this.properties == null) {
            loadProperties();
        }
        return getProperty(key.trim(), null).trim();
    }

}
