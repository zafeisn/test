package com.amway.commerce.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 * @author: Jason.Hu
 * @date: 2023-08-04
 * @desc: 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为 null或者空字符串
     *
     * @param str 待处理的字符串
     * @return 判断结果，true表示为 null或空字符串
     */
    public static boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否不为 null或空字符串
     *
     * @param str 待处理的字符串
     * @return 判断结果，true表示不为 null或空字符串
     */
    public static boolean isNotBlank(String str) {
        // 只要有一个false，就为false
        // 如果str=null，即str!=null返回false
        // 如果str="",即str.isEmpty()返回true，取反则为false
        return str != null && !str.isEmpty();
    }

    /**
     * 去除字符串开头和结尾的空格
     *
     * @param str 待处理的字符串
     * @return 如果 str不为 null，则返回去除空格后的字符串，否则返回空字符串
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * 获取字符串的长度，一个中文算 2个字符
     *
     * @param str 待处理的字符串
     * @return 字符串长度
     */
    public static int length(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // 正则表达式，当匹配到一个中文，length +2
            if (Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")) {
                length += 2;
            } else {
                length++;
            }
        }
        return length;
    }

    /**
     * 字节流转换为 String
     *
     * @param inputStream 字节流
     * @param charset     编码格式
     * @return 指定编码格式的字符串
     */
    public static String streamToStr(InputStream inputStream, String charset) {
        StringBuilder sb = null;
        InputStreamReader streamReader = null;
        try {
            sb = new StringBuilder();
            // 存储从输入流中读取的数据
            char[] buff = new char[1024];
            // 用于将输入流转换为指定字符集的字符流
            streamReader = new InputStreamReader(inputStream, charset);
            // 循环读取输入流中的数据，每次读取 1024个字符，将其添加到 StringBuilder对象中
            for (int read; (read = streamReader.read(buff, 0, buff.length)) > 0; ) {
                sb.append(buff, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭最外层的流
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 字符流转换为 String
     *
     * @param reader 字符流
     * @return 指定编码格式的字符串
     */
    public static String readerToStr(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }


    /**
     * 判断字符串是否是纯数字，可以检测科学计数法（**E|e**），正则表达式 [+-]?[0-9]+.?[0-9]+[Ee]?[0-9]+
     *
     * @param str 待检测字符串
     * @return 判断结果，true表示该字符串是纯数字
     */
    public static boolean isNumeric(String str) {
        return str.matches("[-+]?\\d+(\\.\\d+)?([eE][-+]?\\d+)?");
    }

    /**
     * 数字格式化，例如 123456789.0 ###,###.### -> 123,456,789.0
     *
     * @param pattern 数字显示格式
     * @param number  数字对象
     * @return 如果 number参数为纯数字，则返回格式化后的字符串，否则返回 null
     */
    public static String format(String pattern, Object number) {
        if (!isNumeric(number.toString())) {
            return null;
        }
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance();
        formatter.applyPattern(pattern);
        return formatter.format(number);
    }
}
