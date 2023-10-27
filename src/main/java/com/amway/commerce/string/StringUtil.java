package com.amway.commerce.string;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author: Jason.Hu
 * @date: 2023-08-04
 */
public class StringUtil {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * 判断字符串是否为 null或空字符串。
     *
     * @param str 字符串
     * @return 布尔值，true表示为 null或空字符串
     *
     * <p>
     * <b>例：</b><br>
     * str=null，返回 true；<p>
     * str=""，返回 true；<p>
     * str="hello world"，返回 false。
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否不为 null或空字符串。
     *
     * @param str 字符串
     * @return 布尔值，false表示为 null或空字符串
     *
     * <p>
     * <b>例：</b><br>
     * str=null，返回 false；<p>
     * str=""，返回 false；<p>
     * str="hello world"，返回 true。
     */
    public static boolean isNotEmpty(String str) {
        // 只要有一个 false，就为 false
        // 如果 str=null，即 str!=null返回 false
        // 如果 str="",即 str.isEmpty()返回 true，取反则为 false
        return !isEmpty(str);
    }

    /**
     * 获取字符串的长度，一个中文算 2个字符长度。
     * 该方法需要对参数进行非空判断，若 str为空，则抛出参数不能为空异常。
     *
     * @param str 字符串，不可为空
     * @return 字符串长度，中文算 2个字符长度
     *
     * <p>
     * <b>例：</b><br>
     * str=null，抛出“参数不能为空”异常提示信息；<p>
     * str="hello world"，返回 11；<p>
     * str="你好 世界"，返回 9。
     */
    public static int getLengthWithChinese(String str) {
        isNotNull(str);
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // 正则表达式，当匹配到一个中文，length + 2
            if (Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")) {
                length += 2;
            } else {
                length++;
            }
        }
        return length;
    }

    /**
     * 将字节流转换为字符串，默认使用 UTF_8的字符编码格式。
     * 该方法需要对参数进行非空校验，若 inputStream为空，则抛出参数不能为空异常。
     *
     * @param inputStream InputStream字节流，不可为空
     * @return 字符串
     *
     * <p>
     * <b>例：</b><br>
     * inputStream=null，抛出“参数不能为空”异常提示信息。
     */
    public static String streamToStr(InputStream inputStream) {
        isNotNull(inputStream);
        StringBuilder sb = null;
        InputStreamReader streamReader = null;
        try {
            sb = new StringBuilder();
            // 存储从输入流中读取的数据
            char[] buff = new char[1024];
            // 用于将输入流转换为指定字符集的字符流
            streamReader = new InputStreamReader(inputStream, UTF_8);
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
     * 将字符流转换为字符串。
     * 该方法需要对参数进行非空校验，若 reader为空，则抛出参数不能为空异常。
     *
     * @param reader BufferedReader字符流，不可为空
     * @return 字符串
     *
     * <p>
     * <b>例：</b><br>
     * reader=null，抛出“参数不能为空”异常提示信息。
     */
    public static String readerToStr(BufferedReader reader) throws IOException {
        isNotNull(reader);
        StringBuilder builder = new StringBuilder();
        String line;
        boolean isFirstLine = true;
        while ((line = reader.readLine()) != null) {
            if (!isFirstLine) {
                builder.append("\n");
            }
            builder.append(line);
            isFirstLine = false;
        }
        return builder.toString();
    }

    /**
     * 判断字符串是否是纯数字，可检测科学计数法。
     * 该方法需要对参数进行非空校验，若 str为空，则抛出参数不能为空异常。
     *
     * @param str 字符串，不可为空
     * @return 布尔值，true表示为纯数字
     *
     * <p>
     * <b>例：</b><br>
     * str=null，抛出“参数不能为空”异常提示信息；<p>
     * str="123E0"，返回 true；<p>
     * str="123.99"，返回 true；<p>
     * str="123E-3"，返回 true；<p>
     * str="123E+2"，返回 true；<p>
     * str="123EE2"，返回 false。
     */
    public static boolean isNumeric(String str) {
        isNotNull(str);
        return str.matches("[-+]?\\d+(\\.\\d+)?([eE][-+]?\\d+)?");
    }

    /**
     * 用 ch替换字符串中指定位置的字符。
     * 该方法需要对参数进行非空校验和长度越界检查，若 str为空，则抛出参数不能为空异常；若 pos超过索引位置，则抛出数组越界异常。
     *
     * @param str 字符串，不可为空
     * @param index 指定位置，不能超过索引值（str.length - 1）
     * @param ch  待替换的字符
     * @return 替换后的字符串
     *
     * <p>
     * <b>例：</b><br>
     * str=null，pos=2，ch='e'，抛出“参数不能为空”异常提示信息；<p>
     * str="hallo world"，pos=12，ch='e'，抛出“数组越界”异常提示信息；<p>
     * str="hallo world"，pos=1，ch='e'，返回 hello world。
     */
    public static String replaceCharAtIndex(String str, int index, char ch) {
        isNotNull(str);
        isIndexOut(str, index);
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return new String(chars);
    }

    /**
     * 将字符串以指定长度进行分隔，对于长度不够的直接截取即可。
     * 该方法需要对参数进行非空校验和长度越界检查，若 str为空，则抛出参数不能为空异常；若 length超过字符串的长度，则抛出数组越界异常。
     *
     * @param str    字符串，不可为空
     * @param length 指定长度，不能超过字符串的长度
     * @return 分割后的字符数组
     *
     * <p>
     * <b>例：</b><br>
     * str=null，length=1，抛出“参数不能为空”异常提示信息；<p>
     * str="hello world"，length=12，抛出“数组越界”异常提示信息；<p>
     * str="hello world"，length=3，返回 [hel, lo , wor, ld]；<p>
     * str="hello world"，length=4，返回 [hell, o wo, rld]。
     */
    public static String[] splitByLength(String str, int length) {
        isNotNull(str);
        // 判断是否超过字符串长度
        if (str.length() < length) {
            throw new CommonException(CommonError.ArrayIndexOutOfBoundsException.getMessage());
        }
        // 若指定长度为 0，则直接返回
        if (length == 0) {
            return new String[]{str};
        }
        int len = str.length();
        int n = len / length;
        // 计算分组，若求余为 0表示刚好分完，若不为 0表示还有剩余不够 length长度的字符串，需要将字符串数组大小加 1
        int count = len % length == 0 ? n : n + 1;
        String[] strArr = new String[count];
        // 遍历取子字符串
        for (int i = 0; i < count; i++) {
            // 若求余不为 0时，也就是剩余字符串不够取，直接截取即可
            if (i == n) {
                strArr[i] = str.substring(i * length, len);
                break;
            }
            strArr[i] = str.substring(i * length, (i + 1) * length);
        }
        return strArr;
    }

    /**
     * 将字符串列表以指定拼接符进行拼接。
     * 该方法需要对参数做非空校验，若 stringList为空，则抛出参数不能为空异常。
     *
     * @param delimiter    字符串分隔符
     * @param stringList   字符串列表，不可为空
     * @return 拼接后的字符串
     *
     * <p>
     * <b>例：</b><br>
     * delimiter=null， stringList=["k1","v1","k2","v2"]，抛出“参数不能为空”异常提示信息；<p>
     * delimiter=null， stringList=null，抛出“参数不能为空”异常提示信息；<p>
     * delimiter=":"， stringList=["k1","v1","k2","v2"]，返回 k1:v1:k2:v2。
     */
    public static String joinStr(String delimiter, List<String> stringList) {
        isNotNull(delimiter, stringList);
        StringJoiner stringJoiner = new StringJoiner(delimiter);
        for (String str : stringList) {
            stringJoiner.add(str);
        }
        return stringJoiner.toString();
    }

    private static void isIndexOut(String str, int len) {
        if (str.length() <= len) {
            throw new CommonException(CommonError.ArrayIndexOutOfBoundsException.getMessage());
        }
    }

    protected static void isNotNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new CommonException(CommonError.NotNull.getMessage());
            }
        }
    }

}
