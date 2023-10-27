package com.amway.commerce.validation;

import com.amway.commerce.exception.CommonError;
import com.amway.commerce.exception.CommonException;
import java.lang.reflect.Field;

/**
 * @author: Jason.Hu
 * @date: 2023-08-10
 */
public class EnumValidUtil {

    /**
     * 判断 input是否为枚举类型的某个属性名。
     * 该方法需要对参数进行类型校验，若 enumClass不为枚举类，则抛出类型不匹配异常。
     *
     * @param enumClass Class类对象，须为枚举类
     * @param input     待检验字段
     * @return 布尔值，true表示属于
     */
    public static boolean isEnumFiled(Class<?> enumClass, String input) throws IllegalAccessException {
        isEnum(enumClass);
        // 通过反射获取该枚举类型的所有字段
        Field[] fields = enumClass.getDeclaredFields();
        for (Field field : fields) {
            // 如果该字段是枚举常量
            if (field.isEnumConstant() && ((Enum<?>) field.get(null)).name().equals(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断 input是否为枚举类型 fieldName的属性名。
     * 该方法需要对参数进行类型校验，若 enumClass不为枚举类，则抛出类型不匹配异常。
     *
     * @param enumClass Class类对象，须为枚举类
     * @param fieldName 属性名称
     * @param input     待检验字段
     * @return 布尔值，true表示属于
     */
    public static boolean isEnumFiled(Class<?> enumClass, String fieldName, String input) throws NoSuchFieldException, IllegalAccessException {
        // 检查给定的类是否是枚举类型
        isEnum(enumClass);
        // 通过反射获取该枚举类型的所有字段
        Field[] fields = enumClass.getDeclaredFields();
        for (Field field : fields) {
            // 如果该字段是枚举常量
            if (field.isEnumConstant()) {
                // 获取与 fieldName同名的字段
                Field valueField = field.getDeclaringClass().getDeclaredField(fieldName);
                if (!valueField.isAccessible()) {
                    // 关闭安全检查，获取 private字段的值
                    valueField.setAccessible(true);
                }
                Object value = valueField.get(field.get(null));
                if (value.toString().equals(input)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void isEnum(Class<?> enumClass) {
        if (!enumClass.isEnum()) {
            if (!enumClass.isEnum()) {
                throw new CommonException(CommonError.ClassMatchError.getMessage());
            }
        }
    }
}
