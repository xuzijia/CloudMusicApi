package com.cloudmusic.aop;

import com.alibaba.fastjson.JSONObject;
import com.cloudmusic.dao.ApiLogDao;
import com.cloudmusic.pojo.ApiLog;
import com.cloudmusic.utils.CloudMusicUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

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
    String ip; //访问ip
    Object result; // 结果集
    long responseTime; //接口响应时间
    String queryString; //请求参数
    String url; //完整的请求地址
    Date requestDate; //请求时间
    String method; // 请求方法

    @Autowired
    private ApiLogDao apiLogDao;

    private final Logger logger= LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(* com.cloudmusic.controller..*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void aopPointCut() {}


    @Before("aopPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        ip = CloudMusicUtil.getIpAddress(request);
        uri = request.getRequestURI();
        methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        startTime = System.currentTimeMillis();
        url=request.getRequestURL().toString();
        method=request.getMethod();
        Map<String, String[]> parameterMap = request.getParameterMap();
        queryString = String.valueOf(JSONObject.toJSON(parameterMap));
        requestDate=new Date();
    }

    @After("aopPointCut()")
    public void doAfter() {
        responseTime = System.currentTimeMillis() - startTime;
        logger.info("["+ip+"] 执行 [" + uri + "] 耗时为：" + responseTime + "ms");
    }

    @AfterReturning(returning = "object", pointcut = "aopPointCut()")
    public void doAfterReturning(Object object) {
        //保存结果集
        result=object;
        //保存接口请求日志
        ApiLog apiLog = new ApiLog();
        apiLog.setApiName(uri);
        apiLog.setMethod(method);
        apiLog.setMethodName(methodName);
        apiLog.setParams(queryString);
        apiLog.setUrl(url);
        apiLog.setResponseTime(responseTime);
        apiLog.setResult(String.valueOf(object));
        apiLog.setRequestIp(ip);
        apiLog.setRequestDate(requestDate);
        apiLogDao.save(apiLog);

    }



}
