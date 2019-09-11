package com.yzb.sec.domain.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by brander on 2019/1/14
 */
@Data
@ApiModel("列表")
public class ResultList<T> {

    @ApiModelProperty("数量")
    private Integer count;
    @ApiModelProperty("列表数据")
    private List<T> dataList;

    public ResultList(Integer count, List<T> dataList) {
        this.count = count;
        this.dataList = dataList;
    }
}
