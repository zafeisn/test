package com.amway.commerce.string;


import com.amway.commerce.exception.CommonException;
import org.junit.Assert;
import org.junit.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Jason.Hu
 * @date: 2023-09-21
 */
public class StringUtilTest {

    @Test
    public void testIsEmpty() {

        // normal：中文、数字字符串、英文、特殊字符串
        Assert.assertFalse(StringUtil.isEmpty("中文"));
        Assert.assertFalse(StringUtil.isEmpty("123"));
        Assert.assertFalse(StringUtil.isEmpty("abc"));
        Assert.assertFalse(StringUtil.isEmpty("ABC"));
        Assert.assertFalse(StringUtil.isEmpty(",.!"));

        // boundary：长文本、空值、空串、单个字符
        String longStr = StringUtil.streamToStr(StringUtilTest.class.getResourceAsStream("/DDD.txt"));
        Assert.assertTrue(StringUtil.isEmpty(""));
        Assert.assertFalse(StringUtil.isEmpty(" "));
        Assert.assertFalse(StringUtil.isEmpty("a"));
        Assert.assertTrue(StringUtil.isEmpty(null));
        Assert.assertFalse(StringUtil.isEmpty(longStr));

    }

    @Test
    public void testIsNotEmpty() {
        // normal：中文、数字字符串、英文、特殊字符串
        Assert.assertTrue(StringUtil.isNotEmpty("中文"));
        Assert.assertTrue(StringUtil.isNotEmpty("123"));
        Assert.assertTrue(StringUtil.isNotEmpty("abc"));
        Assert.assertTrue(StringUtil.isNotEmpty("ABC"));
        Assert.assertTrue(StringUtil.isNotEmpty(",.!"));

        // boundary：长文本、空值、空串、单个字符
        String longStr = StringUtil.streamToStr(StringUtilTest.class.getResourceAsStream("/DDD.txt"));
        Assert.assertFalse(StringUtil.isNotEmpty(""));
        Assert.assertTrue(StringUtil.isNotEmpty(" "));
        Assert.assertTrue(StringUtil.isNotEmpty("a"));
        Assert.assertFalse(StringUtil.isNotEmpty(null));
        Assert.assertTrue(StringUtil.isNotEmpty(longStr));
    }

    @Test
    public void testGetLengthWithChinese() {
        // normal：中文、数字字符串、英文、特殊字符串
        Assert.assertEquals(4, StringUtil.getLengthWithChinese("中文"));
        Assert.assertEquals(3, StringUtil.getLengthWithChinese("abc"));
        Assert.assertEquals(3, StringUtil.getLengthWithChinese("ABC"));
        Assert.assertEquals(3, StringUtil.getLengthWithChinese(",./"));
        Assert.assertEquals(7, StringUtil.getLengthWithChinese("中文abc"));
        Assert.assertEquals(8, StringUtil.getLengthWithChinese("123abc中"));

        // boundary：长文本、空值、空串、单个字符
        String longStr = StringUtil.streamToStr(StringUtilTest.class.getResourceAsStream("/long.txt"));
        Assert.assertEquals(0, StringUtil.getLengthWithChinese(""));
        Assert.assertEquals(1, StringUtil.getLengthWithChinese(" "));
        Assert.assertEquals(1, StringUtil.getLengthWithChinese("a"));
        Assert.assertEquals(2, StringUtil.getLengthWithChinese("中"));
        Assert.assertEquals(1, StringUtil.getLengthWithChinese("\n"));
        Assert.assertEquals(295*2+20, StringUtil.getLengthWithChinese(longStr));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, () -> {StringUtil.getLengthWithChinese(null);});
    }

    @Test
    public void testStreamToStr() {
        // normal：中文、英文、特殊字符、组合
        String[] input_01 = {"你好世界", "hello world", "HELLO WORLD", ",./!@#", "你好世界，hello world,HELLO WORLD！.。","你好\nhello"};
        for (int i = 0; i < input_01.length; i++) {
            InputStream inputStream = new ByteArrayInputStream(input_01[i].getBytes(StandardCharsets.UTF_8));
            Assert.assertEquals(input_01[i], StringUtil.streamToStr(inputStream));
        }

        // boundary：空值、空串、单个字符、单个中文、长文本
        String[] input_02 = {"", " ", "a", "你", "!", "\n"};
        for (int i = 0; i < input_02.length; i++) {
            InputStream inputStream = new ByteArrayInputStream(input_02[i].getBytes(StandardCharsets.UTF_8));
            Assert.assertEquals(input_02[i], StringUtil.streamToStr(inputStream));
        }
        Assert.assertEquals("领域驱动设计是一种软件架构方法它强调将业务领域的知识和概念映射到软件模型中通过深入了解业务领域的核心概念和流程能够帮助开发人员构建更贴近业务需求的软件系统在中业务领域被划分为一系列有界上下文每个有界上下文都是一个独立的领域模型通过明确有界上下文的边界和交互方式能够避免不同部分之间的模型混乱和冲突强调使用领域特定语言来描述业务需求和规则使业务专家和开发人员能够更好地沟通和协作通过使用开发人员能够将业务逻辑和规则融入到代码中从而提高代码的可读性和可维护性总之领域驱动设计是一种将业务领域知识和概念映射到软件模型中的架构方法它能够帮助开发人员构建更贴近业务需求的软件系统提高代码质量和可维护性295gehanzi(ABC)，。！【】",
                StringUtil.streamToStr(StringUtilTest.class.getResourceAsStream("/long.txt")));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{StringUtil.streamToStr(null);});
    }

    @Test
    public void testReaderToStr() throws IOException {
        // normal：中文、英文、特殊字符、组合
        String[] input_01 = {"你好世界", "hello world", "HELLO WORLD", ",./!@#", "你好世界，hello world,HELLO WORLD！.。","你好\nhello"};
        for (int i = 0; i < input_01.length; i++) {
            BufferedReader reader = new BufferedReader(new StringReader(input_01[i]));
            Assert.assertEquals(input_01[i], StringUtil.readerToStr(reader));
        }

        // boundary：空值、空串、单个字符、单个中文、特殊字符、长文本
        String[] input_02 = {"", " ", "a", "你", "!"};
        for (int i = 0; i < input_02.length; i++) {
            BufferedReader reader = new BufferedReader(new StringReader(input_02[i]));
            Assert.assertEquals(input_02[i], StringUtil.readerToStr(reader));
        }
        Assert.assertEquals("", StringUtil.readerToStr(new BufferedReader(new StringReader("\n"))));
        Assert.assertEquals("", StringUtil.readerToStr(new BufferedReader(new StringReader("\r"))));
        Assert.assertEquals("领域驱动设计是一种软件架构方法它强调将业务领域的知识和概念映射到软件模型中通过深入了解业务领域的核心概念和流程能够帮助开发人员构建更贴近业务需求的软件系统在中业务领域被划分为一系列有界上下文每个有界上下文都是一个独立的领域模型通过明确有界上下文的边界和交互方式能够避免不同部分之间的模型混乱和冲突强调使用领域特定语言来描述业务需求和规则使业务专家和\n" +
                        "开发人员能够更好地沟通和协作通过使用开发人员能够将业务逻辑和规则融入到代码中从而提高代码的可读性和\n" +
                        "可维护性总之领域驱动设计是一种将业务领域知识和概念映射到软件模型中的架构方法它能够帮助开发人员构建更\n" +
                        "贴近业务需求的软件系统提高代码质量和可维护性295gehanzi(ABC)，。！【】\n"+"\\n",
                StringUtil.readerToStr(new BufferedReader(new FileReader(
                        Thread.currentThread().getContextClassLoader().getResource("reader.txt").getPath()
                ))));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{StringUtil.readerToStr(null);});
    }

    @Test
    public void testIsNumeric() {
        // normal：中文、英文、纯数字、科学计数法、非法数字串
        Assert.assertFalse(StringUtil.isNumeric("hello world"));
        Assert.assertFalse(StringUtil.isNumeric("HELLO WORLD"));
        Assert.assertFalse(StringUtil.isNumeric("你好世界"));
        Assert.assertFalse(StringUtil.isNumeric(",./123"));
        Assert.assertTrue(StringUtil.isNumeric("123456"));
        Assert.assertTrue(StringUtil.isNumeric("123"));
        Assert.assertTrue(StringUtil.isNumeric("123.456"));
        Assert.assertTrue(StringUtil.isNumeric("-1234.56"));
        Assert.assertTrue(StringUtil.isNumeric("+1234.56"));

        // boundary：0，单个字符，空值，空格，科学计数法
        // 0
        Assert.assertTrue(StringUtil.isNumeric("0"));
        Assert.assertTrue(StringUtil.isNumeric("-0"));
        Assert.assertTrue(StringUtil.isNumeric("+0"));
        Assert.assertTrue(StringUtil.isNumeric("0.0"));
        Assert.assertTrue(StringUtil.isNumeric("-0.0"));
        Assert.assertTrue(StringUtil.isNumeric("+0.0"));
        Assert.assertTrue(StringUtil.isNumeric("0.000000000000000000000000"));
        Assert.assertTrue(StringUtil.isNumeric("0.000000000000000000000001"));
        // 单个字符，空值，空格
        Assert.assertFalse(StringUtil.isNumeric("o"));
        Assert.assertFalse(StringUtil.isNumeric(""));
        Assert.assertFalse(StringUtil.isNumeric(" "));
        // 科学计数法
        Assert.assertTrue(StringUtil.isNumeric("123E4"));
        Assert.assertFalse(StringUtil.isNumeric("E12345"));
        Assert.assertTrue(StringUtil.isNumeric("0E12345"));
        Assert.assertTrue(StringUtil.isNumeric("01E2345"));
        Assert.assertTrue(StringUtil.isNumeric("1E2345"));
        Assert.assertTrue(StringUtil.isNumeric("-1E2345"));
        Assert.assertTrue(StringUtil.isNumeric("+1E2345"));
        Assert.assertTrue(StringUtil.isNumeric("-1234E56"));
        Assert.assertTrue(StringUtil.isNumeric("+1234E56"));
        Assert.assertTrue(StringUtil.isNumeric("-1.234E5"));
        Assert.assertTrue(StringUtil.isNumeric("+1.234E5"));
        Assert.assertTrue(StringUtil.isNumeric("1.234E5"));
        Assert.assertTrue(StringUtil.isNumeric("1.234e5"));
        Assert.assertTrue(StringUtil.isNumeric("12e2345"));
        Assert.assertTrue(StringUtil.isNumeric("132345E3"));
        Assert.assertTrue(StringUtil.isNumeric("12345E0"));
        Assert.assertTrue(StringUtil.isNumeric("1234.5E0"));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{StringUtil.isNumeric(null);});
    }

    @Test
    public void testReplaceCharAtIndex() {
        // normal：中文、英文、特殊字符
        Assert.assertEquals("你好u界", StringUtil.replaceCharAtIndex("你好世界",2, 'u'));
        Assert.assertEquals("hella world", StringUtil.replaceCharAtIndex("hello world",4, 'a'));
        Assert.assertEquals("hello+world", StringUtil.replaceCharAtIndex("hello world",5, '+'));
        Assert.assertEquals("..，\r\n", StringUtil.replaceCharAtIndex("..，，\n",3, '\r'));
        Assert.assertEquals("你好世界", StringUtil.replaceCharAtIndex("你好世w",3, '界'));
        Assert.assertEquals("1234567890", StringUtil.replaceCharAtIndex("1234567890",5, '6'));

        // boundary：首位、末位
        // 首位
        Assert.assertEquals("6234567890", StringUtil.replaceCharAtIndex("1234567890",0, '6'));
        Assert.assertEquals("6", StringUtil.replaceCharAtIndex(" ",0, '6'));
        Assert.assertEquals("1", StringUtil.replaceCharAtIndex("u",0, '1'));
        Assert.assertEquals("\r", StringUtil.replaceCharAtIndex("\n",0, '\r'));
        // 末位
        Assert.assertEquals("1234567896", StringUtil.replaceCharAtIndex("1234567890",9, '6'));
        Assert.assertEquals("中6", StringUtil.replaceCharAtIndex("中文",1, '6'));
        Assert.assertEquals("hello wolr6", StringUtil.replaceCharAtIndex("hello wolrd",10, '6'));

        // exception：参数为null、超过字符长度
        Assert.assertThrows(CommonException.class, ()->{StringUtil.replaceCharAtIndex(null, 1, '2');});
        Assert.assertThrows(CommonException.class, ()->{StringUtil.replaceCharAtIndex("null", 4, '2');});
        Assert.assertThrows(CommonException.class, ()->{StringUtil.replaceCharAtIndex("null", 10, '2');});

    }

    @Test
    public void testsplitByLength() {
        // normal：中文、英文、中英文
        Assert.assertEquals(new String[]{"你好", "世界"}, StringUtil.splitByLength("你好世界", 2));
        Assert.assertEquals(new String[]{"你好世", "界"}, StringUtil.splitByLength("你好世界", 3));
        Assert.assertEquals(new String[]{"hel", "lo ", "wor", "ld"}, StringUtil.splitByLength("hello world", 3));
        Assert.assertEquals(new String[]{"hell", "o wo", "rld"}, StringUtil.splitByLength("hello world", 4));
        Assert.assertEquals(new String[]{"你好世界","hell", "o wo", "rld"}, StringUtil.splitByLength("你好世界hello world", 4));

        // boundary：首位、末尾
        // 首位
        Assert.assertEquals(new String[]{"你好世界"}, StringUtil.splitByLength("你好世界", 0));
        Assert.assertEquals(new String[]{""}, StringUtil.splitByLength("", 0));
        Assert.assertEquals(new String[]{"你","好","世","界"}, StringUtil.splitByLength("你好世界", 1));
        Assert.assertEquals(new String[]{"\n"}, StringUtil.splitByLength("\n", 0));
        // 末尾
        Assert.assertEquals(new String[]{"你好世界"}, StringUtil.splitByLength("你好世界", 4));
        Assert.assertEquals(new String[]{" "}, StringUtil.splitByLength(" ", 1));
        Assert.assertEquals(new String[]{"\n"}, StringUtil.splitByLength("\n", 1));

        // exception
        Assert.assertThrows(CommonException.class, ()->{StringUtil.splitByLength(null, 1);});
        Assert.assertThrows(CommonException.class, ()->{StringUtil.splitByLength("123", 4);});
        Assert.assertThrows(CommonException.class, ()->{StringUtil.splitByLength("", 10);});

    }

    @Test
    public void testJoinStr() {
        // normal：正常的key和value
        String[] str_01 = {"k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4"};
        List<String> list_01 = Arrays.asList(str_01);
        String[] str_02 = {"k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4"};
        List<String> list_02 = Arrays.asList(str_02);
        Assert.assertEquals("k1:v1:k2:v2:k3:v3:k4:v4", StringUtil.joinStr(":", list_01));
        Assert.assertEquals("k1\nv1\nk2\nv2\nk3\nv3\nk4\nv4", StringUtil.joinStr("\n", list_02));

        // boundary：空值、空格、单个字符列表，空值拼接符
        // 空值、空格、单个字符列表
        List<String> list_03 = Arrays.asList("");
        List<String> list_04 = Arrays.asList("", "");
        List<String> list_05 = Arrays.asList(" ");
        List<String> list_06 = Arrays.asList(" ", " ");
        List<String> list_07 = Arrays.asList("a");
        List<String> list_08 = Arrays.asList("a","b");
        Assert.assertEquals("", StringUtil.joinStr(":", list_03));
        Assert.assertEquals(":", StringUtil.joinStr(":", list_04));
        Assert.assertEquals(" ", StringUtil.joinStr(":", list_05));
        Assert.assertEquals(" : ", StringUtil.joinStr(":", list_06));
        Assert.assertEquals("a", StringUtil.joinStr("-", list_07));
        Assert.assertEquals("a+b", StringUtil.joinStr("+", list_08));
        // 空值拼接符
        Assert.assertEquals("k1v1k2v2k3v3k4v4", StringUtil.joinStr("", list_01));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{StringUtil.joinStr(null, list_01);});
        Assert.assertThrows(CommonException.class, ()->{StringUtil.joinStr("", null);});
        Assert.assertThrows(CommonException.class, ()->{StringUtil.joinStr(null, null);});

    }
}