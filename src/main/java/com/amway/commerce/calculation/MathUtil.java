package com.amway.commerce.calculation;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * @author: Jason.Hu
 * @date: 2023-08-08
 */
public class MathUtil {

    /**
     * 两数相加，其值的保留位数等于 num1和 num2两数中的最大位数。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 BigDecimal类型，不可为空
     * @return num1 + num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=4，抛出“参数不能为空”异常提示信息；<p>
     * num1=3.14159，num2=3.14，返回 6.28159；<p>
     * num1=3.14，num2=3.14159，返回 6.28159。
     */
    public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
        isNotNull(num1, num2);
        return num1.add(num2);
    }

    /**
     * 两数相加，需要对计算结果设置小数点后的保留位数 scale以及舍入模式 RoundingMode。
     * 该方法需要对参数进行非空判断，若 num1或 num2或 scale或 roundingMode为空，则抛出参数不能为空异常。
     *
     * @param num1         BigDecimal类型，不可为空
     * @param num2         BigDecimal类型，不可为空
     * @param scale        小数点后的保留位数，不可为空
     * @param roundingMode 舍入模式，RoundingMode类型，不可为空
     * @return num1 + num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=4，scale=2，roundingMode=RoundingMode.HALF_UP，抛出“参数不能为空”异常提示信息；<p>
     * num1=3.14159，num2=3.14，scale=2，roundingMode=RoundingMode.HALF_DOWN，返回 6.28。
     */
    public static BigDecimal add(BigDecimal num1, BigDecimal num2, Integer scale, RoundingMode roundingMode) {
        isNotNull(scale, roundingMode);
        return add(num1, num2).setScale(scale, roundingMode);
    }

    /**
     * 对 BigDecimal集合求和，其值的保留位数等于集合中的最大位数。
     * 该方法需要对参数进行非空判断，若 numList为空或存在 null，则抛出参数不能为空异常。
     *
     * @param numList List集合，不可为空且不可存在 null
     * @return 求和结果，BigDecimal类型
     *
     * <p>
     * <b>例：</b><br>
     * numList=null，抛出“参数不能为空”异常提示信息；<p>
     * numList={1,2,null}，抛出“集合中存在空值”异常提示信息；<p>
     * numList={1,2,3.14159}，返回 6.14159。
     */
    public static BigDecimal sum(List<BigDecimal> numList) {
        // 参数不能为空
        isNotNull(numList);
        // 集合中存在 null
        if (numList.contains(null)) {
            throw new CommonException(CommonError.ListContainNullValue.getMessage());
        }
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal num : numList) {
            result = result.add(num);
        }
        return result;
    }

    /**
     * 对 BigDecimal集合求和，需要对最后结果设置小数点后的保留位数 scale以及舍入模式 RoundingMode。
     * 该方法需要对参数进行非空判断，若 numList为空或存在 null，则抛出参数不能为空异常。
     *
     * @param numList      List集合，不可为空且不可存在 null
     * @param scale        小数点后的保留位数，不可为空
     * @param roundingMode 舍入模式，RoundingMode类型，不可为空
     * @return 求和结果，BigDecimal类型
     *
     * <p>
     * <b>例：</b><br>
     * scale=2，roundingMode=RoundingMode.HALF_UP，numList=null，抛出“参数不能为空”异常提示信息；<p>
     * scale=2，roundingMode=RoundingMode.HALF_UP，numList={1,2,3,null}，抛出“参数不能为空”异常提示信息；<p>
     * scale=2，roundingMode=RoundingMode.HALF_UP，numList={1,2,3.14159}，返回 6.14。
     */
    public static BigDecimal sum(List<BigDecimal> numList, Integer scale, RoundingMode roundingMode) {
        return sum(numList).setScale(scale, roundingMode);
    }

    /**
     * 两数相减，其值的保留位数等于 num1和 num2两数中的最大位数。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 BigDecimal类型，不可为空
     * @return num1 - num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=4，抛出“参数不能为空”异常提示信息；<p>
     * num1=3.14159，num2=3.14，返回 0.00159。
     */
    public static BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
        isNotNull(num1, num2);
        return num1.subtract(num2);
    }

    /**
     * 两数相减，需要对计算结果设置小数点后的保留位数 scale以及舍入模式 RoundingMode。
     * 该方法需要对参数进行非空判断，若 num1或 num2或 scale或 roundingMode为空，则抛出参数不能为空异常。
     *
     * @param num1         BigDecimal类型，不可为空
     * @param num2         BigDecimal类型，不可为空
     * @param scale        小数点后的保留位数，不可为空
     * @param roundingMode 舍入模式，RoundingMode类型，不可为空
     * @return num1 - num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=4，抛出“参数不能为空”异常提示信息；<p>
     * num1=3.14159，num2=3.14，返回 0.00159。
     */
    public static BigDecimal subtract(BigDecimal num1, BigDecimal num2, Integer scale, RoundingMode roundingMode) {
        isNotNull(scale, roundingMode);
        return subtract(num1, num2).setScale(scale, roundingMode);
    }

    /**
     * 两数相乘，其值的保留位数等于 num1和 num2的位数之和。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 BigDecimal类型，不可为空
     * @return num1 * num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=4，抛出“参数不能为空”异常提示信息；<p>
     * num1=3，num2=3.14，返回 9.14。
     */
    public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
        isNotNull(num1, num2);
        return num1.multiply(num2);
    }

    /**
     * 两数相乘，需要对计算结果设置小数点后的保留位数 scale以及舍入模式 RoundingMode。
     * 该方法需要对参数进行非空判断，若 num1或 num2或 scale或 roundingMode为空，则抛出参数不能为空异常。
     *
     * @param num1         BigDecimal类型，不可为空
     * @param num2         BigDecimal类型，不可为空
     * @param scale        小数点后的保留位数，不可为空
     * @param roundingMode 舍入模式，RoundingMode类型，不可为空
     * @return num1 * num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=4，scale=2，roundingMode=RoundingMode.HALF_UP，抛出“参数不能为空”异常提示信息；<p>
     * num1=3，num2=3.14，scale=2，roundingMode=RoundingMode.HALF_UP，返回 9.14。
     */
    public static BigDecimal multiply(BigDecimal num1, BigDecimal num2, Integer scale, RoundingMode roundingMode) {
        isNotNull(scale, roundingMode);
        return multiply(num1, num2).setScale(scale, roundingMode);
    }

    /**
     * 两数相乘，其值的保留位数等于 num1的位数。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 Long类型，不可为空
     * @return num1 * num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=4，抛出“参数不能为空”异常提示信息；<p>
     * num1=3，num2=3，返回 9；<p>
     * num1=3.14159，num2=3，返回 9.4247778。
     */
    public static BigDecimal multiply(BigDecimal num1, Long num2) {
        isNotNull(num1, num2);
        return multiply(num1, BigDecimal.valueOf(num2));
    }

    /**
     * 两数相乘，需要对计算结果设置小数点后的保留位数 scale以及舍入模式 RoundingMode。
     * 该方法需要对参数进行非空判断，若 num1或 num2或 scale或 roundingMode为空，则抛出参数不能为空异常。
     *
     * @param num1         BigDecimal类型，不可为空
     * @param num2         Long类型，不可为空
     * @param scale        小数点后的保留位数，不可为空
     * @param roundingMode 舍入模式，RoundingMode类型，不可为空
     * @return num1 * num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=2，num2=null，scale=2，roundingMode=RoundingMode.HALF_UP，抛出“参数不能为空”异常提示信息；<p>
     * num1=3，num2=3，scale=2，roundingMode=RoundingMode.HALF_DOWN，返回 9.00；<p>
     * num1=3.14159，num2=3，scale=2，roundingMode=RoundingMode.UP，返回 9.43。
     */
    public static BigDecimal multiply(BigDecimal num1, Long num2, Integer scale, RoundingMode roundingMode) {
        isNotNull(scale, roundingMode);
        return multiply(num1, num2).setScale(scale, roundingMode);
    }

    /**
     * 两数相乘，其值的保留位数等于 num1的位数。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 Long类型，不可为空
     * @return num1 * num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=4，抛出“参数不能为空”异常提示信息；<p>
     * num1=3，num2=3，返回 9；<p>
     * num1=3.14159，num2=3，返回 9.42477。
     */
    public static BigDecimal multiply(BigDecimal num1, Integer num2) {
        isNotNull(num1, num2);
        return num1.multiply(BigDecimal.valueOf(num2));
    }

    /**
     * 两数相乘，需要对计算结果设置小数点后的保留位数 scale以及舍入模式 RoundingMode。
     * 该方法需要对参数进行非空判断，若 num1或 num2或 scale或 roundingMode为空，则抛出参数不能为空异常。
     *
     * @param num1         BigDecimal类型，不可为空
     * @param num2         Integer类型，不可为空
     * @param scale        小数点后的保留位数，不可为空
     * @param roundingMode 舍入模式，RoundingMode类型，不可为空
     * @return num1 * num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=2，num2=null，scale=2，roundingMode=RoundingMode.HALF_UP，抛出“参数不能为空”异常提示信息；<p>
     * num1=3，num2=3，scale=2，roundingMode=RoundingMode.HALF_DOWN，返回 9.00；<p>
     * num1=3.14159，num2=3，scale=2，roundingMode=RoundingMode.UP，返回 9.43。
     */
    public static BigDecimal multiply(BigDecimal num1, Integer num2, Integer scale, RoundingMode roundingMode) {
        return multiply(num1, num2).setScale(scale, roundingMode);
    }

    /**
     * 两数相除，需要对计算结果设置小数点后的保留位数 scale以及舍入模式 RoundingMode。
     * 该方法需要对参数进行非空判断和被除数非 0校验，若 num1或 num2或 scale或 roundingMode为空，则抛出参数不能为空异常；
     * 若被除数为 0，则抛出“被除数为0”异常提示信息。
     *
     * @param num1         BigDecimal类型，不可为空
     * @param num2         BigDecimal类型，不可为空且非 0
     * @param scale        小数点后的保留位数，不可为空
     * @param roundingMode 舍入模式，RoundingMode类型，不可为空
     * @return num1 / num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=2，num2=null，scale=2，roundingMode=RoundingMode.HALF_UP，抛出“参数不能为空”异常提示信息；<p>
     * num1=2，num2=0，scale=2，roundingMode=RoundingMode.HALF_DOWN，抛出“被除数为0”异常提示信息；<p>
     * num1=3，num2=3，scale=2，roundingMode=RoundingMode.UP，返回 1.00；<p>
     * num1=3.14159，num2=2，scale=2，roundingMode=RoundingMode.DOWN，返回 1.57。
     */
    public static BigDecimal divide(BigDecimal num1, BigDecimal num2, Integer scale, RoundingMode roundingMode) {
        isNotNull(num1, num2, scale, roundingMode);
        // 被除数非 0校验
        if (BigDecimalUtil.isEqual(BigDecimal.ZERO, num2)) {
            throw new CommonException(CommonError.DividedByZero.getMessage());
        }
        return num1.divide(num2, scale, roundingMode);
    }

    /**
     * 对 BigDecimal集合求最大值。
     * 该方法需要对参数进行非空判断，若 numList为空，则抛出参数不能为空异常。
     *
     * @param numList List集合，不可全为空但可存在部分空
     * @return 集合最大值
     *
     * <p>
     * <b>例：</b><br>
     * numList=null，抛出“参数不能为空”异常提示信息；<p>
     * numList={1,2,3,4,null}，返回 4。
     */
    public static BigDecimal max(List<BigDecimal> numList) {
        isNotNull(numList);
        return numList.stream().filter(Objects::nonNull).max(BigDecimal::compareTo).get();
    }

    /**
     * 对 BigDecimal集合求最小值。
     * 该方法需要对参数进行非空判断，若 numList为空，则抛出参数不能为空异常。
     *
     * @param numList List集合，List集合，不可全为空但可存在部分空
     * @return 集合最大值
     *
     * <p>
     * <b>例：</b><br>
     * numList=null，抛出“参数不能为空”异常提示信息；<p>
     * numList={1,2,3,4,null}，返回 1。
     */
    public static BigDecimal min(List<BigDecimal> numList) {
        isNotNull(numList);
        return numList.stream().filter(Objects::nonNull).min(BigDecimal::compareTo).get();
    }

    private static void isNotNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new CommonException(CommonError.NotNull.getMessage());
            }
        }
    }

}
