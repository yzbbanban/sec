package com.yzb.sec.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzb.sec.common.util.JwtTokenUtil;
import com.yzb.sec.common.util.SysManageUserUtils;
import com.yzb.sec.domain.orm.SysAuthUser;
import com.yzb.sec.domain.result.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("yAuthenticationSuccessHandler")
public class YAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(YAuthenticationSuccessHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 处理登录成功的请求
     *
     * @param httpServletRequest  , authentication—封装登录信息的]
     * @param httpServletResponse , authentication—封装登录信息的]
     * @return void
     * @author hdd
     * @date 2018/12/10 0010 10:16
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException, ServletException {
        logger.info("登录成功");
        SysAuthUser userDetail = SysManageUserUtils.getCurrentHr();
        String token = jwtTokenUtil.generateToken(userDetail);
        userDetail.setToken(token);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ResultJson respBean = ResultJson.createBySuccess("登录成功!", userDetail);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(objectMapper.writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}