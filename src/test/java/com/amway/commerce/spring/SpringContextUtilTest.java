package com.amway.commerce.spring;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import static org.mockito.Mockito.when;

/**
 * @author: Jason.Hu
 * @date: 2023-10-09
 */
public class SpringContextUtilTest {

    @Data
    class A {
        private int id;
        private String desc;
        private Date date;
        private OffsetDateTime offsetDateTime;
        private LocalDateTime localDateTime;
        public A(){
        }
        public A(int id, String desc, Date date, OffsetDateTime offsetDateTime, LocalDateTime localDateTime) {
            this.id = id;
            this.desc = desc;
            this.date = date;
            this.offsetDateTime = offsetDateTime;
            this.localDateTime = localDateTime;
        }
    }

    @Test
    public void testGetBean() {
        ApplicationContext context = Mockito.mock(ApplicationContext.class);
        when(context.getBean("test")).thenReturn("测试");
        when(context.getBean(A.class)).thenReturn(new A());
        A a = new A(1, "a", new Date(), OffsetDateTime.now(), LocalDateTime.now());
        when(context.getBean("a", A.class)).thenReturn(a);

        // normal：字符串、类
        SpringContextUtil.setApplicationContext(context);
        Assert.assertEquals("测试", SpringContextUtil.getBean("test"));
        Assert.assertEquals(new A(), SpringContextUtil.getBean(A.class));
        Assert.assertEquals(a, SpringContextUtil.getBean("a", A.class));

        // boundary：空值
        Assert.assertEquals(null, SpringContextUtil.getBean(""));
        Assert.assertEquals(null, SpringContextUtil.getBean("", null));

        // exception：参数为null
        when(context.getBean(new String())).thenThrow(IllegalArgumentException.class);
        Assert.assertThrows(IllegalArgumentException.class, ()->{SpringContextUtil.getBean(new String());});
    }
}