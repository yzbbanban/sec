package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/2/6
 */
@ApiModel("更换密码")
public class UserModifyDTO {

    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("国家代码")
    private String countryCode;
    @ApiModelProperty("验证码")
    private String code;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UserModifyDTO{" +
                "password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
