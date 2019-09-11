package com.yzb.sec.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzb.sec.domain.result.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("demoAuthenticationFailureHandler")
public class DemoAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(DemoAuthenticationFailureHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 处理登录失败的请求
     *
     * @param httpServletRequest
     * @return void
     * @author hdd
     * @date 2018/12/10 0010 10:15
     * @Param httpServletResponse , e-用来封装错误信息对象的]
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败");
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(ResultJson.createByErrorMsg(e.getMessage())));
    }
}
