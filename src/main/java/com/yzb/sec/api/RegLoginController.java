package com.yzb.sec.api;

import com.yzb.sec.domain.result.ResultJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangban
 * @data 2019/9/11 18:02
 */
@RestController
public class RegLoginController {
    @RequestMapping("/login_p")
    public ResultJson<String> login() {
        return ResultJson.createByErrorMsg("尚未登录，请登录!");
    }
}
