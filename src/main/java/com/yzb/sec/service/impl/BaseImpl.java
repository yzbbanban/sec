package com.yzb.sec.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangban
 * @data 2019/9/11 11:17
 */
@Configuration
public class BaseImpl {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected List<String> getHttpParam(String... names) {
        List<String> results = new ArrayList<>(names.length);
        for (String name : names) {
            HttpServletRequest httpServletRequest = getHttpServletRequest();
            String n = String.valueOf(httpServletRequest.getAttribute(name));
            results.add(n);
        }
        return results;
    }

    protected HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes != null ? requestAttributes.getRequest() : null;
    }
}
