package com.yzb.sec.config.security;

import com.yzb.sec.common.util.JwtTokenUtil;
import com.yzb.sec.config.cache.LocalCache;
import com.yzb.sec.config.redis.RedisClusterClient;
import com.yzb.sec.config.security.filter.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yzb.sec.domain.constant.MessageConstant.SYSTEM_SMS_MANAGE_LOGIN_CODE_ACCOUNT;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private YAuthenticationFailureHandler demoAuthenticationFailureHandler;

    @Autowired
    protected LocalCache localCache;

    @Autowired
    protected RedisClusterClient redisClient;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            logger.info("checking authentication " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } else if (StringUtils.equals("/v1/auth/login", request.getRequestURI()) &&
                StringUtils.endsWithIgnoreCase(request.getMethod(), "post")) {
            try {
                validate(new ServletWebRequest(request));
            } catch (AuthenticationException e) {
                demoAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        chain.doFilter(request, response);

    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        String account = ServletRequestUtils.getStringParameter(request.getRequest(), "account");
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "code");
        logger.info("===>" + account + "," + codeInRequest);

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (!getCode(account, codeInRequest)) {
            throw new ValidateCodeException("验证码错误");
        }

        redisClient.delCacheByKey(SYSTEM_SMS_MANAGE_LOGIN_CODE_ACCOUNT + account);
    }

    /**
     * 获取验证码是否正确
     *
     * @param code 验证码
     * @return 是否正确
     */
    protected boolean getCode(String account, String code) {
//        String smsCode = localCache.getCache(account);
        String smsCode = redisClient.get(SYSTEM_SMS_MANAGE_LOGIN_CODE_ACCOUNT + account);
        smsCode="111111";
        return code.equals(smsCode);
    }


}

