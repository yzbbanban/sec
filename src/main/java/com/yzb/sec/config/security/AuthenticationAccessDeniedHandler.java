package com.yzb.sec.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzb.sec.domain.result.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangban
 * @data 2019/9/2 11:41
 */
@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp,
                       AccessDeniedException e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        ResultJson error = ResultJson.createByErrorMsg("权限不足，请联系管理员!");
        out.write(objectMapper.writeValueAsString(error));
        out.flush();
        out.close();
    }
}
