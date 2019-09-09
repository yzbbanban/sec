package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/14
 */
@ApiModel("添加角色权限")
public class SysManageResourceRoleAddDTO {
    @ApiModelProperty("角色 id")
    private Integer sysRoleId;
    @ApiModelProperty("资源 id 列表，用','隔开")
    private String resArr;

    public Integer getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Integer sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public String getResArr() {
        return resArr;
    }

    public void setResArr(String resArr) {
        this.resArr = resArr;
    }

    @Override
    public String toString() {
        return "SysManageResourceRoleAddDTO{" +
                "sysRoleId=" + sysRoleId +
                ", resArr='" + resArr + '\'' +
                '}';
    }
}
