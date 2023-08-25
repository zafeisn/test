package com.amway.commerce.string;

import java.util.StringJoiner;

/**
 * @author: Jason.Hu
 * @date: 2023-08-07
 * @desc: 字符串拼接工具类
 */
public class StringJoinerUtil {

    /**
     * 字符串拼接
     *
     * @param delimiter 字符串分隔符
     * @param strings   待拼接字符串数组
     * @return 拼接后的字符串
     */
    public static String joinStr(String delimiter, String... strings) {
        StringJoiner stringJoiner = new StringJoiner(delimiter);
        for (String str : strings) {
            stringJoiner.add(str);
        }
        return stringJoiner.toString();
    }

}
