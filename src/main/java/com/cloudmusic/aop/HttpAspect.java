package com.cloudmusic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 计算接口执行时间
 * Created by xuzijia
 * 2018/5/31 20:14
 */
@Component
@Aspect
public class HttpAspect {
    String methodName;      // 方法名
    String uri;//请求地址
    long startTime;         // 开始时间

    private final Logger logger= LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(* com.cloudmusic.controller..*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void aopPointCut() {}


    @Before("aopPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        uri = request.getRequestURI();
        methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        startTime = System.currentTimeMillis();
    }

    @After("aopPointCut()")
    public void doAfter() {
        long E_time = System.currentTimeMillis() - startTime;
        logger.info("执行 [" + uri + "] 耗时为：" + E_time + "ms");
    }

    @AfterReturning(returning = "object", pointcut = "aopPointCut()")
    public void doAfterReturning(Object object) {
    }
}
