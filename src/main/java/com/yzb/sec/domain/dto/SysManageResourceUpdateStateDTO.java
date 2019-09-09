package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/11
 */
@ApiModel("系统资源状态更新")
public class SysManageResourceUpdateStateDTO {
    @ApiModelProperty("id")
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
        return "SysManageResourceUpdateStateDTO{" +
                "id=" + id +
                ", useable=" + useable +
                '}';
    }
}
