package com.amway.commerce.calculation;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import com.amway.commerce.string.StringUtil;
import org.springframework.lang.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: Jason.Hu
 * @date: 2023-08-07
 */
public class BigDecimalUtil {

    private static final Integer DEFAULT_SCALE = 2;
    private static final RoundingMode DEFAULT_ROUNDINGMODE = RoundingMode.HALF_UP;
    private static final String PARAM_IS_NULL_WARN = CommonError.NotNull.getMessage();


    private static int compareTo(BigDecimal num1, BigDecimal num2) {
        isNotNull(num1, num2);
        return num1.compareTo(num2);
    }

    /**
     * 判断 num1是否大于 num2。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 BigDecimal类型，不可为空
     * @return 布尔值，true表示 num1大于 num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=2，抛出“参数不能为空”异常提示信息；<p>
     * num1=2，num2=4，返回 false；<p>
     * num1=0，num2=-3，返回 true。
     */
    public static boolean isGreaterThan(BigDecimal num1, BigDecimal num2) {
        return compareTo(num1, num2) > 0;
    }

    /**
     * 判断 num1是否大于等于 num2。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 BigDecimal类型，不可为空
     * @return 布尔值，true表示 num1大于等于 num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=3，抛出“参数不能为空”异常提示信息；<p>
     * num1=3，num2=3，返回 true；<p>
     * num1=4，num2=3，返回 true；<p>
     * num1=5，num2=9，返回 false。
     */
    public static boolean isGreaterEqual(BigDecimal num1, BigDecimal num2) {
        return compareTo(num1, num2) >= 0;
    }

    /**
     * 判断 num1是否等于 num2。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 BigDecimal类型，不可为空
     * @return 布尔值，true表示 num1等于 num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=2，抛出“参数不能为空”异常提示信息；<p>
     * num1=1，num2=1，返回 true；<p>
     * num1=2，num2=6，返回 false。
     */
    public static boolean isEqual(BigDecimal num1, BigDecimal num2) {
        return compareTo(num1, num2) == 0;
    }

    /**
     * 判断 num1是否小于 num2。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 BigDecimal类型，不可为空
     * @return 布尔值，true表示 num1小于 num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=3，抛出“参数不能为空”异常提示信息；<p>
     * num1=1，num2=3，返回 true；<p>
     * num1=4，num2=0，返回 false。
     */
    public static boolean isLessThan(BigDecimal num1, BigDecimal num2) {
        return compareTo(num1, num2) < 0;
    }

    /**
     * 判断 num1是否小于等于 num2。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 BigDecimal类型，不可为空
     * @return 布尔值，true表示 num1小于等于 num2
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=2，抛出“参数不能为空”异常提示信息；<p>
     * num1=1，num2=1，返回 true；<p>
     * num1=1，num2=5，返回 true；<p>
     * num1=4，num2=3，返回 false。
     */
    public static boolean isLessEqual(BigDecimal num1, BigDecimal num2) {
        return compareTo(num1, num2) <= 0;
    }

    /**
     * 按某一位比较 num1和 num2的大小，结果不受正负号和小数点的影响。
     * 该方法需要对参数进行非空判断，若 num1或 num2为空，则抛出参数不能为空异常。
     *
     * @param num1 BigDecimal类型，不可为空
     * @param num2 BigDecimal类型，不可为空
     * @param n    位数，不能超过去除正负号和小数点后两个数的最小长度，否则会抛出“数组越界”异常提示
     * @return -1，0，1；-1表示 num2大，0表示一样大，1表示 num1大。
     *
     * <p>
     * <b>例：</b><br>
     * num1=null，num2=2，n=2，抛出“参数不能为空”异常提示信息；<p>
     * num1=123，num2=2，n=2，抛出“数组越界”异常提示信息；<p>
     * num1=123，num2=123，n=2，返回 0（两者一样大）；<p>
     * num1=-123，num=+123，n=2，返回 0（两者一样大）；<p>
     * num1=1.23，num2=-123，n=2，返回 0（两者一样大）；<p>
     * num1=123，num2=234，n=2，返回 -1（后者大）；<p>
     * num1=234，num2=123，n=2，返回 1（前者大）。
     */
    public static int bitComparison(BigDecimal num1, BigDecimal num2, int n) {
        isNotNull(num1, num2);
        // 使用正则表达式，将正负号和小数点去除掉
        String reg = "[-+.]";
        String s1 = num1.toPlainString().replaceAll(reg, "");
        String s2 = num2.toPlainString().replaceAll(reg, "");
        int len = s1.length() <= s2.length() ? s1.length() : s2.length();
        // n 大于等于 s1和 s2的最小长度，越界抛异常
        if (len <= n || n < 0) {
            throw new CommonException(CommonError.ArrayIndexOutOfBoundsException.getMessage());
        }
        int n1 = s1.charAt(n);
        int n2 = s2.charAt(n);
        // 比较大小
        if (n1 > n2) {
            return 1;
        } else if (n1 < n2) {
            return -1;
        }
        return 0;
    }

    /**
     * 将 Double类型的 num转换为 BigDecimal类型，需要对转换结果设置小数点后的保留位数 scale以及舍入模式 RoundingMode。
     * 该方法需要对参数进行非空判断，若 num或 scale或 roundingMode为空，则抛出参数不能为空异常。
     *
     * @param num          Double类型，不可为空
     * @param scale        小数点后的保留位数，不可为空
     * @param roundingMode 舍入模式，RoundingMode类型，不可为空
     * @return BigDecimal类型
     *
     * <p>
     * <b>例：</b><br>
     * num=null，scale=2，roundingMode=RoundingMode.HALF_UP，抛出“参数不能为空”异常提示信息；<p>
     * num=3.14，scale=null，roundingMode=RoundingMode.HALF_UP，抛出“参数不能为空”异常提示信息；<p>
     * num=3.14，scale=null，roundingMode=null，抛出“参数不能为空”异常提示信息；<p>
     * num=3.1415926，scale=2，roundingMode=RoundingMode.HALF_UP，返回 3.14；<p>
     * num=3.1415926，scale=3，roundingMode=RoundingMode.HALF_DOWN，返回 3.142；<p>
     * num=3.1415926，scale=4，roundingMode=RoundingMode.UP，返回 3.1416。
     */
    public static BigDecimal toBigDecimal(Double num, Integer scale, RoundingMode roundingMode) {
        isNotNull(num, scale, roundingMode);
        return BigDecimal.valueOf(num).setScale(scale, roundingMode);
    }

    /**
     * 将 Double类型的 num转换为 BigDecimal类型，提供默认的舍入模式 DEFAULT_ROUNDINGMODE（四舍五入）。
     * 该方法需要对参数进行非空判断，若 num为空，则抛出参数不能为空异常。
     *
     * @param num   Double类型，不可为空
     * @param scale 小数点后的保留位数，可为空，为空时采用 DEFAULT_SCALE（保留小数点后两位）
     * @return BigDecimal类型
     *
     * <p>
     * <b>例：</b><br>
     * num=null，抛出“参数不能为空”异常提示信息；<p>
     * num=3.1415926，scale=null，返回 3.14；<p>
     * num=3.1415926，scale=3，返回 3.142。
     */
    public static BigDecimal toBigDecimal(Double num, @Nullable Integer scale) {
        return toBigDecimal(num, scale == null ? DEFAULT_SCALE : scale, DEFAULT_ROUNDINGMODE);
    }

    /**
     * 将 Double类型的 num转换为 BigDecimal类型。
     * 该方法需要对参数进行非空判断，若 num为空，则抛出参数不能为空异常。
     *
     * @param num Double类型，不可为空
     * @return BigDecimal类型
     * <p>
     * <b>例：</b><br>
     * num=null，抛出“参数不能为空”异常提示信息；<p>
     * num=3.1415926，返回 3.1415926。
     */
    public static BigDecimal toBigDecimal(Double num) {
        isNotNull(num);
        return BigDecimal.valueOf(num);
    }

    /**
     * 将 String类型的 value转换为 BigDecimal类型。
     * 该方法需要对参数进行非空判断和纯数字校验，若 value为空或空值，则抛出参数不能为空异常；若 value为非纯数字字符串，则抛出内容格式错误。
     *
     * @param value Double类型，不可为空且为纯数字
     * @return BigDecimal类型
     *
     * <p>
     * <b>例：</b><br>
     * value=null，抛出“参数不能为空”异常提示信息；<p>
     * value="abc123"，抛出“内容格式错误”异常提示信息；<p>
     * value="123"，返回 123；<p>
     * value="3.1415926"，返回 3.1415926。
     */
    public static BigDecimal toBigDecimal(String value) {
        if (!StringUtil.isNumeric(value)) {
            throw new CommonException(CommonError.ContentFormatInvalid.getMessage());
        }
        return new BigDecimal(value);
    }

    /**
     * 将 BigDecimal类型的 num转换为 double类型。
     * 该方法需要对参数进行非空判断，若 num为空，则抛出参数不能为空异常。
     *
     * @param num BigDecimal类型，不可为空
     * @return double类型
     *
     * <p>
     * <b>例：</b><br>
     * num=null，抛出“参数不能为空”异常提示信息；<p>
     * num=3.1415926，返回 3.1415926。
     */
    public static double toDouble(BigDecimal num) {
        isNotNull(num);
        return num.doubleValue();
    }

    private static void isNotNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new CommonException(PARAM_IS_NULL_WARN);
            }
        }
    }

}
