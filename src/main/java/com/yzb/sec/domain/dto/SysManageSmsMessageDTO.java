package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/27
 */
@ApiModel("短信")
public class SysManageSmsMessageDTO {
    @ApiModelProperty("手机号")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "SysManageSmsMessageDTO{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
