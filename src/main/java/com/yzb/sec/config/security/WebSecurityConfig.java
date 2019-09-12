package com.yzb.sec.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzb.sec.common.util.SysManageUserUtils;
import com.yzb.sec.config.redis.RedisClusterClient;
import com.yzb.sec.domain.result.ResultJson;
import com.yzb.sec.service.impl.SysMangeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * @author wangban
 * @data 2019/9/2 11:42
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysMangeServiceImpl hrService;
    @Autowired
    CustomMetadataSource metadataSource;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    AuthenticationAccessDeniedHandler deniedHandler;

    @Autowired
    YAuthenticationFailureHandler yAuthenticationFailureHandler;
    @Autowired
    YAuthenticationSuccessHandler yAuthenticationSuccessHandler;
    @Autowired
    YLogoutSuccessHandler yLogoutSuccessHandler;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    protected RedisClusterClient redisClient;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(hrService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/login_p", "/favicon.ico");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/v1/manage/sms/login", "/swagger-ui.html", "/images/**", "/swagger-resources/**",
                        "/v1/auth/login", "/webjars/**", "/v2/api-docs",
                        "/v1/auth/logout").permitAll().anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().loginPage("/login_p").loginProcessingUrl("/v1/auth/login")
                .usernameParameter("account").passwordParameter("pass")
                .failureHandler(yAuthenticationFailureHandler)
                .successHandler(yAuthenticationSuccessHandler)
                .and()
                .logout()
                .logoutUrl("/v1/auth/logout")
                .logoutSuccessHandler(yLogoutSuccessHandler);
        // 在 UsernamePasswordAuthenticationFilter 前添加 jwtAuthenticationTokenFilter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加权限不足 filter
                .exceptionHandling().accessDeniedHandler(deniedHandler);
    }
}