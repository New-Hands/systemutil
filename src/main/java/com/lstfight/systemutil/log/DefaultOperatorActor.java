package com.lstfight.systemutil.log;

import org.aspectj.lang.JoinPoint;


/**
 * <p>默认的处理器</p>
 *
 * @author 李尚庭
 * @date 2018/8/29 0029 10:56
 */
public class DefaultOperatorActor implements OperatorActor<Object> {

    @Override
    public Object action(JoinPoint joinPoint, Object result) {

        String[] content = {

        };

        return result;
    }
}
