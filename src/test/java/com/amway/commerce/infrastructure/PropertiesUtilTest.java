package com.amway.commerce.infrastructure;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * @author: Jason.Hu
 * @date: 2023-09-20
 */
public class PropertiesUtilTest {

    class MyCyclicBarrierWorker implements Runnable{

        private CyclicBarrier cyclicBarrier;
        Properties properties;

        MyCyclicBarrierWorker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @SneakyThrows
        @Override
        public void run() {
            try {
                properties = PropertiesUtil.getProperties("key.properties");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            Assert.assertSame(properties, PropertiesUtil.getProperties("key.properties"));
        }
    }

    @Test
    public void testGetPropertiesAndGetProperty() throws InterruptedException {
        // normal：单个去获取
        Assert.assertNotNull(PropertiesUtil.getProperties("key.properties"));
        Assert.assertNotNull(PropertiesUtil.getProperties("redis.properties"));
        Assert.assertEquals("127.0.0.1",PropertiesUtil.getProperties("redis.properties").getProperty("redis-local-host"));
        Assert.assertEquals("\"我是中文\"",PropertiesUtil.getProperties("key.properties").getProperty("rsa.privateKey"));
        Properties properties = PropertiesUtil.getProperties("key.properties");
        // 并发线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        MyCyclicBarrierWorker myCyclicBarrierWorker = new MyCyclicBarrierWorker(cyclicBarrier);
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(myCyclicBarrierWorker);
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        Properties properties02 = PropertiesUtil.getProperties("key.properties");
        Assert.assertEquals(properties, properties02);

        // boundary：属性名不存在
        Assert.assertEquals(null,PropertiesUtil.getProperty("redis.properties","dddd"));
        Assert.assertEquals(null,PropertiesUtil.getProperty("redis.properties","中文"));

        // exception：文件不存在
        Assert.assertThrows(CommonException.class,()->{PropertiesUtil.getProperties(null);});
        Assert.assertThrows(NullPointerException.class,()->{PropertiesUtil.getProperties("keys.properties1");});
        Assert.assertThrows(NullPointerException.class,()->{PropertiesUtil.getProperties("redis1.properties");});
    }
}