package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/12
 */
@ApiModel("系统管理员更新")
public class SysManageUserUpdateDTO {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("电话")
    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "SysManageUserUpdateDTO{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
