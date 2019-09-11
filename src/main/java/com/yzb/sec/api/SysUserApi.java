package com.yzb.sec.api;

import com.yzb.sec.domain.dto.SysManageUserAddDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统用户api
 *
 * @author wangban
 * @data 2019/9/11 10:50
 */
@RestController
@RequestMapping("v1/sysUser")
public class SysUserApi extends BaseApi {


    @PostMapping("register")
    public void register(SysManageUserAddDTO addDTO, ServletResponse resp) {
        logger.info("===>" + addDTO);
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        try {
            httpServletResponse.sendRedirect("/sysUser/login");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
