package com.amway.commerce.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: Jason.Hu
 * @date: 2023-08-22
 * @desc: Http工具类
 */
public class HttpUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final int SUCCESS_CODE = 200;

    /**
     * 无参数 get请求
     *
     * @param url 请求地址
     * @return 响应内容
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * 带参数 get请求
     *
     * @param url    请求地址
     * @param params uri参数
     * @return 响应内容
     */
    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }

    /**
     * 带认证信息的 get请求
     *
     * @param url    请求地址
     * @param params uri参数
     * @param token  token
     * @return 响应内容
     */
    public static String doGet(String url, Map<String, String> params, String token) {
        // 创建一个默认可关闭的 Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 设置返回值
        String resultMsg = "";
        CloseableHttpResponse response = null;
        // 定义 HttpResponse对象
        try {
            // URIBuilder用于构造 URI
            URIBuilder builder = new URIBuilder(url);
            // 创建 URI，设置 host等参数
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            URI uri = builder.build();
            // 创建 HttpGET请求
            HttpGet httpGet = new HttpGet(uri);
            if (token != null) {
                // Http所需认证
                httpGet.addHeader("Authorization", token);
                httpGet.addHeader("Content-Type", "application/json");
            }
            // 设置请求的请求头
            // 看是否需要登录校验 keyhttpGet.setHeader(key,value);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态为 200则给返回值赋值
            resultMsg = getSuccessResult(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            close(response, httpClient);
        }
        return resultMsg;
    }

    /**
     * 无参数 post请求
     *
     * @param url 请求地址
     * @return 响应内容
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 带参数 post请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应内容
     */
    public static String doPost(String url, Map<String, String> params) {
        // 创建一个默认可关闭的 Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultMsg = "";
        try {
            // 创建 HttpPost请求
            HttpPost httpPost = new HttpPost(url);
            // 设置 post请求的请求头
            // 看是否需要登录校验 httpPost.setHeader(key,value);
            // 创建参数列表
            if (params != null) {
                // NameValuePair用于存储 key-value数据，可以用于 Http请求中的参数传递和存储配置信息等场景
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    // BasicNameValuePair是一种数据结构，用于存储和表示键值对
                    paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                // UrlEncodedFormEntity设置 body，消息体内容类似于 KEY1=VALUE1&KEY2=VALUE2&...
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行 http请求
            response = httpClient.execute(httpPost);
            // 判断是否成功响应
            resultMsg = getSuccessResult(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            close(response, httpClient);
        }
        return resultMsg;
    }

    /**
     * 带 json参数 post请求
     *
     * @param url  请求地址
     * @param json 请求的json数据
     * @return 响应内容
     */
    public static String doPostJson(String url, String json) {
        // 创建一个默认可关闭的 Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultMsg = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            // 设置 post请求的请求头
            // 看是否需要登录校验 httpPost.setHeader(key,value);
            // 指定传输参数为 json
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行 http请求
            response = httpClient.execute(httpPost);
            // 判断是否成功响应
            resultMsg = getSuccessResult(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            close(response, httpClient);
        }
        return resultMsg;
    }

    /**
     * 获取成功响应结果
     *
     * @param response Http响应
     * @return 响应结果
     */
    private static String getSuccessResult(CloseableHttpResponse response) throws IOException {
        String resultMsg = "";
        if (response.getStatusLine().getStatusCode() == SUCCESS_CODE) {
            resultMsg = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
        }
        return resultMsg;
    }

    /**
     * 资源关闭
     *
     * @param response   Http响应
     * @param httpClient Http客户端
     */
    private static void close(CloseableHttpResponse response, CloseableHttpClient httpClient) {
        try {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
