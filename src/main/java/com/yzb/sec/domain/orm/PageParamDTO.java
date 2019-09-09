package com.yzb.sec.domain.orm;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by brander on 2019/1/12
 */
@Data
@ApiModel("分页数据")
public class PageParamDTO {

    @ApiModelProperty("页码")
    private Integer pageNo;
    @ApiModelProperty("页数")
    private Integer pageSize;


}
