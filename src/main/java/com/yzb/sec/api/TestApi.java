package com.yzb.sec.api;

import com.yzb.sec.domain.result.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/test")
@RestController
public class TestApi {

    @GetMapping("auth")
    public ResultJson<String> testAuth() {
        return ResultJson.createBySuccess();
    }
}
