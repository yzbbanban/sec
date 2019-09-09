package com.yzb.sec.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/1/11
 */
@ApiModel("添加父系统资源")
public class SysManageResourceAddParentDTO {
    @ApiModelProperty("资源名称")
    private String resName;
    @ApiModelProperty("父 id")
    private Integer parentId;
    @ApiModelProperty("资源 key")
    private String resKey;
    @ApiModelProperty("资源类型，1父类，2子类")
    private Integer resType;
    @ApiModelProperty("资源路径，前端用")
    private String resUrl;
    @ApiModelProperty("是否可用")
    private Boolean useable;
    @ApiModelProperty("排序")
    private Integer sort;

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        this.resKey = resKey;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SysManageResourceAddParentDTO{" +
                "resName='" + resName + '\'' +
                ", parentId=" + parentId +
                ", resKey='" + resKey + '\'' +
                ", resType=" + resType +
                ", resUrl='" + resUrl + '\'' +
                ", useable=" + useable +
                ", sort=" + sort +
                '}';
    }
}
