package com.dream.spring.aop.features.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * @author lim
 * @date 2021/2/10 23:38
 * @className AspectJConfiguration
 * @desc TODO
 */
@Aspect
public class AspectJConfiguration {

    // PointCut 只做判断不做执行动作
    @Pointcut("execution(public * *(..))") // 拦截任何public方法
    private void pointCut(){
        System.out.println("@Pointcut 拦截任务public方法 ");
    }

    @Before(value = "pointCut()")  // Joint Point 拦截动作
    public void before(JoinPoint joinPoint){
        System.out.println("@Before 拦截 " + joinPoint.getSignature().getName());
    }
}
