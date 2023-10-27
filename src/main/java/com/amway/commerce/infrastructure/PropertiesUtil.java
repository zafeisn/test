package com.amway.commerce.infrastructure;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
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
 */
@Slf4j
public class PropertiesUtil {

    private static Map<String, Properties> map = new HashMap<>();
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static Properties properties;

    /**
     * 获取已加载好的 Properties。
     * 该方法需要对参数进行非空判断，若 fileName为空，则抛出参数不能为空异常。
     *
     * @param fileName 配置文件名，不可为空
     * @return 已加载的 Properties
     *
     * <p>
     * <b>例：</b><br>
     * fileName=null，抛出“参数不能为空”异常提示信息。
     */
    public static Properties getProperties(String fileName) {
        isNotNull(fileName);
        if (map.get(fileName) == null) {
            loadProperties(fileName);
        }
        return map.get(fileName);
    }

    /**
     * 获取配置文件中的 key对应的 value值，若 key不存在则返回设置的默认值。
     * 该方法需要对参数进行非空判断，若 fileName为空，则抛出参数不能为空异常。
     *
     * @param fileName     配置文件名，不可为空
     * @param key          配置文件的键名
     * @param defaultValue key不存在时返回的默认值
     * @return key对应的键值
     *
     * <p>
     * <b>例：</b><br>
     * fileName=null，key="key is exist"，defaultValue="key does not exist"，抛出“参数不能为空”异常提示信息；<p>
     * fileName="fileName"，key="key is exist"，defaultValue="key does not exist"，返回 key对应的键值；<p>
     * fileName="fileName"，key="key is not exist"，defaultValue="key does not exist"，返回 "key does not exist"。
     */
    public static String getProperty(String fileName, String key, String defaultValue) {
        isNotNull(fileName);
        if (map.get(fileName) == null) {
            loadProperties(fileName);
        }
        return map.get(fileName).getProperty(key.trim(), defaultValue);
    }

    /**
     * 获取配置文件中的 key对应的 value值，若 key不存在则返回 null。
     * 该方法需要对参数进行非空判断，若 fileName为空，则抛出参数不能为空异常。
     *
     * @param fileName 配置文件名，不可为空
     * @param key      配置文件的键名
     * @return key对应的键值
     *
     * <p>
     * <b>例：</b><br>
     * fileName=null，key="key is exist"，抛出“参数不能为空”异常提示信息；<p>
     * fileName="fileName"，key="key is exist"，返回 key对应的键值；<p>
     * fileName="fileName"，key="key is not exist"，返回 null。
     */
    public static String getProperty(String fileName, String key) {
        return getProperty(fileName, key, null);
    }

    private static void loadProperties(String fileName) {
        InputStream inputStream = null;
        InputStreamReader reader = null;
        try {
            properties = new Properties();
            // 从类加载路径中获取文件的输入流
            inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            // 将输入字节流转换为输入字符流，并设置字符编码格式
            reader = new InputStreamReader(inputStream, DEFAULT_CHARSET);
            // 从输入字符流中读取属性列表
            properties.load(reader);
            // 存入加载好的 Properties
            map.put(fileName, properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("InputStream close fail.", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("InputStreamReader close fail.", e);
                }
            }
        }
    }

    private static void isNotNull(String fileName) {
        if (fileName == null) {
            throw new CommonException(CommonError.NotNull.getMessage());
        }
    }

}
