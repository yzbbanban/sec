package com.yzb.sec.domain.dto;

import com.yzb.sec.domain.orm.PageParamDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by brander on 2019/2/5
 */
@ApiModel("用户搜索")
public class UserSearchDTO extends PageParamDTO {

    @ApiModelProperty("用户 id")
    private Integer id;
    @ApiModelProperty("手机号")
    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserSearchDTO{" +
                "mobile='" + mobile + '\'' +
                ", id=" + id +
                '}';
    }
}
