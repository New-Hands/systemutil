package com.lstfight.systemutil.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  <p>操作日志标记</p>
 *  @author 李尚庭
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperatorLog {
    String value() default "default";
}
