package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/27
 */
@ApiModel("用户添加")
public class SysManageUserAddDTO {
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("密码")
    private String pass;
    @ApiModelProperty("电话")
    private String mobile;
    @ApiModelProperty("真实姓名")
    private String realName;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
