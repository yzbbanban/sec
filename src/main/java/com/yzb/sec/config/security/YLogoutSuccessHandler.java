package com.yzb.sec.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzb.sec.domain.result.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangban
 * @data 2019/9/12 12:05
 */
@Component("yLogoutSuccessHandler")
public class YLogoutSuccessHandler implements LogoutSuccessHandler {


    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest hsr, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        ResultJson respBean = ResultJson.createBySuccess("注销成功!");
        PrintWriter out = resp.getWriter();
        out.write(objectMapper.writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
