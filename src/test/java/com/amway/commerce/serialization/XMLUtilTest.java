package com.amway.commerce.serialization;

import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.junit.Test;

import java.util.List;

/**
 * @author: Jason.Hu
 * @date: 2023-08-17
 * @desc:
 */
public class XMLUtilTest {

    /**
     * 测试全部方法
     */
    @Test
    public void test() throws DocumentException {
        String filePath = "src/main/resources/student1.xml";
        Node rootNode = XMLUtil.getRootNode(filePath);
        // org.dom4j.tree.DefaultElement@47fd17e3 [Element: <students attributes: []/>]
        System.out.println(rootNode);
        Node node = XMLUtil.getNode(filePath, "//students//student");
        // org.dom4j.tree.DefaultElement@548b7f67 [Element: <student attributes: [org.dom4j.tree.DefaultAttribute@7c16905e [Attribute: name number value "heima_0001"]]/>]
        System.out.println(node);
        // 19
        System.out.println(XMLUtil.getText(filePath, "//students//student//age"));
        List<Node> nodes = XMLUtil.listNode(node, "//students//student//age");
        for (int i = 0; i < nodes.size(); i++) {
            // 19 18
            System.out.println(XMLUtil.getText(nodes.get(i)));
        }
        // [org.dom4j.tree.DefaultElement@66a3ffec [Element: <age attributes: []/>], org.dom4j.tree.DefaultElement@6a41eaa2 [Element: <age attributes: []/>]]
        System.out.println(XMLUtil.listNode(filePath, "//students//student//age"));
    }
}