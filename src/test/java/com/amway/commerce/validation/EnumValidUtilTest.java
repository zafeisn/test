package com.amway.commerce.validation;

import com.amway.commerce.exception.CommonException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author: Jason.Hu
 * @date: 2023-09-21
 */
public class EnumValidUtilTest {

    @Getter
    @AllArgsConstructor
    public enum EA {

        A(1, "A1"),

        B(2, "B2"),

        C(3, "C3"),

        ;

        private int id;
        private String value;
    }

    @Test
    public void testInputIsEnumFiled() throws IllegalAccessException {

        // normal：英文、中文
        Assert.assertTrue(EnumValidUtil.isEnumFiled(EA.class, "A"));
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, "a"));
        Assert.assertTrue(EnumValidUtil.isEnumFiled(EA.class, "B"));
        Assert.assertTrue(EnumValidUtil.isEnumFiled(EA.class, "C"));
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, "D"));
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, "E"));
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, "F"));
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, "你好世界"));

        // boundary：空值、空格、换行符、input参数为null
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, ""));
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, " "));
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, "\n"));
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, null));

        // exception：非枚举类、enumClass参数为null
        Assert.assertThrows(CommonException.class, ()->{EnumValidUtil.isEnumFiled(String.class, "a");});
        Assert.assertThrows(NullPointerException.class, ()->{EnumValidUtil.isEnumFiled(null, "a");});

    }

    @Test
    public void testInputIsEnumFiledName() throws IllegalAccessException, NoSuchFieldException {

        // normal：中文、英文
        Assert.assertTrue(EnumValidUtil.isEnumFiled(EA.class, "A", "A"));
        Assert.assertTrue(EnumValidUtil.isEnumFiled(EA.class, "B", "B"));
        Assert.assertTrue(EnumValidUtil.isEnumFiled(EA.class, "C", "C"));
        Assert.assertFalse(EnumValidUtil.isEnumFiled(EA.class, "C", "中文"));

        // boundary：空值、空格、特殊字符
        Assert.assertEquals(false, EnumValidUtil.isEnumFiled(EA.class, "A", ""));
        Assert.assertEquals(false, EnumValidUtil.isEnumFiled(EA.class, "B", " "));
        Assert.assertEquals(false, EnumValidUtil.isEnumFiled(EA.class, "C","\n"));
        Assert.assertEquals(false, EnumValidUtil.isEnumFiled(EA.class, "C","\\"));
        Assert.assertEquals(false, EnumValidUtil.isEnumFiled(EA.class, "C","."));

        // exception：非枚举类、fieldName参数为null、不属于枚举类的属性名
        // 非枚举类
        Assert.assertThrows(CommonException.class, ()->{EnumValidUtil.isEnumFiled(String.class, "a", "D");});
        // fieldName参数为null、不属于枚举类的属性名
        Assert.assertThrows(NullPointerException.class,()->{EnumValidUtil.isEnumFiled(EA.class, null, " ");});
        Assert.assertThrows(NoSuchFieldException.class, ()->{EnumValidUtil.isEnumFiled(EA.class, "D", "D");});
        Assert.assertThrows(NoSuchFieldException.class, ()->{EnumValidUtil.isEnumFiled(EA.class, "", "B");});
        Assert.assertThrows(NoSuchFieldException.class, ()->{EnumValidUtil.isEnumFiled(EA.class, " ", "C");});
        Assert.assertThrows(NoSuchFieldException.class, ()->{EnumValidUtil.isEnumFiled(EA.class, "你好世界", "B");});

    }
}