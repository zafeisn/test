package com.amway.commerce.http;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: Jason.Hu
 * @date: 2023-08-22
 */
@Slf4j
public class HttpUtil {

    public static final String APPLICATION_URLENCODED = "application/x-www-form-urlencoded";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_HTML = "text/html";
    public static final String APPLICATION_XML = "application/xml";

    /**
     * 成功请求码 200
     */
    private static final int SUCCESS_CODE = 200;
    /**
     * 连接池的最大连接数
     */
    public static int POOL_MAX_TOTAL = 300;
    /*
     * 每一个路由的最大连接数
     */
    private static int POOL_MAX_PRE_ROUTE = 200;
    /**
     * 客户端请求服务器建立连接的超时时间
     */
    private static int CONNECT_TIMEOUT = 2000;
    /*
     * 请求获取数据的超时时间
     */
    private static int SOCKET_TIMEOUT = 2000;
    /**
     * 从连接池中获取可用连接的超时时间
     */
    private static int CONNECTION_REQUEST_TIMEOUT = 2000;
    /**
     * 空闲连接驱逐策略，设置最大空闲时间，清理过期的连接
     */
    private static long MAX_IDLE_TIME = 60;
    /**
     * Http请求客户端
     */
    public static CloseableHttpClient httpClient;
    /**
     * Http连接池
     */
    private static PoolingHttpClientConnectionManager connectionManager;
    /**
     * HttpClient请求超时配置类
     */
    private static RequestConfig requestConfig;
    /**
     * 线程池集合，用于存放创建的连接池
     */
    public static Map<String, PoolingHttpClientConnectionManager> managerMap = new HashMap<>();
    /**
     * 默认连接池名称，作为  Map集合的键名
     */
    public static final String DEFAULT_POOL_NAME = "defaultPool";

    static {
        // 连接池配置
        connectionManager = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        connectionManager.setMaxTotal(POOL_MAX_TOTAL);
        // 设置并发访问数
        connectionManager.setDefaultMaxPerRoute(POOL_MAX_PRE_ROUTE);
        // 存入 map
        managerMap.put(DEFAULT_POOL_NAME, connectionManager);
        // HttpClient请求超时配置
        requestConfig = RequestConfig.custom()
                // 设置从连接池获取连接的超时时间
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                // 设置连接超时时间
                .setConnectTimeout(CONNECT_TIMEOUT)
                // 设置请求数据超时时间
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();
        httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                // 空闲连接驱逐策略，设置最大空闲时间，清理过期的连接
                .evictIdleConnections(MAX_IDLE_TIME, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 创建自定义 PoolingHttpClientConnectionManager连接池，需要设置连接池名称、最大连接数和并发访问数，创建完成后通过在 managerMap集合中取出使用。
     * 该方法需要对参数进行非空判断，若 poolName为空，则抛出参数不能为空异常。
     *
     * @param poolName        连接池名称，不可为空
     * @param poolMaxTotal    最大连接数
     * @param poolMaxPreRoute 并发访问数
     *
     * <p>
     * <b>例：</b><br>
     * poolName=null，poolMaxTotal=3，poolMaxPreRoute=2，抛出“参数不能为空”异常提示信息；<p>
     * poolName="pool01"，poolMaxTotal=3，poolMaxPreRoute=2，使用时通过 HttpUtil.managerMap.get(poolName)获取。
     */
    public static void setConnectionManager(String poolName, int poolMaxTotal, int poolMaxPreRoute) {
        isNotNull(poolName);
        connectionManager = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        connectionManager.setMaxTotal(poolMaxTotal);
        // 设置并发访问数
        connectionManager.setDefaultMaxPerRoute(poolMaxPreRoute);
        // 存入 map集合中
        managerMap.put(poolName, connectionManager);
    }

    /**
     * 使用指定连接池来管理自定义创建的 HttpClient客户端，需要提供连接池名以及设置请求连接超时时间、请求数据超时时间以及从连接池获取连接的超时时间、最大空闲时间。
     * 该方法需要对参数进行非空判断，若 poolName为空，则抛出参数不能为空异常。
     *
     * @param poolName                 连接池名称，不可为空
     * @param connectTimeout           HttpClient请求连接超时时间
     * @param socketTimeout            HttpClient请求数据超时时间
     * @param connectionRequestTimeout 从连接池获取连接的超时时间
     * @param maxIdleTime              最大空闲时间，单位秒
     *
     * <p>
     * <b>例：</b><br>
     * poolName=null，connectTimeout=3000，socketTimeout=2000，connectionRequestTimeout=2000，maxIdleTime=2000，抛出“参数不能为空”异常提示信息；<p>
     * poolName="pool not find"，connectTimeout=3000，socketTimeout=2000，connectionRequestTimeout=2000，maxIdleTime=2000，抛出“未找到集合中键对应的值”异常提示信息；<p>
     * poolName=HttpUtil.DEFAULT_POOL_NAME，connectTimeout=3000，socketTimeout=2000，connectionRequestTimeout=2000，maxIdleTime=2000，完成 HttpClient的创建即可发送请求；<p>
     * poolName="pool in map"，connectTimeout=3000，socketTimeout=2000，connectionRequestTimeout=2000，maxIdleTime=2000，完成 HttpClient的创建即可发送请求。
     */
    public static void createHttpClient(String poolName, int connectTimeout,
                                        int socketTimeout, int connectionRequestTimeout, int maxIdleTime) {
        isNotNull(poolName);
        connectionManager = managerMap.get(poolName);
        // 判断 map集合中是否存在该连接池
        isNotNull(connectTimeout);
        // HttpClient请求超时配置
        requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).
                setConnectionRequestTimeout(connectionRequestTimeout).build();
        // 创建 HttpClient客户端
        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionManager)
                .evictIdleConnections(maxIdleTime, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 发送无参数 Get请求。
     * 该方法需要对参数进行非空判断，若 url为空，则抛出参数不能为空异常。
     *
     * @param url 请求地址，不可为空
     * @return 响应内容，非成功状态码 200的响应结果为空字符串
     * @throws IOException
     * @throws URISyntaxException URL不正确
     *
     * <p>
     * <b>例：</b><br>
     * url=null，抛出“参数不能为空”异常提示信息。
     */
    public static String doGet(String url) throws IOException, URISyntaxException {
        return doGet(url, null);
    }

    /**
     * 发送带参数的 Get请求。
     * 该方法需要对参数进行非空判断，若 url为空，则抛出参数不能为空异常。
     *
     * @param url    请求地址，不可为空
     * @param params URL请求参数，Map集合
     * @return 响应内容，非成功状态码 200的响应结果为空字符串
     * @throws IOException
     * @throws URISyntaxException URL不正确
     *
     * <p>
     * <b>例：</b><br>
     * url=null，params="paramsMap"，抛出“参数不能为空”异常提示信息。
     */
    public static String doGet(String url, Map<String, String> params) throws IOException, URISyntaxException {
        return doGet(url, params, null);
    }

    /**
     * 发送带参数和 token认证信息的 Get请求，contentType默认使用 application/json请求格式。
     * 该方法需要对参数进行非空判断，若 url为空，则抛出参数不能为空异常。
     *
     * @param url    请求地址，不可为空
     * @param params URL请求参数，Map集合
     * @param token  token字符串
     * @return 响应内容，非成功状态码 200的响应结果为空字符串
     * @throws IOException
     * @throws URISyntaxException URL不正确
     *
     * <p>
     * <b>例：</b><br>
     * url=null，params=paramsMap，token="is token"，抛出“参数不能为空”异常提示信息。
     */
    public static String doGet(String url, Map<String, String> params, String token) throws IOException, URISyntaxException {
        return doGet(url, params, token, APPLICATION_JSON);
    }

    /**
     * 发送带参数、token和 contentType请求格式的 Get请求。
     * 该方法需要对参数进行非空判断，若 url为空，则抛出参数不能为空异常。
     *
     * @param url    请求地址，不可为空
     * @param params URL请求参数，Map集合
     * @param token  token字符串
     * @return 响应内容，非成功状态码 200的响应结果为空字符串
     * @throws IOException
     * @throws URISyntaxException URL不正确
     *
     * <p>
     * <b>例：</b><br>
     * url=null，params=paramsMap，token="is token"，contentType=HttpUtil.APPLICATION_JSON，抛出“参数不能为空”异常提示信息。
     */
    public static String doGet(String url, Map<String, String> params, String token, String contentType) throws IOException, URISyntaxException {
        isNotNull(url);
        long start = System.currentTimeMillis();
        String result = "";
        // 定义 HttpResponse对象
        CloseableHttpResponse response = null;
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
                httpGet.addHeader("Content-Type", contentType);
            }
            // 执行请求
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == SUCCESS_CODE) {
                result = EntityUtils.toString(response.getEntity());
            } else {
                log.info("request to:{},param:{},response code:{},cost {} ms",
                        new Object[]{url, params, statusCode, System.currentTimeMillis() - start});
            }
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return result;
    }

    /**
     * 发送无参数 Post请求。
     * 该方法需要对参数进行非空判断，若 url为空，则抛出参数不能为空异常。
     *
     * @param url 请求地址，不可为空
     * @return 响应内容，非成功状态码 200的响应结果为空字符串
     * @throws IOException
     *
     * <p>
     * <b>例：</b><br>
     * url=null，抛出“参数不能为空”异常提示信息。
     */
    public static String doPost(String url) throws IOException {
        return doPost(url, null, null);
    }

    /**
     * 发送带参数的 Post请求。
     * 该方法需要对参数进行非空判断，若 url为空，则抛出参数不能为空异常。
     *
     * @param url    请求地址，不可为空
     * @param params URL请求参数，Map集合
     * @return 响应内容，非成功状态码 200的响应结果为空字符串
     * @throws IOException
     *
     * <p>
     * <b>例：</b><br>
     * url=null，params=paramsMap，抛出“参数不能为空”异常提示信息。
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        isNotNull(url);
        long start = System.currentTimeMillis();
        String result = "";
        // 定义 HttpResponse对象
        CloseableHttpResponse response = null;
        try {
            // 创建 HttpPost请求
            HttpPost httpPost = new HttpPost(url);
            // 设置 post请求的请求头
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
            // 执行请求
            response = httpClient.execute(httpPost);
            // 获取响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == SUCCESS_CODE) {
                result = EntityUtils.toString(response.getEntity());
            } else {
                log.info("request to:{},param:{},response code:{},cost {} ms",
                        new Object[]{url, params, statusCode, System.currentTimeMillis() - start});
            }
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return result;
    }

    /**
     * 发送带参数的 Post请求。
     * 该方法需要对参数进行非空判断，若 url为空，则抛出参数不能为空异常。
     *
     * @param url  请求地址，不可为空
     * @param jsonStr URL请求参数，JSON字符串
     * @return 响应内容，非成功状态码 200的响应结果为空字符串
     * @throws IOException
     *
     * <p>
     * <b>例：</b><br>
     * url=null，jsonStr="{"p1": "a","p2": "b"}"，抛出“参数不能为空”异常提示信息。
     */
    public static String doPost(String url, String jsonStr) throws IOException {
        return doPost(url, jsonStr, ContentType.APPLICATION_JSON);
    }

    /**
     * 发送带请求数据和 contentType请求格式的 Post请求。
     * 该方法需要对参数进行非空判断，若 url为空，则抛出参数不能为空异常。
     *
     * @param url         请求地址，不可为空
     * @param content     请求数据
     * @param contentType 请求格式
     * @return 响应内容，非成功状态码 200的响应结果为空字符串
     *
     * <p>
     * <b>例：</b><br>
     * url=null，content="{"name": "Jason","pwd": "123456"}"，contentType=ContentType.APPLICATION_FORM_URLENCODED，抛出“参数不能为空”异常提示信息。
     */
    public static String doPost(String url, String content, ContentType contentType) throws IOException {
        isNotNull(url);
        long start = System.currentTimeMillis();
        String result = "";
        // 定义 HttpResponse对象
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            if (content != null) {
                // 设置 post请求的请求头
                StringEntity entity = new StringEntity(content, contentType);
                httpPost.setEntity(entity);
            }
            // 执行请求
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            // 200
            if (statusCode == SUCCESS_CODE) {
                result = EntityUtils.toString(response.getEntity());
            } else {
                log.info("request to:{},param:{},response code:{},cost {} ms",
                        new Object[]{url, content, statusCode, System.currentTimeMillis() - start});
            }
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return result;
    }

    private static void isNotNull(Object obj) {
        if (obj == null) {
            throw new CommonException(CommonError.NotNull.getMessage());
        }
    }

}
