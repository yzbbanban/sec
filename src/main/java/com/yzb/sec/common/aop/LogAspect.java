package com.yzb.sec.common.aop;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * log收集
 *
 * @author wangban
 * @date 11:50 2018/10/30
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private Gson gson = new Gson();

    /**
     * 申明一个切点 里面是 execution表达式
     */
    @Pointcut("execution(* com.yzb.sec.api..*.*(..))")
    private void controllerAspect() {
    }

    /**
     * 请求method前打印内容
     *
     * @param joinPoint obj
     */
    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //打印请求内容
        log.debug("╔═══════════════请求内容══════════════");
        log.debug("║  请求地址:" + request.getRequestURL().toString());
        log.debug("║  请求方式:" + request.getMethod());
        log.debug("║  请求类方法:" + joinPoint.getSignature());
        log.debug("║  请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        log.debug("╚═══════════════请求内容══════════════");
    }

    /**
     * 在方法执行完结后打印返回内容
     *
     * @param o obj
     */
    @AfterReturning(returning = "o", pointcut = "controllerAspect()")
    public void methodAfterReturning(Object o) {
        log.debug("╔═══════════════返回内容═══════════════");
        log.debug("║  Response内容:" + o);
        log.debug("║  ");
        log.debug("╚═══════════════返回内容═══════════════");
    }
}
