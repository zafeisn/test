package com.amway.commerce.serialization;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.List;

/**
 * @author: Jason.Hu
 * @date: 2023-08-17
 */
@Slf4j
public class XMLUtil {

    /**
     * 获取 XML文档的根元素。
     * 该方法需要对参数进行非空判断，若 filePath为空，则抛出参数不能为空异常。
     *
     * @param filePath XML文件路径，不可为空
     * @return XML文档的根元素
     *
     * <p>
     * <b>例：</b><br>
     * filePath=null，抛出“参数不能为空”异常提示信息。
     */
    public static Node getRootNode(String filePath) throws DocumentException {
        return getDocument(filePath).getRootElement();
    }

    /**
     * 根据 xpath表达式选择并返回符合条件的第一个节点。
     * 该方法需要对参数进行非空判断，若 filePath或 xpath为空，则抛出参数不能为空异常。
     *
     * @param filePath XML文件路径，不可为空
     * @param xpath    节点 xpath路径，不可为空
     * @return 符合条件的第一个节点
     *
     * <p>
     * <b>例：</b><br>
     * filePath=null，xpath="/xpath"，抛出“参数不能为空”异常提示信息；<p>
     * filePath="filePath"，xpath=null，抛出“参数不能为空”异常提示信息。
     */
    public static Node getNode(String filePath, String xpath) throws DocumentException {
        isNotNull(xpath);
        return getRootNode(filePath).selectSingleNode(xpath);
    }

    /**
     * 根据 XPath表达式选择并返回符合条件的所有节点。
     * 该方法需要对参数进行非空判断，若 filePath或 xpath为空，则抛出参数不能为空异常。
     *
     * @param filePath XML文件路径，不可为空
     * @param xpath    节点 xpath路径，不可为空
     * @return 节点列表，符合条件的所有节点
     *
     * <p>
     * <b>例：</b><br>
     * filePath=null，xpath="/xpath"，抛出“参数不能为空”异常提示信息；<p>
     * filePath="filePath"，xpath=null，抛出“参数不能为空”异常提示信息。
     */
    public static List<Node> listNode(String filePath, String xpath) throws DocumentException {
        isNotNull(xpath);
        List<Node> nodes = getRootNode(filePath).selectNodes(xpath);
        return (nodes == null || nodes.size() == 0) ? null : nodes;
    }

    /**
     * 根据 xpath获取节点值。
     * 该方法需要对参数进行非空判断，若 filePath或 xpath为空，则抛出参数不能为空异常。
     *
     * @param filePath XML文件路径，不可为空
     * @param xpath 节点 xpath路径，不可为空
     * @return 节点值
     *
     * <p>
     * <b>例：</b><br>
     * filePath=null，xpath="/xpath"，抛出“参数不能为空”异常提示信息；<p>
     * filePath="filePath"，xpath=null，抛出“参数不能为空”异常提示信息。
     */
    public static String getText(String filePath, String xpath) throws DocumentException {
        Node node = getNode(filePath, xpath);
        return node.getText();
    }

    /**
     * 获取 XML文件的 Document对象。
     * 该方法需要对参数进行非空判断，若 filePath为空，则抛出参数不能为空异常。
     *
     * @param filePath XML文件路径，不可为空
     * @return Document对象
     * @throws DocumentException
     *
     * <p>
     * <b>例：</b><br>
     * filePath=null，抛出“参数不能为空”异常提示信息。
     */
    public static Document generateXML(String filePath) throws DocumentException {
        return getDocument(filePath);
    }

    /**
     * 将 XML字符串解析为 Document对象。
     * 该方法需要对参数进行非空判断，若 xmlString为空，则抛出参数不能为空异常。
     *
     * @param xmlString XML字符串，不可为空
     * @return Document对象
     * @throws DocumentException
     *
     * <p>
     * <b>例：</b><br>
     * xmlString=null，抛出“参数不能为空”异常提示信息。
     */
    public static Document parseText(String xmlString) throws DocumentException {
        isNotNull(xmlString);
        return DocumentHelper.parseText(xmlString);
    }

    /**
     * 将 Document对象转换为 XML字符串。
     * 该方法需要对参数进行非空判断，若 document为空，则抛出参数不能为空异常。
     *
     * @param document Document对象，不可为空
     * @return XML字符串
     *
     * <p>
     * <b>例：</b><br>
     * document=null，抛出“参数不能为空”异常提示信息。
     */
    public static String asXML(Document document) {
        isNotNull(document);
        return document.asXML();
    }

    private static Document getDocument(String filePath) throws DocumentException {
        // 文件路径校验
        isNotNull(filePath);
        File file = new File(filePath);
        SAXReader reader = new SAXReader();
        // 读取并解析文件
        return reader.read(file);
    }

    private static void isNotNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new CommonException(CommonError.NotNull.getMessage());
            }
        }
    }

}
