package com.amway.commerce.serialization;

import com.amway.commerce.exception.CommonException;
import org.dom4j.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author: Jason.Hu
 * @date: 2023-09-20
 */
public class XMLUtilTest {

    @Test
    public void testGetRootNode() throws DocumentException {
        // normal：文件存在
        Assert.assertNotNull(XMLUtil.getRootNode("src/main/resources/test01.xml"));
        Assert.assertNotNull(XMLUtil.getRootNode("src/main/resources/test02.xml"));
        Assert.assertNotNull(XMLUtil.getRootNode("C:\\Users\\CNU07HWR\\File\\IdeaProjects\\common\\amway-ecommerce-common\\src\\main\\resources\\test01.xml"));
        Assert.assertNotNull(XMLUtil.getRootNode("C:\\Users\\CNU07HWR\\File\\IdeaProjects\\common\\amway-ecommerce-common\\src\\main\\resources\\test02.xml"));

        // exception：文件不存在，文件内容本身错误
        // 文件不存在
        Assert.assertThrows(CommonException.class, ()->{XMLUtil.getRootNode(null);});
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.getRootNode("no path");});
        // 文件内容本身错误
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.getRootNode("C:\\Users\\CNU07HWR\\Desktop\\test03.xml");});
    }

    /**
     * 测试 Node根节点是否能获取到
     * @throws DocumentException
     */
    @Test
    public void testGetNode() throws DocumentException {
        // normal：xpath存在
        Assert.assertEquals("中文",XMLUtil.getNode("src/main/resources/test01.xml", "student").getText().trim());
        Assert.assertEquals("",XMLUtil.getNode("src/main/resources/test01.xml", "student/name").getText().trim());
        Assert.assertEquals("三", XMLUtil.getNode("src/main/resources/test01.xml", "student/name/ming").getText().trim());
        Assert.assertEquals("1",XMLUtil.getNode("src/main/resources/test01.xml", "id").getText().trim());
        Assert.assertEquals("1",XMLUtil.getNode("src/main/resources/test02.xml", "b").getText().trim());
        Assert.assertEquals("4", XMLUtil.getNode("src/main/resources/test02.xml", "e").getText().trim());

        // exception：xpath为空值、空格、特殊字符，文件不存在
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.getNode("src/main/resources/test01.xml", " ");});
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.getNode("src/main/resources/test01.xml", "");});
        Assert.assertThrows(XPathException.class,()->{XMLUtil.getNode("src/main/resources/test01.xml", "123");});
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.getNode("src/main/resources/test01.xml", "\\");});
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.getNode("src/main/resources/test01.xml", "\n");});
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.getNode("no path", "\n");});
        Assert.assertThrows(CommonException.class,()->{XMLUtil.getNode(null, null);});
        Assert.assertThrows(CommonException.class,()->{XMLUtil.getNode("no path", null);});
    }

    @Test
    public void testListNode() throws DocumentException {
        // normal：xpath存在
        Assert.assertNotNull(XMLUtil.listNode("src/main/resources/test01.xml", "student"));
        Assert.assertNotNull(XMLUtil.listNode("src/main/resources/test01.xml", "student/name"));
        Assert.assertNotNull(XMLUtil.listNode("src/main/resources/test01.xml", "student/name/ming"));
        Assert.assertNotNull(XMLUtil.listNode("src/main/resources/test01.xml", "id"));
        Assert.assertNotNull(XMLUtil.listNode("src/main/resources/test02.xml", "b"));
        Assert.assertNotNull(XMLUtil.listNode("src/main/resources/test02.xml", "c"));
        Assert.assertNotNull(XMLUtil.listNode("src/main/resources/test02.xml", "c/d"));

        // boundary：xpath不存在，但为非空值非特殊字符非空格
        Assert.assertNotNull(XMLUtil.listNode("src/main/resources/test01.xml", "123456"));
        Assert.assertEquals(null,XMLUtil.listNode("src/main/resources/test01.xml", "he3"));
        Assert.assertEquals(null,XMLUtil.listNode("src/main/resources/test01.xml", "你好世界"));
        Assert.assertEquals(null,XMLUtil.listNode("src/main/resources/test01.xml", "helloworld"));
        Assert.assertEquals(null,XMLUtil.listNode("src/main/resources/test02.xml", "student"));

        // exception：参数为null，xpath为空值、空格、特殊字符，文件不存在
        // 参数为null
        Assert.assertThrows(CommonException.class,()->{XMLUtil.listNode("src/main/resources/test01.xml", null);});
        // xpath为空值、空格、特殊字符
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.listNode("src/main/resources/test01.xml", " ");});
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.listNode("src/main/resources/test01.xml", "");});
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.listNode("src/main/resources/test01.xml", "\\");});
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.listNode("src/main/resources/test01.xml", "\n");});
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.listNode("src/main/resources/test01.xml", "hello world");});
        // 文件不存在
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.listNode("no path", "\n");});
        Assert.assertThrows(CommonException.class, ()->{XMLUtil.listNode(null, "\n");});
    }

    @Test
    public void testGetText() throws DocumentException {
        // normal：path、xpath均存在
        Assert.assertEquals("19",XMLUtil.getText("src/main/resources/test01.xml", "student/age"));
        Assert.assertEquals("male",XMLUtil.getText("src/main/resources/test01.xml", "student/sex"));
        Assert.assertEquals("1",XMLUtil.getText("src/main/resources/test01.xml", "id"));
        Assert.assertEquals("1",XMLUtil.getText("src/main/resources/test02.xml", "b"));
        Assert.assertEquals("张",XMLUtil.getText("src/main/resources/test02.xml", "c/d/xing"));

        // exception：xpath为空值、空格、特殊字符、不存在，文件不存在
        // xpath为空值、空格、特殊字符、不存在
        Assert.assertThrows(NullPointerException.class, ()->{XMLUtil.getText("src/main/resources/test01.xml", "s");});
        Assert.assertThrows(InvalidXPathException.class, ()->{XMLUtil.getText("src/main/resources/test01.xml", "");});
        Assert.assertThrows(InvalidXPathException.class, ()->{XMLUtil.getText("src/main/resources/test01.xml", " ");});
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.getText("src/main/resources/test01.xml", "\\");});
        Assert.assertThrows(InvalidXPathException.class,()->{XMLUtil.getText("src/main/resources/test01.xml", "\n");});
        // 文件不存在
        Assert.assertThrows(CommonException.class, ()->{XMLUtil.getText(null, "s");});
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.getText("filePath", "s");});
    }

    @Test
    public void testAsXMLAndParseXML() throws DocumentException {
        // normal：txt文件、XML文件、XML字符串
        // 生成 Document对象
        Assert.assertNotNull(XMLUtil.generateXML("src/main/resources/test01.txt"));
        Assert.assertNotNull(XMLUtil.generateXML("src/main/resources/test01.txt").getRootElement());
        Assert.assertNotNull(XMLUtil.generateXML("src/main/resources/test01.xml"));
        Assert.assertNotNull(XMLUtil.generateXML("src/main/resources/test02.xml"));
        // 解析成 XML
        Assert.assertNotNull(XMLUtil.asXML(XMLUtil.generateXML("src/main/resources/test01.txt")));
        Assert.assertNotNull(XMLUtil.asXML(XMLUtil.generateXML("src/main/resources/test01.xml")));
        Assert.assertNotNull(XMLUtil.asXML(XMLUtil.generateXML("src/main/resources/test02.xml")));
        Assert.assertNotNull(XMLUtil.parseText(XMLUtil.asXML(XMLUtil.generateXML("src/main/resources/test01.txt"))));
        Assert.assertNotNull(XMLUtil.parseText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<students>\n" +
                "    <id>1</id>\n" +
                "    <student number=\"heima_0001\">\n" +
                "        <name id=\"sn1\">\n" +
                "            <xing>张</xing>\n" +
                "            <ming>三</ming>\n" +
                "        </name>\n" +
                "        <name id=\"sn2\">小</name>\n" +
                "        <age>19</age>\n" +
                "        <sex>male</sex>\n" +
                "    </student>\n" +
                "    <student number=\"heima_0002\">\n" +
                "        <name>jack</name>\n" +
                "        <age>18</age>\n" +
                "        <sex>female</sex>\n" +
                "    </student>\n" +
                "\n" +
                "</students>"));
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<students>\n" +
                "    <id>1</id>\n" +
                "    <student number=\"heima_0001\">\n" +
                "        <name id=\"sn1\">\n" +
                "            <xing>张</xing>\n" +
                "            <ming>三</ming>\n" +
                "        </name>\n" +
                "        <name id=\"sn2\">小</name>\n" +
                "        <age>19</age>\n" +
                "        <sex>male</sex>\n" +
                "    </student>\n" +
                "    <student number=\"heima_0002\">\n" +
                "        <name>jack</name>\n" +
                "        <age>18</age>\n" +
                "        <sex>female</sex>\n" +
                "    </student>\n" +
                "\n" +
                "</students>",XMLUtil.asXML(XMLUtil.generateXML("src/main/resources/test01.txt")));

        // exception：null，文件路径不存在，空值、空格、特殊字符、数字
        // null
        Assert.assertThrows(CommonException.class, ()->{XMLUtil.generateXML(null);});
        Assert.assertThrows(CommonException.class, ()->{XMLUtil.asXML(null);});
        // 文件路径不存在
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.generateXML("filePath");});
        // 空值、空格、特殊字符、数字
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.generateXML("");});
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.generateXML(" ");});
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.generateXML("123");});
        Assert.assertThrows(DocumentException.class, ()->{XMLUtil.generateXML("./,");});
    }
}