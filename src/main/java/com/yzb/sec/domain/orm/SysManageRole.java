package com.yzb.sec.domain.orm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by brander on 2019/1/12
 */
@Data
@ApiModel("角色信息")
public class SysManageRole {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("角色名")
    private String roleName;
    @ApiModelProperty("角色关键字")
    private String roleKey;
    @ApiModelProperty("角色状态")
    private Integer roleStatus;

}
