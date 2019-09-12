package com.yzb.sec.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzb.sec.domain.result.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("yAuthenticationFailureHandler")
public class YAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(YAuthenticationFailureHandler.class);

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
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        ResultJson respBean = null;
        if (e instanceof BadCredentialsException ||
                e instanceof UsernameNotFoundException) {
            respBean = ResultJson.createByErrorMsg("账户名或者密码输入错误!");
        } else if (e instanceof LockedException) {
            respBean = ResultJson.createByErrorMsg("账户被锁定，请联系管理员!");
        } else if (e instanceof CredentialsExpiredException) {
            respBean = ResultJson.createByErrorMsg("密码过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            respBean = ResultJson.createByErrorMsg("账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            respBean = ResultJson.createByErrorMsg("账户被禁用，请联系管理员!");
        } else {
            respBean = ResultJson.createByErrorMsg("登录失败!");
        }
        resp.setStatus(401);
        PrintWriter out = resp.getWriter();
        out.write(objectMapper.writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
