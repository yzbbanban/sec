package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/2/6
 */
@ApiModel("锁定用户")
public class UserLockDTO {

    @ApiModelProperty("用户 id")
    private Integer id;
    @ApiModelProperty("是否可用")
    private Boolean useable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    @Override
    public String toString() {
        return "UserLockDTO{" +
                "id=" + id +
                ", useable=" + useable +
                '}';
    }
}
