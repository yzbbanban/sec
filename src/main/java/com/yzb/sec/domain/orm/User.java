package com.yzb.sec.domain.orm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息")
public class User {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("盐")
    private String salt;
    @ApiModelProperty("创建时间")
    private Long createTime;
    @ApiModelProperty("更新时间")
    private Long updateTime;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("国家代码")
    private String countryCode;
    @ApiModelProperty("设备 id")
    private String deviceId;
    @ApiModelProperty("设备来源")
    private String from;
    @ApiModelProperty("用户状态")
    private Boolean status;

}
