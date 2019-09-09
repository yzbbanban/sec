package com.yzb.sec.domain.orm;

import lombok.Data;

/**
 * Created by brander on 2019/1/11
 */
@Data
public class SysManageResource {
    private Integer id;
    private String resName;
    private Integer parentId;
    private String resKey;
    private Integer resType;
    private String resUrl;
    private Boolean useable;
    private Integer sort;
    private Long createTime;
    private Long updateTime;
}
