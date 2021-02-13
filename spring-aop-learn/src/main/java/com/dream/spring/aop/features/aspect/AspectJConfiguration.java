package com.dream.spring.aop.features.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author lim
 * @date 2021/2/10 23:38
 * @className AspectJConfiguration
 * @desc TODO
 */
@Aspect
public class AspectJConfiguration {

    // PointCut 只做判断不做执行动作
    @Pointcut("execution(public * *(..))") // 拦截任何public方法
    private void pointCut() {
        System.out.println("@Pointcut 拦截任务public方法 ");
    }

    @Before(value = "pointCut()")  // Joint Point 拦截动作
    public void before(JoinPoint joinPoint) {
        System.out.println("@Before 拦截 " + joinPoint.getSignature().getName());
    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        System.out.println("@Around(value = \"pointCut()\")" + methodSignature.getName());
        return proceedingJoinPoint.proceed();
    }

    @After("pointCut()")
    public void  after(){
        System.out.println("@After(value = \"pointCut()\")");
    }
}
