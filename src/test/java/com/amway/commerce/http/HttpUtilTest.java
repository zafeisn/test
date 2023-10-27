package com.amway.commerce.http;

import com.amway.commerce.exception.CommonException;
import com.amway.commerce.serialization.JsonUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: Jason.Hu
 * @date: 2023-09-21
 */
public class HttpUtilTest {

    static class MyTask implements Runnable{

        private CyclicBarrier cyclicBarrier;

        private List<String> lists = new ArrayList<>();

        MyTask(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                // 线程等待
                cyclicBarrier.await();
                // 并发执行 doGet请求
                lists.add(HttpUtil.doGet("http://www.baidu.com"));
//                lists.add(HttpUtil.doPost("http://www.baidu.com"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testCreatePoolAndHttpClient() throws InterruptedException {
        // normal：使用自定义连接池和自定义httpClient
        PoolingHttpClientConnectionManager connectionManager = HttpUtil.managerMap.get(HttpUtil.DEFAULT_POOL_NAME);
        HttpUtil.setConnectionManager("pool01", 300, 200);
        HttpUtil.setConnectionManager("pool02", 300, 200);
        Assert.assertNotNull(HttpUtil.managerMap.get("pool01"));
        Assert.assertNotNull(HttpUtil.managerMap.get("pool02"));
        Assert.assertEquals(300,HttpUtil.managerMap.get("pool01").getMaxTotal());
        Assert.assertEquals(300,HttpUtil.managerMap.get("pool02").getMaxTotal());
        Assert.assertNotEquals(HttpUtil.managerMap.get("pool01"), connectionManager);
        Assert.assertNotEquals(HttpUtil.managerMap.get("pool02"), connectionManager);
        Assert.assertNotEquals(HttpUtil.managerMap.get("pool02"), HttpUtil.managerMap.get("pool01"));
        CloseableHttpClient defaultHttpClient = HttpUtil.httpClient;
        HttpUtil.createHttpClient("pool01", 3000, 3000, 3000, 10000);
        HttpUtil.createHttpClient("pool02", 3000, 3000, 3000, 10000);
        Assert.assertNotEquals(HttpUtil.httpClient, defaultHttpClient);
        Assert.assertNotEquals(HttpUtil.httpClient, defaultHttpClient);

        // boundary：并发，判断超过最大线程池数后，所有线程是否都能拿到数据
        HttpUtil.setConnectionManager("threadPool01", 2, 2);
        HttpUtil.createHttpClient("threadPool01", 2000, 2000, 2000, 20000);
        int num = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num);
        MyTask myTask = new MyTask(cyclicBarrier);
        Thread[] threads = new Thread[num];
        for (int i = 0; i < num; i++) {
            threads[i] = new Thread(myTask);
            threads[i].start();
        }
        for (int i = 0; i < num; i++) {
            threads[i].join();
        }
        Assert.assertEquals(num, myTask.lists.size());
        Assert.assertEquals(2, HttpUtil.managerMap.get("threadPool01").getMaxTotal());

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{HttpUtil.createHttpClient(null, 2000, 2000, 2000, 2000);});

    }

    @Test
    public void testDoGetWithDefaultPool() throws IOException, URISyntaxException {
        String url = "http://www.baidu.com";
        Map<String, String> params = new HashMap<>();
        params.put("test01", "value01");
        params.put("test02", "value02");
        String token = "我是token";
        String[] contentTypes = {HttpUtil.APPLICATION_JSON, HttpUtil.TEXT_HTML, HttpUtil.MULTIPART_FORM_DATA,
                HttpUtil.APPLICATION_URLENCODED, HttpUtil.APPLICATION_XML};

        // normal
        Assert.assertNotNull(HttpUtil.doGet(url,params));
        Assert.assertNotNull(HttpUtil.doGet(url,params, token));
        for (String contentType : contentTypes) {
            Assert.assertNotNull(HttpUtil.doGet(url, params, token, contentType));
        }

        // boundary：空值、null
        Assert.assertNotNull(HttpUtil.doGet(url, params, "", contentTypes[0]));
        Assert.assertNotNull(HttpUtil.doGet(url, params, token, ""));
        Assert.assertNotNull(HttpUtil.doGet(url, null, token, contentTypes[0]));
        Assert.assertNotNull(HttpUtil.doGet(url, params, null, contentTypes[0]));
        Assert.assertEquals(HttpUtil.doGet(url, params), HttpUtil.doGet(url, params, null, contentTypes[0]));
        Assert.assertNotNull(HttpUtil.doGet(url, params, token, null));
        Assert.assertEquals(HttpUtil.doGet(url, params, token), HttpUtil.doGet(url, params, token, contentTypes[0]));

        // exception：URL为空值或null
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doGet("", params);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doGet("", params, token);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doGet("", params, token, contentTypes[0]);});
        Assert.assertThrows(CommonException.class, ()->{HttpUtil.doGet(null, params, token, contentTypes[0]);});

    }

    @Test
    public void testDoGetWithCreatePool() throws IOException, URISyntaxException {
        String url = "http://www.baidu.com";
        Map<String, String> params = new HashMap<>();
        params.put("test01", "value01");
        params.put("test02", "value02");
        String token = "我是token";
        String[] contentTypes = {HttpUtil.APPLICATION_JSON, HttpUtil.TEXT_HTML, HttpUtil.MULTIPART_FORM_DATA,
                HttpUtil.APPLICATION_URLENCODED, HttpUtil.APPLICATION_XML};

        // normal
        HttpUtil.setConnectionManager("pool01", 300, 200);
        HttpUtil.createHttpClient("pool01", 3000, 3000, 3000, 10000);
        Assert.assertNotNull(HttpUtil.doGet(url,params));
        Assert.assertNotNull(HttpUtil.doGet(url,params, token));
        for (String contentType : contentTypes) {
            Assert.assertNotNull(HttpUtil.doGet(url, params, token, contentType));
        }

        // boundary：空值、null
        Assert.assertNotNull(HttpUtil.doGet(url, params, "", contentTypes[0]));
        Assert.assertNotNull(HttpUtil.doGet(url, params, token, ""));
        Assert.assertNotNull(HttpUtil.doGet(url, null, token, contentTypes[0]));
        Assert.assertNotNull(HttpUtil.doGet(url, params, null, contentTypes[0]));
        Assert.assertEquals(HttpUtil.doGet(url, params), HttpUtil.doGet(url, params, null, contentTypes[0]));
        Assert.assertNotNull(HttpUtil.doGet(url, params, token, null));
        Assert.assertEquals(HttpUtil.doGet(url, params, token), HttpUtil.doGet(url, params, token, contentTypes[0]));

        // exception：URL为空值或null
        Assert.assertThrows(CommonException.class, ()->{HttpUtil.doGet(null, params, token, contentTypes[0]);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doGet("1111", params, token, contentTypes[0]);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doGet("", params);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doGet("", params, token);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doGet("", params, token, contentTypes[0]);});

    }

    @Test
    public void testDoPostWithDefaultPool() throws IOException {

        String url = "http://www.baidu.com";
        Map<String, String> params = new HashMap<>();
        params.put("uname", "zhangsan");
        params.put("pwd", "123456");

        // normal
        Assert.assertNotNull(HttpUtil.doPost(url));
        Assert.assertNotNull(HttpUtil.doPost(url, params));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params)));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.APPLICATION_XML));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.APPLICATION_JSON));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.TEXT_HTML));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.MULTIPART_FORM_DATA));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.APPLICATION_FORM_URLENCODED));
        Assert.assertEquals(HttpUtil.doPost(url, params), HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.APPLICATION_FORM_URLENCODED));

        // boundary：空值、null
        Assert.assertEquals(HttpUtil.doPost(url), HttpUtil.doPost(url, new HashMap<>()));
        Assert.assertEquals(HttpUtil.doPost(url), HttpUtil.doPost(url, JsonUtil.toJson(null)));
        Assert.assertEquals(HttpUtil.doPost(url), HttpUtil.doPost(url, JsonUtil.toJson(params), null));
        Assert.assertEquals(HttpUtil.doPost(url), HttpUtil.doPost(url, "", null));

        // exception：URL为空值或null
        Assert.assertThrows(CommonException.class, ()->{HttpUtil.doPost(null, JsonUtil.toJson(params), ContentType.APPLICATION_JSON);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doPost("", params);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doPost("", JsonUtil.toJson(params));});

    }

    @Test
    public void testDoPostWithCreatePool() throws IOException {
        // normal
        String url = "http://www.baidu.com";
        Map<String, String> params = new HashMap<>();
        params.put("uname", "zhangsan");
        params.put("pwd", "123456");
        // normal
        HttpUtil.setConnectionManager("pool01", 300, 200);
        HttpUtil.createHttpClient("pool01", 3000, 3000, 3000, 10000);
        Assert.assertNotNull(HttpUtil.doPost(url));
        Assert.assertNotNull(HttpUtil.doPost(url, params));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params)));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.APPLICATION_XML));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.APPLICATION_JSON));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.TEXT_HTML));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.MULTIPART_FORM_DATA));
        Assert.assertNotNull(HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.APPLICATION_FORM_URLENCODED));
        Assert.assertEquals(HttpUtil.doPost(url, params), HttpUtil.doPost(url, JsonUtil.toJson(params), ContentType.APPLICATION_FORM_URLENCODED));

        // boundary：空值、null
        Assert.assertEquals(HttpUtil.doPost(url), HttpUtil.doPost(url, new HashMap<>()));
        Assert.assertEquals(HttpUtil.doPost(url), HttpUtil.doPost(url, JsonUtil.toJson(null)));
        Assert.assertEquals(HttpUtil.doPost(url), HttpUtil.doPost(url, JsonUtil.toJson(params), null));
        Assert.assertEquals(HttpUtil.doPost(url), HttpUtil.doPost(url, "", null));

        // exception：URL为空值或null
        Assert.assertThrows(CommonException.class, ()->{HttpUtil.doPost(null, JsonUtil.toJson(params), ContentType.APPLICATION_JSON);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doPost("", params);});
        Assert.assertThrows(ClientProtocolException.class, ()->{HttpUtil.doPost("", JsonUtil.toJson(params));});

    }
}