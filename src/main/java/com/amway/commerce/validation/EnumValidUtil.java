package com.amway.commerce.validation;

import java.lang.reflect.Field;

/**
 * @author: Jason.Hu
 * @date: 2023-08-07
 * @desc: 枚举工具类
 */
public class EnumValidUtil {

    /**
     * 判断指定字符串是否是枚举类型的某个属性
     *
     * @param enumClass 枚举类型
     * @param input     待检验字符串
     * @return 校验结果，true表示属于
     */
    public static boolean isEnumFiled(Class<?> enumClass, String input) {
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("The class is not an enumeration type.");
        }
        try {
            // 通过反射获取该枚举类型的所有字段
            Field[] fields = enumClass.getDeclaredFields();
            for (Field field : fields) {
                // 如果该字段是枚举常量
                if (field.isEnumConstant() && ((Enum<?>) field.get(null)).name().equals(input)) {
                    return true;
                }
            }
            return false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Enum Type Access Exception", e);
        }
    }

    /**
     * 判断指定字符串是否是枚举类型的 fieldName属性
     *
     * @param enumClass 枚举类型
     * @param fieldName 属性名称
     * @param input     待检验字符串
     * @return 校验结果，true表示属于
     */
    public static boolean isEnumFiled(Class<?> enumClass, String fieldName, String input) {
        // 检查给定的类是否是枚举类型
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("The class is not an enumeration type.");
        }
        try {
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
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Enum Type Access Exception", e);
        }
    }
}
