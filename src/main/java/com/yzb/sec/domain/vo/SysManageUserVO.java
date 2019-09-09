package com.yzb.sec.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by brander on 2019/1/12
 */
@Data
@ApiModel("平台用户")
public class SysManageUserVO {
    @ApiModelProperty(
            value = "字典ID"
    )
    private Integer id;
    @ApiModelProperty("登录账号")
    private String account;
    @ApiModelProperty("姓名")
    private String realName;
    @ApiModelProperty("账号锁定(0锁定；1未锁定)")
    private Boolean locked;
    @ApiModelProperty("创建时间")
    private Long createTime;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty("修改时间")
    private Long updateTime;
    @ApiModelProperty("手机号码")
    private String mobile;

}