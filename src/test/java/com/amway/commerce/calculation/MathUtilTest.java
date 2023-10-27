package com.amway.commerce.calculation;

import com.amway.commerce.exception.CommonException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Jason.Hu
 * @date: 2023-09-19
 * @desc:
 */
public class MathUtilTest {

    @Test
    public void testAddWithNoScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("4"), MathUtil.add(new BigDecimal("2"), new BigDecimal("2")));
        Assert.assertEquals(new BigDecimal("1002"), MathUtil.add(new BigDecimal("1000"), new BigDecimal("2")));
        Assert.assertEquals(new BigDecimal("4.231"), MathUtil.add(new BigDecimal("2"), new BigDecimal("2.231")));
        Assert.assertEquals(new BigDecimal("4.62"), MathUtil.add(new BigDecimal("2.31"), new BigDecimal("2.31")));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.add(new BigDecimal("-2.31"), new BigDecimal("2.31")));
        Assert.assertEquals(new BigDecimal("-0.009"), MathUtil.add(new BigDecimal("-2.31"), new BigDecimal("2.301")));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("-4.62"), MathUtil.add(new BigDecimal("-2.31"), new BigDecimal("-2.31")));
        Assert.assertEquals(new BigDecimal("-2303.31"), MathUtil.add(new BigDecimal("-2.31"), new BigDecimal("-2301")));

        // boundary：0，0.000000000000001，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0"), MathUtil.add(new BigDecimal("0"), new BigDecimal("0")));
        Assert.assertEquals(new BigDecimal("0.0"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("0")));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("0.000")));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("-0.000")));
        // 0和 0.000000000000001
        Assert.assertEquals(new BigDecimal("0.000000000000001"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("0.000000000000001")));
        Assert.assertEquals(new BigDecimal("-0.000000000000001"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("-0.000000000000001")));
        // 0和 极限值
        Assert.assertEquals(new BigDecimal("9999999999"), MathUtil.add(new BigDecimal("9999999999"), new BigDecimal("0")));
        Assert.assertEquals(new BigDecimal("-9999999999.0"), MathUtil.add(new BigDecimal("-9999999999"), new BigDecimal("0.0")));
        // 0.000000000000001和极限值
        Assert.assertEquals(new BigDecimal("-9999999999.999999899999999"), MathUtil.add(new BigDecimal("-9999999999.9999999"), new BigDecimal("0.000000000000001")));
        Assert.assertEquals(new BigDecimal("-9999999999.999999900000001"), MathUtil.add(new BigDecimal("-9999999999.9999999"), new BigDecimal("-0.000000000000001")));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("19999999998"), MathUtil.add(new BigDecimal("9999999999"), new BigDecimal("9999999999")));
        Assert.assertEquals(new BigDecimal("-19999999998"), MathUtil.add(new BigDecimal("-9999999999"), new BigDecimal("-9999999999")));
        Assert.assertEquals(new BigDecimal("-19999999999.9999998"), MathUtil.add(new BigDecimal("-9999999999.9999999"), new BigDecimal("-9999999999.9999999")));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{MathUtil.add(null, BigDecimal.ZERO);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.add(BigDecimal.ZERO, null);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.add(null, null);});

    }

    @Test
    public void testAddWithScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("4.00"), MathUtil.add(new BigDecimal("2"), new BigDecimal("2"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("4.231"), MathUtil.add(new BigDecimal("2"), new BigDecimal("2.231"), 3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("4.6200"), MathUtil.add(new BigDecimal("2.31"), new BigDecimal("2.31"), 4, RoundingMode.HALF_UP));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.add(new BigDecimal("-2.31"), new BigDecimal("2.31"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-0.00900"), MathUtil.add(new BigDecimal("-2.31"), new BigDecimal("2.301"), 5, RoundingMode.HALF_DOWN));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("-4.00"), MathUtil.add(new BigDecimal("-2"), new BigDecimal("-2"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-4.231"), MathUtil.add(new BigDecimal("-2"), new BigDecimal("-2.231"), 3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-4.6200"), MathUtil.add(new BigDecimal("-2.31"), new BigDecimal("-2.31"), 4, RoundingMode.HALF_UP));

        // boundary：0，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0"), MathUtil.add(new BigDecimal("0"), new BigDecimal("0"), 0, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("0"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("0.000"), 0, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("0.000"), 3, RoundingMode.DOWN));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("-0.000"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("0.000000000000001"), 2, RoundingMode.HALF_UP));
        // 0和 0.000000000000001
        Assert.assertEquals(new BigDecimal("-0.000000000000001"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("-0.000000000000001"), 15, RoundingMode.HALF_DOWN));
        // 0和 极限值
        Assert.assertEquals(new BigDecimal("-9999999999.0"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("-9999999999"), 1, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("9999999999.00"), MathUtil.add(new BigDecimal("0.0"), new BigDecimal("9999999999"), 2, RoundingMode.HALF_DOWN));
        // 0.000000000000001和极限值
        Assert.assertEquals(new BigDecimal("-9999999999.00"), MathUtil.add(new BigDecimal("-9999999999"), new BigDecimal("-0.000000000000001"),2 ,RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-9999999999.000"), MathUtil.add(new BigDecimal("-9999999999"), new BigDecimal("0.000000000000001"),3 ,RoundingMode.HALF_UP));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("19999999998.00"), MathUtil.add(new BigDecimal("9999999999"), new BigDecimal("9999999999"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-19999999998"), MathUtil.add(new BigDecimal("-9999999999"), new BigDecimal("-9999999999"),0 ,RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-19999999999.9999998"), MathUtil.add(new BigDecimal("-9999999999.9999999"), new BigDecimal("-9999999999.9999999"), 7, RoundingMode.HALF_EVEN));
        Assert.assertEquals(new BigDecimal("-20000000000.000"), MathUtil.add(new BigDecimal("-9999999999.9999999"), new BigDecimal("-9999999999.9999999"), 3, RoundingMode.HALF_UP));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{MathUtil.add(null, BigDecimal.ZERO, 1, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.add(BigDecimal.ZERO, null,1, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.add(null, null,1, RoundingMode.HALF_UP);});

    }

    @Test
    public void testSumWithNoScale() {
        // normal：正数列表，负数列表，正负数列表
        // 正数列表
        BigDecimal[] nums_01 = {new BigDecimal("123"), new BigDecimal("234"), new BigDecimal("1.234")};
        List<BigDecimal> numList_01 = Arrays.asList(nums_01);
        Assert.assertEquals(new BigDecimal("358.234"), MathUtil.sum(numList_01));
        // 负数列表
        BigDecimal[] nums_02 = {new BigDecimal("-345"), new BigDecimal("-1.234455"), new BigDecimal("-234.45")};
        List<BigDecimal> numList_02 = Arrays.asList(nums_02);
        Assert.assertEquals(new BigDecimal("-580.684455"), MathUtil.sum(numList_02));
        // 正负数列表
        BigDecimal[] nums_03 = {new BigDecimal("123"), new BigDecimal("234"),
                new BigDecimal("-345"), new BigDecimal("1.234"), new BigDecimal("-234.45")};
        List<BigDecimal> numList_03 = Arrays.asList(nums_03);
        Assert.assertEquals(new BigDecimal("-221.216"), MathUtil.sum(numList_03));

        // boundary：0，极限值，0.00000000001
        // 全 0列表
        BigDecimal[] nums_04 = {new BigDecimal("0"), new BigDecimal("0.00000000000"), new BigDecimal("-0.00")};
        List<BigDecimal> numList_04 = Arrays.asList(nums_04);
        Assert.assertEquals(new BigDecimal("0.00000000000"), MathUtil.sum(numList_04));
        // 极限值列表
        BigDecimal[] nums_05 = {new BigDecimal("99999999999999"), new BigDecimal("99999999.99999"),
                new BigDecimal("-99999998888888")};
        List<BigDecimal> numList_05 = Arrays.asList(nums_05);
        Assert.assertEquals(new BigDecimal("101111110.99999"), MathUtil.sum(numList_05));
        // 0.00000000001
        BigDecimal[] nums_08 = {new BigDecimal("0.00000000001"), new BigDecimal("0.1"),
                new BigDecimal("-0.000000000000000000000001"), new BigDecimal("0.01")};
        List<BigDecimal> numList_08 = Arrays.asList(nums_08);
        Assert.assertEquals(new BigDecimal("0.110000000009999999999999"), MathUtil.sum(numList_08));
        // 0和极限值
        BigDecimal[] nums_06 = {new BigDecimal("0"), new BigDecimal("0.00000000000"),
                new BigDecimal("-0.00"), new BigDecimal("999999999"), new BigDecimal("9999999999"),
                new BigDecimal("-999999999"), new BigDecimal("9999.999999")};
        List<BigDecimal> numList_06 = Arrays.asList(nums_06);
        Assert.assertEquals(new BigDecimal("10000009998.99999900000"), MathUtil.sum(numList_06));
        // 0和 0.00000000001
        BigDecimal[] nums_09 = {new BigDecimal("0.00000000001"), new BigDecimal("0"),
                new BigDecimal("-0.000000000000000000000001"), new BigDecimal("0.0000000000")};
        List<BigDecimal> numList_09 = Arrays.asList(nums_09);
        Assert.assertEquals(new BigDecimal("9.999999999999E-12"), MathUtil.sum(numList_09));
        // 极限值和 0.00000000001
        BigDecimal[] nums_10 = {new BigDecimal("0.00000000001"), new BigDecimal("0"),
                new BigDecimal("-0.000000000000000000000001"), new BigDecimal("0.0000000000")};
        List<BigDecimal> numList_10 = Arrays.asList(nums_10);
        Assert.assertEquals(new BigDecimal("9.999999999999E-12"), MathUtil.sum(numList_10));

        // exception：参数为null，列表含null
        // 列表含null
        BigDecimal[] nums_07 = {new BigDecimal("123"), null, null};
        List<BigDecimal> nums = Arrays.asList(nums_07);
        Assert.assertThrows(CommonException.class, ()->{MathUtil.sum(null);});
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{MathUtil.sum(nums);});

    }

    @Test
    public void testSumWithScale() {
        // normal：正数列表，负数列表，正负数列表
        // 正数列表
        BigDecimal[] nums_01 = {new BigDecimal("123"), new BigDecimal("234"), new BigDecimal("1.234")};
        List<BigDecimal> numList_01 = Arrays.asList(nums_01);
        Assert.assertEquals(new BigDecimal("358.23"), MathUtil.sum(numList_01, 2, RoundingMode.HALF_DOWN));
        // 负数列表
        BigDecimal[] nums_02 = {new BigDecimal("-345"), new BigDecimal("-1.234455"), new BigDecimal("-234.45")};
        List<BigDecimal> numList_02 = Arrays.asList(nums_02);
        Assert.assertEquals(new BigDecimal("-580.685"), MathUtil.sum(numList_02, 3, RoundingMode.UP));
        // 正负数列表
        BigDecimal[] nums_03 = {new BigDecimal("123"), new BigDecimal("234"),
                new BigDecimal("-345"), new BigDecimal("1.234"), new BigDecimal("-234.45")};
        List<BigDecimal> numList_03 = Arrays.asList(nums_03);
        Assert.assertEquals(new BigDecimal("-221.22"), MathUtil.sum(numList_03, 2, RoundingMode.HALF_UP));

        // boundary：0，极限值
        // 全 0列表
        BigDecimal[] nums_04 = {new BigDecimal("0"), new BigDecimal("0.00000000000"), new BigDecimal("-0.00")};
        List<BigDecimal> numList_04 = Arrays.asList(nums_04);
        Assert.assertEquals(new BigDecimal("0.0000"), MathUtil.sum(numList_04, 4, RoundingMode.HALF_DOWN));
        // 0.00000000001
        BigDecimal[] nums_08 = {new BigDecimal("0.00000000001"), new BigDecimal("0.1"),
                new BigDecimal("-0.000000000000000000000001"), new BigDecimal("0.01")};
        List<BigDecimal> numList_08 = Arrays.asList(nums_08);
        Assert.assertEquals(new BigDecimal("0.110"), MathUtil.sum(numList_08, 3, RoundingMode.FLOOR));
        // 极限值列表
        BigDecimal[] nums_05 = {new BigDecimal("99999999999999"), new BigDecimal("99999999.99999"),
                new BigDecimal("-99999998888888")};
        List<BigDecimal> numList_05 = Arrays.asList(nums_05);
        Assert.assertEquals(new BigDecimal("101111111.00"), MathUtil.sum(numList_05, 2, RoundingMode.HALF_UP));
        // 0和极限值
        BigDecimal[] nums_06 = {new BigDecimal("0"), new BigDecimal("0.00000000000"),
                new BigDecimal("-0.00"), new BigDecimal("999999999"), new BigDecimal("9999999999"),
                new BigDecimal("-999999999"), new BigDecimal("9999.999999")};
        List<BigDecimal> numList_06 = Arrays.asList(nums_06);
        Assert.assertEquals(new BigDecimal("10000009999.00"), MathUtil.sum(numList_06, 2, RoundingMode.HALF_UP));
        // 0和 0.00000000001
        BigDecimal[] nums_09 = {new BigDecimal("0.00000000001"), new BigDecimal("0"),
                new BigDecimal("-0.000000000000000000000001"), new BigDecimal("0.0000000000")};
        List<BigDecimal> numList_09 = Arrays.asList(nums_09);
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.sum(numList_09, 2, RoundingMode.DOWN));
        // 极限值和 0.00000000001
        BigDecimal[] nums_10 = {new BigDecimal("0.00000000001"), new BigDecimal("0"),
                new BigDecimal("-0.000000000000000000000001"), new BigDecimal("0.0000000000")};
        List<BigDecimal> numList_10 = Arrays.asList(nums_10);
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.sum(numList_10, 2, RoundingMode.HALF_DOWN));

        // exception：参数为null，列表含null
        // 列表含null
        BigDecimal[] nums_07 = {new BigDecimal("123"), null, null};
        List<BigDecimal> nums = Arrays.asList(nums_07);
        Assert.assertThrows(CommonException.class, ()->{MathUtil.sum(null, 2, RoundingMode.HALF_UP);});
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{MathUtil.sum(nums, 3, RoundingMode.UP);});

    }

    @Test
    public void testSubtractWithNoScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("0"), MathUtil.subtract(new BigDecimal("2"), new BigDecimal("2")));
        Assert.assertEquals(new BigDecimal("998"), MathUtil.subtract(new BigDecimal("1000"), new BigDecimal("2")));
        Assert.assertEquals(new BigDecimal("-0.231"), MathUtil.subtract(new BigDecimal("2"), new BigDecimal("2.231")));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.subtract(new BigDecimal("2.31"), new BigDecimal("2.31")));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("-4.62"), MathUtil.subtract(new BigDecimal("-2.31"), new BigDecimal("2.31")));
        Assert.assertEquals(new BigDecimal("-4.611"), MathUtil.subtract(new BigDecimal("-2.31"), new BigDecimal("2.301")));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.subtract(new BigDecimal("-2.31"), new BigDecimal("-2.31")));
        Assert.assertEquals(new BigDecimal("2298.69"), MathUtil.subtract(new BigDecimal("-2.31"), new BigDecimal("-2301")));

        // boundary：0，0.000000000000001，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0"), MathUtil.subtract(new BigDecimal("0"), new BigDecimal("0")));
        Assert.assertEquals(new BigDecimal("0.0"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("0")));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("0.000")));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("-0.000")));
        // 0和 0.000000000000001
        Assert.assertEquals(new BigDecimal("-0.000000000000001"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("0.000000000000001")));
        Assert.assertEquals(new BigDecimal("0.000000000000001"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("-0.000000000000001")));
        // 0和极限值
        Assert.assertEquals(new BigDecimal("9999999999"), MathUtil.subtract(new BigDecimal("9999999999"), new BigDecimal("0")));
        Assert.assertEquals(new BigDecimal("-9999999999.0"), MathUtil.subtract(new BigDecimal("-9999999999"), new BigDecimal("0.0")));
        // 0.000000000000001和极限值
        Assert.assertEquals(new BigDecimal("-9999999999.999999900000001"), MathUtil.subtract(new BigDecimal("-9999999999.9999999"), new BigDecimal("0.000000000000001")));
        Assert.assertEquals(new BigDecimal("-9999999999.999999899999999"), MathUtil.subtract(new BigDecimal("-9999999999.9999999"), new BigDecimal("-0.000000000000001")));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("0"), MathUtil.subtract(new BigDecimal("9999999999"), new BigDecimal("9999999999")));
        Assert.assertEquals(new BigDecimal("0"), MathUtil.subtract(new BigDecimal("-9999999999"), new BigDecimal("-9999999999")));
        Assert.assertEquals(new BigDecimal("0E-7"), MathUtil.subtract(new BigDecimal("-9999999999.9999999"), new BigDecimal("-9999999999.9999999")));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{MathUtil.subtract(null, BigDecimal.ZERO);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.subtract(BigDecimal.ZERO, null);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.subtract(null, null);});

    }

    @Test
    public void testSubtractWithScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.subtract(new BigDecimal("2"), new BigDecimal("2"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("998.000"), MathUtil.subtract(new BigDecimal("1000"), new BigDecimal("2"), 3, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("-0.23"), MathUtil.subtract(new BigDecimal("2"), new BigDecimal("2.231"), 2, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("0.0000"), MathUtil.subtract(new BigDecimal("2.31"), new BigDecimal("2.31"), 4, RoundingMode.DOWN));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("-4.620"), MathUtil.subtract(new BigDecimal("-2.31"), new BigDecimal("2.31"), 3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-4.6110"), MathUtil.subtract(new BigDecimal("-2.31"), new BigDecimal("2.301"), 4, RoundingMode.UP));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.subtract(new BigDecimal("-2.31"), new BigDecimal("-2.31"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("2298.69"), MathUtil.subtract(new BigDecimal("-2.31"), new BigDecimal("-2301"), 2, RoundingMode.HALF_UP));

        // boundary：0，0.000000000000001，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.subtract(new BigDecimal("0"), new BigDecimal("0"), 3, RoundingMode.DOWN));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("0"), 2, RoundingMode.DOWN));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("0.000"),2, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("-0.000"), 2, RoundingMode.HALF_UP));
        // 0和 0.000000000000001
        Assert.assertEquals(new BigDecimal("-1E+2"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("0.000000000000001"), -2, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.subtract(new BigDecimal("0.0"), new BigDecimal("-0.000000000000001"), 2, RoundingMode.HALF_UP));
        // 0和极限值
        Assert.assertEquals(new BigDecimal("9999999999.00"), MathUtil.subtract(new BigDecimal("9999999999"), new BigDecimal("0"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-9.999999E+9"), MathUtil.subtract(new BigDecimal("-9999999999"), new BigDecimal("0.0"), -3, RoundingMode.DOWN));
        // 0.000000000000001和极限值
        Assert.assertEquals(new BigDecimal("-10000000000.000"), MathUtil.subtract(new BigDecimal("-9999999999.9999999"), new BigDecimal("0.000000000000001"), 3, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("-10000000000.00"), MathUtil.subtract(new BigDecimal("-9999999999.9999999"), new BigDecimal("-0.000000000000001"), 2, RoundingMode.UP));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("0E+4"), MathUtil.subtract(new BigDecimal("9999999999"), new BigDecimal("9999999999"), -4, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.subtract(new BigDecimal("-9999999999"), new BigDecimal("-9999999999"), 3, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("0.0000"), MathUtil.subtract(new BigDecimal("-9999999999.9999999"), new BigDecimal("-9999999999.9999999"), 4, RoundingMode.UP));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{MathUtil.subtract(null, BigDecimal.ZERO, 3, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.subtract(BigDecimal.ZERO, null, 3, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.subtract(null, null, 2, RoundingMode.UP);});
    }

    @Test
    public void testMultiplyWithNoScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("4"), MathUtil.multiply(new BigDecimal("2"), new BigDecimal("2")));
        Assert.assertEquals(new BigDecimal("2000"), MathUtil.multiply(new BigDecimal("1000"), new BigDecimal("2")));
        Assert.assertEquals(new BigDecimal("4.462"), MathUtil.multiply(new BigDecimal("2"), new BigDecimal("2.231")));
        Assert.assertEquals(new BigDecimal("5.3361"), MathUtil.multiply(new BigDecimal("2.31"), new BigDecimal("2.31")));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("-5.3361"), MathUtil.multiply(new BigDecimal("-2.31"), new BigDecimal("2.31")));
        Assert.assertEquals(new BigDecimal("-5.31531"), MathUtil.multiply(new BigDecimal("-2.31"), new BigDecimal("2.301")));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("5.3361"), MathUtil.multiply(new BigDecimal("-2.31"), new BigDecimal("-2.31")));
        Assert.assertEquals(new BigDecimal("5315.31"), MathUtil.multiply(new BigDecimal("-2.31"), new BigDecimal("-2301")));

        // boundary：0，0.000000000000001，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("0"), new BigDecimal("0")));
        Assert.assertEquals(new BigDecimal("0.0"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("0")));
        Assert.assertEquals(new BigDecimal("0.0000"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("0.000")));
        Assert.assertEquals(new BigDecimal("0.0000"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("-0.000")));
        // 0和 0.000000000000001
        Assert.assertEquals(new BigDecimal("0E-16"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("0.000000000000001")));
        Assert.assertEquals(new BigDecimal("0E-16"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("-0.000000000000001")));
        // 0和极限值
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("9999999999"), new BigDecimal("0")));
        Assert.assertEquals(new BigDecimal("0.0"), MathUtil.multiply(new BigDecimal("-9999999999"), new BigDecimal("0.0")));
        // 0.000000000000001和极限值
        Assert.assertEquals(new BigDecimal("-0.0000099999999999999999"), MathUtil.multiply(new BigDecimal("-9999999999.9999999"), new BigDecimal("0.000000000000001")));
        Assert.assertEquals(new BigDecimal("0.0000099999999999999999"), MathUtil.multiply(new BigDecimal("-9999999999.9999999"), new BigDecimal("-0.000000000000001")));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("99999999980000000001"), MathUtil.multiply(new BigDecimal("9999999999"), new BigDecimal("9999999999")));
        Assert.assertEquals(new BigDecimal("99999999980000000001"), MathUtil.multiply(new BigDecimal("-9999999999"), new BigDecimal("-9999999999")));
        Assert.assertEquals(new BigDecimal("99999999999999998000.00000000000001"), MathUtil.multiply(new BigDecimal("-9999999999.9999999"), new BigDecimal("-9999999999.9999999")));

        // exception：参数为null
        BigDecimal num = null;
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, BigDecimal.ZERO);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(BigDecimal.ZERO, num);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(num, num);});

    }

    @Test
    public void testMultiplyWithScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("4.00"), MathUtil.multiply(new BigDecimal("2"), new BigDecimal("2"), 2, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("2000.00"), MathUtil.multiply(new BigDecimal("1000"), new BigDecimal("2"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("4.462"), MathUtil.multiply(new BigDecimal("2"), new BigDecimal("2.231"), 3, RoundingMode.DOWN));
        Assert.assertEquals(new BigDecimal("5.33"), MathUtil.multiply(new BigDecimal("2.31"), new BigDecimal("2.31"), 2, RoundingMode.FLOOR));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("-5.336"), MathUtil.multiply(new BigDecimal("-2.31"), new BigDecimal("2.31"), 3, RoundingMode.DOWN));
        Assert.assertEquals(new BigDecimal("-5.32"), MathUtil.multiply(new BigDecimal("-2.31"), new BigDecimal("2.301"), 2, RoundingMode.HALF_UP));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("5.34"), MathUtil.multiply(new BigDecimal("-2.31"), new BigDecimal("-2.31"), 2, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("5315.31000"), MathUtil.multiply(new BigDecimal("-2.31"), new BigDecimal("-2301"), 5, RoundingMode.UP));

        // boundary：0，0.000000000000001，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("0"), new BigDecimal("0"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("0"), 2, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("0.000"), 2, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("-0.000"), 3, RoundingMode.DOWN));
        // 0和 0.000000000000001
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("0.000000000000001"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("0.0"), new BigDecimal("-0.000000000000001"), 2, RoundingMode.HALF_UP));
        // 0和极限值
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.multiply(new BigDecimal("9999999999"), new BigDecimal("0"), 3, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.multiply(new BigDecimal("-9999999999"), new BigDecimal("0.0"), 3, RoundingMode.HALF_UP));
        // 0.000000000000001和极限值
        Assert.assertEquals(new BigDecimal("-0.001"), MathUtil.multiply(new BigDecimal("-9999999999.9999999"), new BigDecimal("0.000000000000001"), 3, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("-9999999999.9999999"), new BigDecimal("-0.000000000000001"), 2, RoundingMode.FLOOR));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("99999999980000000001.0000"), MathUtil.multiply(new BigDecimal("9999999999"), new BigDecimal("9999999999"), 4, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("99999999980000000001.00"), MathUtil.multiply(new BigDecimal("-9999999999"), new BigDecimal("-9999999999"), 2, RoundingMode.HALF_EVEN));
        Assert.assertEquals(new BigDecimal("99999999999999998000.0000"), MathUtil.multiply(new BigDecimal("-9999999999.9999999"), new BigDecimal("-9999999999.9999999"), 4, RoundingMode.HALF_UP));

        // exception：参数为null
        BigDecimal num = null;
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, BigDecimal.ZERO, 2, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(BigDecimal.ZERO, num, 4, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(num, num, 3, RoundingMode.UP);});

    }

    @Test
    public void testBigDecimalMultiplyLongWithNoScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("4"), MathUtil.multiply(new BigDecimal("2"), new Long("2")));
        Assert.assertEquals(new BigDecimal("2000"), MathUtil.multiply(new BigDecimal("100"), new Long("20")));
        Assert.assertEquals(new BigDecimal("4.62"), MathUtil.multiply(new BigDecimal("2.31"), new Long("2")));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("-92.40"), MathUtil.multiply(new BigDecimal("-2.31"), new Long("40")));
        Assert.assertEquals(new BigDecimal("-92.40"), MathUtil.multiply(new BigDecimal("2.31"), new Long("-40")));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("9.24"), MathUtil.multiply(new BigDecimal("-2.31"), new Long("-4")));
        Assert.assertEquals(new BigDecimal("924"), MathUtil.multiply(new BigDecimal("-231"), new Long("-4")));

        // boundary：0，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("0"), new Long("0")));
        Assert.assertEquals(new BigDecimal("0.0"), MathUtil.multiply(new BigDecimal("0.0"), new Long("0")));
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("-0"), new Long("-0")));
        Assert.assertEquals(new BigDecimal("0E-9"), MathUtil.multiply(new BigDecimal("0.000000000"), new Long("0")));
        Assert.assertEquals(new BigDecimal("0.000000000000020"), MathUtil.multiply(new BigDecimal("0.000000000000001"), new Long("20")));
        Assert.assertEquals(new BigDecimal("0.000000000000020"), MathUtil.multiply(new BigDecimal("-0.000000000000001"), new Long("-20")));
        // 0和 极限值
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("9999999999"), new Long("0")));
        Assert.assertEquals(new BigDecimal("0E-9"), MathUtil.multiply(new BigDecimal("-9999999999.999999999"), new Long("0")));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("99999999980000000001"), MathUtil.multiply(new BigDecimal("9999999999"), new Long("9999999999")));
        Assert.assertEquals(new BigDecimal("-99999999980000000001"), MathUtil.multiply(new BigDecimal("-9999999999"), new Long("9999999999")));
        Assert.assertEquals(new BigDecimal("-99999989999999999.9999999990000001"), MathUtil.multiply(new BigDecimal("-9999999999.9999999999999999"), new Long("9999999")));

        // exception：参数为null
        Long num = null;
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, Long.MIN_VALUE);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(BigDecimal.ZERO, num);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, num);});

    }

    @Test
    public void testBigDecimalMultiplyLongWithScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("4.000"), MathUtil.multiply(new BigDecimal("2"), new Long("2"), 3, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("2000.000"), MathUtil.multiply(new BigDecimal("100"), new Long("20"), 3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("4.620"), MathUtil.multiply(new BigDecimal("2.31"), new Long("2"), 3, RoundingMode.FLOOR));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("-1E+3"), MathUtil.multiply(new BigDecimal("-2.31"), new Long("40"), -3, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("-92.40"), MathUtil.multiply(new BigDecimal("2.31"), new Long("-40"), 2, RoundingMode.FLOOR));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("9.2400"), MathUtil.multiply(new BigDecimal("-2.31"), new Long("-4"), 4, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("9E+2"), MathUtil.multiply(new BigDecimal("-231"), new Long("-4"), -2, RoundingMode.HALF_DOWN));

        // boundary：0，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("0"), new Long("0"), 0, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("0.0"), new Long("0"), 0, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.multiply(new BigDecimal("-0"), new Long("-0"), 3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.0"), MathUtil.multiply(new BigDecimal("0.000000000"), new Long("0"), 1, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.multiply(new BigDecimal("0.000000000000001"), new Long("20"), 3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.0001"), MathUtil.multiply(new BigDecimal("-0.000000000000001"), new Long("-20"), 4, RoundingMode.UP));
        // 0和 极限值
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.multiply(new BigDecimal("9999999999"), new Long("0"), 3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.0"), MathUtil.multiply(new BigDecimal("-9999999999.999999999"), new Long("0"), 1, RoundingMode.UP));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("99999999980000000001.0000"), MathUtil.multiply(new BigDecimal("9999999999"), new Long("9999999999"), 4, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-99999999980000000001.00"), MathUtil.multiply(new BigDecimal("-9999999999"), new Long("9999999999"), 2, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("-99999990000000000.000"), MathUtil.multiply(new BigDecimal("-9999999999.9999999999999999"), new Long("9999999"), 3, RoundingMode.UP));

        // exception：参数为null
        Long num = null;
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, Long.MIN_VALUE, 3, RoundingMode.FLOOR);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(BigDecimal.ZERO, num, 2, RoundingMode.UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, num, 4, RoundingMode.FLOOR);});

    }

    @Test
    public void testBigDecimalMultiplyIntegerWithNoScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("4"), MathUtil.multiply(new BigDecimal("2"), new Integer("2")));
        Assert.assertEquals(new BigDecimal("2000"), MathUtil.multiply(new BigDecimal("100"), new Integer("20")));
        Assert.assertEquals(new BigDecimal("4.62"), MathUtil.multiply(new BigDecimal("2.31"), new Integer("2")));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("-92.40"), MathUtil.multiply(new BigDecimal("-2.31"), new Integer("40")));
        Assert.assertEquals(new BigDecimal("92.40"), MathUtil.multiply(new BigDecimal("2.31"), new Integer("40")));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("9.24"), MathUtil.multiply(new BigDecimal("-2.31"), new Integer("-4")));
        Assert.assertEquals(new BigDecimal("924"), MathUtil.multiply(new BigDecimal("-231"), new Integer("-4")));

        // boundary：0，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("0"), new Integer("0")));
        Assert.assertEquals(new BigDecimal("0.0"), MathUtil.multiply(new BigDecimal("0.0"), new Integer("0")));
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("-0"), new Integer("-0")));
        Assert.assertEquals(new BigDecimal("0E-9"), MathUtil.multiply(new BigDecimal("0.000000000"), new Integer("0")));
        Assert.assertEquals(new BigDecimal("0.000000000000020"), MathUtil.multiply(new BigDecimal("0.000000000000001"), new Integer("20")));
        Assert.assertEquals(new BigDecimal("0.000000000000020"), MathUtil.multiply(new BigDecimal("-0.000000000000001"), new Integer("-20")));
        // 0和 极限值
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("9999999999"), new Integer("0")));
        Assert.assertEquals(new BigDecimal("0E-8"), MathUtil.multiply(new BigDecimal("9999999999.99999999"), new Integer("0")));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("99999989990000001"), MathUtil.multiply(new BigDecimal("9999999999"), new Integer("9999999")));
        Assert.assertEquals(new BigDecimal("-99999989990000001"), MathUtil.multiply(new BigDecimal("-9999999999"), new Integer("9999999")));
        Assert.assertEquals(new BigDecimal("-99999989999999999.9999999990000001"), MathUtil.multiply(new BigDecimal("-9999999999.9999999999999999"), new Integer("9999999")));
        Assert.assertEquals(new BigDecimal("21474836479999999999.9999997852516352"), MathUtil.multiply(new BigDecimal("-9999999999.9999999999999999"), Integer.MIN_VALUE));
        Assert.assertEquals(new BigDecimal("-21474836469999999999.9999997852516353"), MathUtil.multiply(new BigDecimal("-9999999999.9999999999999999"), Integer.MAX_VALUE));

        // exception：参数为null
        Integer num = null;
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, Integer.MIN_VALUE);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(BigDecimal.ZERO, num);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, num);});

    }

    @Test
    public void testBigDecimalMultiplyIntegerWithScale() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("4.00"), MathUtil.multiply(new BigDecimal("2"), new Integer("2"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("2000.00"), MathUtil.multiply(new BigDecimal("100"), new Integer("20"), 2, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("4.6"), MathUtil.multiply(new BigDecimal("2.31"), new Integer("2"), 1, RoundingMode.DOWN));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("-92"), MathUtil.multiply(new BigDecimal("-2.31"), new Integer("40"), 0, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("-92"), MathUtil.multiply(new BigDecimal("2.31"), new Integer("-40"), 0, RoundingMode.HALF_DOWN));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("9.24"), MathUtil.multiply(new BigDecimal("-2.31"), new Integer("-4"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("924.00"), MathUtil.multiply(new BigDecimal("-231"), new Integer("-4"), 2, RoundingMode.HALF_EVEN));

        // boundary：0，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("0"), new Integer("0"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0"), MathUtil.multiply(new BigDecimal("0.0"), new Integer("0"), 0, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0E+1"), MathUtil.multiply(new BigDecimal("-0"), new Integer("-0"), -1, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("0.000000000"), new Integer("0"), 2, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("0.000000000000001"), new Integer("20"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.000000000000020"), MathUtil.multiply(new BigDecimal("-0.000000000000001"), new Integer("-20"), 15, RoundingMode.HALF_EVEN));
        // 0和极限值
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.multiply(new BigDecimal("0.00000000000000"), new Integer("999999"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.0"), MathUtil.multiply(new BigDecimal("0"), new Integer("8888888"), 1, RoundingMode.DOWN));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("9999989999000001.00"), MathUtil.multiply(new BigDecimal("9999999999"), new Integer("999999"), 2, RoundingMode.HALF_DOWN));
        Assert.assertEquals(new BigDecimal("-99999989990000001"), MathUtil.multiply(new BigDecimal("-9999999999"), new Integer("9999999"), 0, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-999990000000000.00000000"), MathUtil.multiply(new BigDecimal("-9999999999.9999999999999999"), new Integer("99999"), 8, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("21474836479999999999.99999979"), MathUtil.multiply(new BigDecimal("-9999999999.9999999999999999"), Integer.MIN_VALUE, 8, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-21474836470000000000.00"), MathUtil.multiply(new BigDecimal("-9999999999.9999999999999999"), Integer.MAX_VALUE, 2, RoundingMode.HALF_UP));

        // exception：参数为null
        Integer num = null;
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, Integer.MIN_VALUE, 2, RoundingMode.DOWN);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(BigDecimal.ZERO, num, 2, RoundingMode.UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.multiply(null, num, 2, RoundingMode.HALF_UP);});

    }

    @Test
    public void testDivide() {
        // normal：正数、负数
        // 正数和正数
        Assert.assertEquals(new BigDecimal("0.53"), MathUtil.divide(new BigDecimal("123"), new BigDecimal("234"), 2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.005"), MathUtil.divide(new BigDecimal("1.23"), new BigDecimal("234"), 3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("5.25641"), MathUtil.divide(new BigDecimal("123"), new BigDecimal("23.4"), 5, RoundingMode.DOWN));
        Assert.assertEquals(new BigDecimal("0.5256"), MathUtil.divide(new BigDecimal("0.123"), new BigDecimal("0.234"), 4, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("5.3"), MathUtil.divide(new BigDecimal("1234"), new BigDecimal("234"), 1, RoundingMode.HALF_EVEN));
        Assert.assertEquals(new BigDecimal("1.00"), MathUtil.divide(new BigDecimal("1234"), new BigDecimal("1234"), 2, RoundingMode.HALF_DOWN));
        // 正数和负数
        Assert.assertEquals(new BigDecimal("-0.5256"), MathUtil.divide(new BigDecimal("0.123"), new BigDecimal("-0.234"), 4, RoundingMode.HALF_EVEN));
        Assert.assertEquals(new BigDecimal("-5.3"), MathUtil.divide(new BigDecimal("-1234"), new BigDecimal("234"), 1, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-1.00"), MathUtil.divide(new BigDecimal("1234"), new BigDecimal("-1234"), 2, RoundingMode.HALF_UP));
        // 负数和负数
        Assert.assertEquals(new BigDecimal("0.5257"), MathUtil.divide(new BigDecimal("-0.123"), new BigDecimal("-0.234"), 4, RoundingMode.UP));
        Assert.assertEquals(new BigDecimal("5.3"), MathUtil.divide(new BigDecimal("-1234"), new BigDecimal("-234"), 1, RoundingMode.HALF_EVEN));
        Assert.assertEquals(new BigDecimal("1.00"), MathUtil.divide(new BigDecimal("-1234"), new BigDecimal("-1234"), 2, RoundingMode.DOWN));

        // boundary：0，极限值
        // 0和 0
        Assert.assertEquals(new BigDecimal("0.00"), MathUtil.divide(new BigDecimal("0"), new BigDecimal("0.01"),2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.000000"), MathUtil.divide(new BigDecimal("0"), new BigDecimal("0.00000000001"),6, RoundingMode.FLOOR));
        Assert.assertEquals(new BigDecimal("0E+6"), MathUtil.divide(new BigDecimal("0.00000000001"), new BigDecimal("0.00000000001"),-6, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-1.00"), MathUtil.divide(new BigDecimal("0.00000000001"), new BigDecimal("-0.00000000001"),2, RoundingMode.HALF_EVEN));
        Assert.assertEquals(new BigDecimal("1.00"), MathUtil.divide(new BigDecimal("-0.00000000001"), new BigDecimal("-0.00000000001"),2, RoundingMode.HALF_EVEN));
        // 0和极限值
        Assert.assertEquals(new BigDecimal("0E+2"), MathUtil.divide(new BigDecimal("0"), new BigDecimal("777777777777"),-2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("0.000"), MathUtil.divide(new BigDecimal("0.000000000000000000"), new BigDecimal("888888888888"),3, RoundingMode.DOWN));
        // 极限值和极限值
        Assert.assertEquals(new BigDecimal("-999999999999900000000000.00"), MathUtil.divide(new BigDecimal("9999999999999"), new BigDecimal("-0.00000000001"),2, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("11.250"), MathUtil.divide(new BigDecimal("9999999999999"), new BigDecimal("888888888888"),3, RoundingMode.HALF_UP));
        Assert.assertEquals(new BigDecimal("-11.250"), MathUtil.divide(new BigDecimal("9999999999999"), new BigDecimal("-888888888888"),3, RoundingMode.HALF_UP));

        // exception：参数为null、被除数为 0
        // 参数为null
        Assert.assertThrows(CommonException.class, ()->{MathUtil.divide(null, new BigDecimal("0"), 2, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.divide(null, null, 2, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.divide(new BigDecimal("0"), null, 2, RoundingMode.HALF_UP);});
        // 被除数为 0
        Assert.assertThrows(CommonException.class, ()->{MathUtil.divide(new BigDecimal("1.33"), new BigDecimal("0"), 2, RoundingMode.HALF_UP);});
        Assert.assertThrows(CommonException.class, ()->{MathUtil.divide(new BigDecimal("0"), new BigDecimal("0.000000"), 2, RoundingMode.HALF_UP);});

    }

    @Test
    public void testMax() {
        // normal：正数列表、负数列表、正负数列表
        // 正数列表
        BigDecimal[] nums_01 = {new BigDecimal("4"), new BigDecimal("3"), new BigDecimal("1000"), new BigDecimal("0.234"),
                new BigDecimal("3.14159"), new BigDecimal("1000")};
        Assert.assertEquals(new BigDecimal("1000"), MathUtil.max(Arrays.asList(nums_01)));
        // 负数列表
        BigDecimal[] nums_02 = {new BigDecimal("-4"), new BigDecimal("-3"), new BigDecimal("-1000"), new BigDecimal("-0.234"),
                new BigDecimal("-3.14159")};
        Assert.assertEquals(new BigDecimal("-0.234"), MathUtil.max(Arrays.asList(nums_02)));
        // 正负数列表
        BigDecimal[] nums_03 = {new BigDecimal("4"), new BigDecimal("3"), new BigDecimal("1000"), new BigDecimal("0.234"),
                new BigDecimal("3.14159"), new BigDecimal("-400"), new BigDecimal("-3.14159")};
        Assert.assertEquals(new BigDecimal("1000"), MathUtil.max(Arrays.asList(nums_03)));

        // boundary：0，极限值，含null的列表
        // 只含 0列表
        BigDecimal[] nums_04 = {new BigDecimal("0"), new BigDecimal("0.000"), new BigDecimal("0.00000000000"),
                new BigDecimal("-0.0000")};
        Assert.assertEquals(new BigDecimal("0"), MathUtil.max(Arrays.asList(nums_04)));
        // 极限值列表
        BigDecimal[] nums_05 = {new BigDecimal("999999999"), new BigDecimal("-99999999999"), new BigDecimal(99999999)};
        Assert.assertEquals(new BigDecimal("999999999"), MathUtil.max(Arrays.asList(nums_05)));
        // 含null列表
        BigDecimal[] nums_06 = {null, new BigDecimal("0.000"), new BigDecimal("0.00000000000"),
                new BigDecimal("-0.0000"), new BigDecimal("999999999"), new BigDecimal("-99999999999"), null};
        Assert.assertEquals(new BigDecimal("999999999"), MathUtil.max(Arrays.asList(nums_06)));
        BigDecimal[] nums_07 = {new BigDecimal("0"), new BigDecimal("0.000"), new BigDecimal("0.00000000000"),
                new BigDecimal("-0.0000"), new BigDecimal("999999999"), new BigDecimal("-99999999999"), null};
        Assert.assertEquals(new BigDecimal("999999999"), MathUtil.max(Arrays.asList(nums_07)));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{MathUtil.max(null);});

    }

    @Test
    public void testMin() {
        // normal：正数列表、负数列表、正负数列表
        // 正数列表
        BigDecimal[] nums_01 = {new BigDecimal("4"), new BigDecimal("3"), new BigDecimal("1000"), new BigDecimal("0.234"),
                new BigDecimal("3.14159")};
        Assert.assertEquals(new BigDecimal("0.234"), MathUtil.min(Arrays.asList(nums_01)));
        // 负数列表
        BigDecimal[] nums_02 = {new BigDecimal("-4"), new BigDecimal("-3"), new BigDecimal("-1000"), new BigDecimal("-0.234"),
                new BigDecimal("-3.14159")};
        Assert.assertEquals(new BigDecimal("-1000"), MathUtil.min(Arrays.asList(nums_02)));
        // 正负数列表
        BigDecimal[] nums_03 = {new BigDecimal("4"), new BigDecimal("3"), new BigDecimal("1000"), new BigDecimal("0.234"),
                new BigDecimal("3.14159"), new BigDecimal("-400"), new BigDecimal("-3.14159")};
        Assert.assertEquals(new BigDecimal("-400"), MathUtil.min(Arrays.asList(nums_03)));

        // boundary：0，极限值，含null的列表
        // 只含 0列表
        BigDecimal[] nums_04 = {new BigDecimal("0"), new BigDecimal("0.000"), new BigDecimal("0.00000000000"),
                new BigDecimal("-0.0000")};
        Assert.assertEquals(new BigDecimal("0"), MathUtil.min(Arrays.asList(nums_04)));
        // 极限值列表
        BigDecimal[] nums_05 = {new BigDecimal("999999999"), new BigDecimal("-99999999999"), new BigDecimal(99999999)};
        Assert.assertEquals(new BigDecimal("-99999999999"), MathUtil.min(Arrays.asList(nums_05)));
        // 含null列表
        BigDecimal[] nums_06 = {null, new BigDecimal("0.000"), new BigDecimal("0.00000000000"),
                new BigDecimal("-0.0000"), new BigDecimal("999999999"), new BigDecimal("-99999999999"), null};
        Assert.assertEquals(new BigDecimal("-99999999999"), MathUtil.min(Arrays.asList(nums_06)));
        BigDecimal[] nums_07 = {new BigDecimal("0"), new BigDecimal("0.000"), new BigDecimal("0.00000000000"),
                new BigDecimal("-0.0000"), new BigDecimal("999999999"), new BigDecimal("-99999999999"), null};
        Assert.assertEquals(new BigDecimal("-99999999999"), MathUtil.min(Arrays.asList(nums_07)));

        // exception：参数为null
        Assert.assertThrows(CommonException.class, ()->{MathUtil.min(null);});
    }
}