package com.yzb.sec.domain.dto;

/**
 * Created by brander on 2019/1/27
 */
public class SysManageUserLoginDTO {
    private String account;
    private String pass;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
