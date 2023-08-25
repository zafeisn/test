package com.amway.commerce.serialization;

import com.amway.commerce.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * @author: Jason.Hu
 * @date: 2023-08-17
 * @desc: XML工具类
 */
@Slf4j
public class XMLUtil {

    private static void requireNonNull(Node node, String xpath) {
        if (node == null) {
            log.error("node is null");
            return;
        }
        if (StringUtil.isBlank(xpath)) {
            log.error("xpath is null or is empty");
        }
    }

    /**
     * 根据 xml文件路径获取 Document对象，方法私有
     */
    private static Document getDocument(String filePath) throws DocumentException {
        if (StringUtil.isBlank(filePath)) {
            log.error("filePath is null or is empty");
        }
        File file = new File(filePath);
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }

    /**
     * 根据 xml文件路径查找根节点
     *
     * @param filePath xml文件路径
     * @return 根节点
     */
    public static Node getRootNode(String filePath) throws DocumentException {
        return getDocument(filePath).getRootElement();
    }

    /**
     * 根据根节点查找单个节点
     *
     * @param filePath xml文件路径
     * @param xpath    节点路径
     * @return 单个节点
     */
    public static Node getNode(String filePath, String xpath) throws DocumentException {
        return getRootNode(filePath).selectSingleNode(xpath);
    }

    /**
     * 根据 node节点查找 xpath下的单个节点
     *
     * @param node  node节点
     * @param xpath 节点路径
     * @return 单个节点
     */
    public static Node getNode(Node node, String xpath) {
        // 非空判断
        requireNonNull(node, xpath);
        return node.selectSingleNode(xpath);
    }

    /**
     * 根据 node节点查找，获取 xpath下所有节点列表
     *
     * @param node 当前节点
     * @return 节点列表
     */
    public static List<Node> listNode(Node node, String xpath) {
        // 非空判断
        requireNonNull(node, xpath);
        List<Node> nodes = node.selectNodes(xpath);
        return (nodes == null || nodes.size() == 0) ? null : nodes;
    }

    /**
     * 根据根节点查找，获取 xpath下所有节点列表
     *
     * @param filePath xml文件路径
     * @param xpath    节点 xpath路径
     * @return 节点列表
     */
    public static List<Node> listNode(String filePath, String xpath) throws DocumentException {
        List<Node> nodes = getRootNode(filePath).selectNodes(xpath);
        return (nodes == null || nodes.size() == 0) ? null : nodes;
    }

    /**
     * 根据 xpath获取节点值
     *
     * @param xpath 节点路径
     * @return 节点值
     */
    public static String getText(String filePath, String xpath) throws DocumentException {
        Node node = getNode(filePath, xpath);
        return getText(node);
    }

    /**
     * 获取该节点的值
     *
     * @param node 目标节点
     * @return 节点值
     */
    public static String getText(Node node) {
        if (node != null) {
            return node.getText();
        }
        return null;
    }
}
