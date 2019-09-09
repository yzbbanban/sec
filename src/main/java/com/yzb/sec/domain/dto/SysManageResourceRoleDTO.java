package com.yzb.sec.domain.dto;

/**
 * Created by brander on 2019/1/12
 */
public class SysManageResourceRoleDTO {

    private String[] resArr;
    private Integer sysRoleId;

    public String[] getResArr() {
        return resArr;
    }

    public void setResArr(String[] resArr) {
        this.resArr = resArr;
    }

    public Integer getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    @Override
    public String toString() {
        return "SysManageResourceRoleDTO{" +
                "resArr=" + resArr +
                ", sysRoleId=" + sysRoleId +
                '}';
    }
}
