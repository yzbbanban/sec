package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/12
 */
@ApiModel("角色状态")
public class SysManageRoleStateDTO {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("状态，1可用，0不可用")
    private Integer roleStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    @Override
    public String toString() {
        return "SysManageRoleStateDTO{" +
                "id=" + id +
                ", roleStatus=" + roleStatus +
                '}';
    }
}
