package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/12
 */
@ApiModel("锁定用户")
public class SysManageUserLockDTO {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("是否锁定")
    private Boolean locked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "SysManageUserLockDTO{" +
                "id=" + id +
                ", locked=" + locked +
                '}';
    }
}
