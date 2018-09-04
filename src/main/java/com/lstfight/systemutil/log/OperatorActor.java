package com.lstfight.systemutil.log;

import org.aspectj.lang.JoinPoint;

/**
 * @author 李尚庭
 */
public interface OperatorActor<T> {

    /**
     * Actor 工作接口
     * @param joinPoint 切点信息
     * @param targetResult 目标对象（被代理对象）返回值
     * @return action后结果
     */
    T action(JoinPoint joinPoint, T targetResult);

}
