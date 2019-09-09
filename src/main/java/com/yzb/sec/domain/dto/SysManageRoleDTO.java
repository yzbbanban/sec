package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/12
 */
@ApiModel("系统角色")
public class SysManageRoleDTO {
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色名称key")
    private String roleKey;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    @Override
    public String toString() {
        return "SysManageRoleDTO{" +
                "roleName='" + roleName + '\'' +
                ", roleKey='" + roleKey + '\'' +
                '}';
    }
}
