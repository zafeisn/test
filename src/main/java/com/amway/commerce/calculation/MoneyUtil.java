package com.amway.commerce.calculation;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * @author: Jason.Hu
 * @date: 2023-08-11
 */
public class MoneyUtil {

    /**
     * 美元
     */
    public static final String DOLLAR_PATTERN = "$###,##0.00";
    /**
     * 人民币
     */
    public static final String RMB_PATTERN = "￥###,##0.00";
    /**
     * 欧元
     */
    public static final String EURO_PATTERN = "€###,##0.00";
    /**
     * 英镑
     */
    public static final String POUND_PATTERN = "￡###,##0.00";

    /**
     * 将 BigDecimal类型的 money转换为货币的表现形式，默认采用四舍五入的舍入模式，如将 123456789.09876以“###,##0.00”的 Pattern格式转换为“123,456,789.10”。
     * 该方法需要对参数进行非空判断，若 money或 pattern为空，则抛出参数不能为空异常。
     *
     * @param money   BigDecimal类型，待格式化的金额，不可为空
     * @param pattern 模式字符串，不可为空且需要符合 DecimalFormat的 Pattern模式，详情见 {@link java.text.DecimalFormat}的 Special Pattern Characters或 applyPattern(String,boolean)方法
     * @return 转换后的货币字符串
     *
     * <p>
     * <b>例：</b><br>
     * bigDecimal=null，抛出“参数不能为空”异常提示信息；<p>
     * bigDecimal=null，pattern=null，抛出“参数不能为空”异常提示信息；<p>
     * bigDecimal=3.1415926，pattern=DOLLAR_PATTERN，返回 $3.142；<p>
     * bigDecimal=31415.926535，pattern=RMB_PATTERN，返回 ￥31,415.9265；<p>
     * bigDecimal=31415.0000，pattern=EURO_PATTERN，返回 €31,415；<p>
     * bigDecimal=31415.0000，pattern=POUND_PATTERN，返回 ￡31,415.0000。
     */
    public static String format(BigDecimal money, String pattern) {
        isNotNull(money, pattern);
        return new DecimalFormat(pattern).format(money);
    }

    /**
     * 将 BigDecimal类型的 money转换为 RMB_PATTERN格式的货币表现形式，默认采用四舍五入的舍入模式，如将 123456789.09876转换为“￥123,456,789.10”。
     * 该方法需要对参数进行非空判断，若 money为空，则抛出参数不能为空异常。
     *
     * @param money BigDecimal类型，待格式化的金额，不可为空
     * @return 转换后的货币字符串
     *
     * <p>
     * 例：bigDecimal=null，抛出“参数不能为空”异常提示信息；<p>
     * bigDecimal=31415.926535，返回 ￥31,415.9265。
     */
    public static String format(BigDecimal money) {
        return format(money, RMB_PATTERN);
    }

    /**
     * 将转换后的货币字符串解析成 BigDecimal类型的对象，需要指定与货币字符串保持一致的 Pattern格式。
     * 该方法需要对参数进行非空判断，若 money或 pattern为空，则抛出参数不能为空异常。
     *
     * @param money   货币字符串，不可为空
     * @param pattern 模式字符串，不可为空且需要与 money的格式保持一致
     * @return 解析后的 BigDecimal对象
     * @throws ParseException
     *
     * <p>
     * <b>例：</b><br>
     * money=null，pattern=“$###,#####”，抛出“参数不能为空”异常提示信息；<p>
     * money=￥31,415.9265，pattern=“$###,#####”，抛出 java.text.ParseException异常；<p>
     * money=￥31,415.9265，pattern=RMB_PATTERN，返回 314159265。
     */
    public static BigDecimal parse(String money, String pattern) throws ParseException {
        isNotNull(money, pattern);
        Number parse = new DecimalFormat(pattern).parse(money);
        return BigDecimalUtil.toBigDecimal(parse.toString());
    }

    /**
     * 将转换后的货币字符串解析成 BigDecimal类型的对象，默认采用 RMB_PATTERN的格式进行解析。
     * 该方法需要对参数进行非空判断，若 money为空，则抛出参数不能为空异常。
     *
     * @param money 货币字符串，不可为空
     * @return 解析后的 BigDecimal对象
     * @throws ParseException
     *
     * <p>
     * <b>例：</b><br>
     * money=null，抛出“参数不能为空”异常提示信息；<p>
     * money="not currency"，抛出 java.text.ParseException异常；<p>
     * money=￥31,415.9265，返回 314159265。
     */
    public static BigDecimal parse(String money) throws ParseException {
        return parse(money, RMB_PATTERN);
    }

    private static void isNotNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new CommonException(CommonError.NotNull.getMessage());
            }
        }
    }

}
