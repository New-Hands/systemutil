package com.lstfight.systemutil.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <p>切面 需要主容器开启切面扫描支持，并使用基于类的代理</p>
 *
 * @author 李尚庭
 */
@Aspect
@Component
public class OperatorLogAspect {

    /**
     * 具体操作对象容器
     */
    private static Map<String, OperatorActor<Object>> operatorActor = new HashMap<>(4);


    static {
        operatorActor.put("default", new DefaultOperatorActor());
    }

    /**
     * 注册器方法,可注册具体处理角色
     *
     * @param actor 具体处理角色
     * @param name  处理器标识与{@link OperatorLog 中value字段值一致}
     */
    public static void register(String name, OperatorActor<Object> actor) {
        operatorActor.put(name, actor);
    }

    /**
     * 定义切点为 {@link OperatorLog 标识的点}
     */
    @Pointcut("@annotation(com.lstfight.systemutil.log.OperatorLog)")
    public void operatorLogPoint() {

    }

    /**
     * 切点after处理 在目标对象方法调用后增强
     *
     * @param joinPoint 切点
     * @param result    调用结果
     * @return 返回值
     */
    @AfterReturning(returning = "result", value = "operatorLogPoint()&&@annotation(operatorLog)")
    public Object operator(JoinPoint joinPoint, Object result, OperatorLog operatorLog) {
        //可扩展的日志处理
        return operatorActor.get(operatorLog.value()).action(joinPoint, result);
    }

}
