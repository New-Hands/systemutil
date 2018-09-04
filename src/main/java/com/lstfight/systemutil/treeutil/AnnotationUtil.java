package com.lstfight.systemutil.treeutil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author 李尚庭
 * @date 2018/8/24 0024 12:30
 */
public class AnnotationUtil {
    /**
     *
     * @param targetAnnotation 目标注解实例
     * @param TargetFiled 目标属性
     * @param targetValue 目标值
     */
    public static void changeAnnotationFileValue(Annotation targetAnnotation, String targetFiled, String targetValue) {


        InvocationHandler h = Proxy.getInvocationHandler(targetAnnotation);

        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field hField = null;
        try {
            hField = h.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        // 因为这个字段事 private final 修饰，所以要打开权限
        hField.setAccessible(true);
        // 获取 memberValues
        Map memberValues = null;
        try {
            memberValues = (Map<String,String>) hField.get(h);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 修改 value 属性值
        memberValues.put(targetFiled, targetValue);
    }
}
