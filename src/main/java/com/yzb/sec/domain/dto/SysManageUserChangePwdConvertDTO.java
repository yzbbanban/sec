package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/12
 */
@ApiModel("更改密码")
public class SysManageUserChangePwdConvertDTO {
    @ApiModelProperty(value = "id", hidden = true)
    private Integer id;
    @ApiModelProperty("密码")
    private String pass;
    @ApiModelProperty(value = "盐", hidden = true)
    private String salt;
    @ApiModelProperty("验证码")
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SysManageUserChangePwdConvertDTO{" +
                "id=" + id +
                ", pass='" + pass + '\'' +
                ", salt='" + salt + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
