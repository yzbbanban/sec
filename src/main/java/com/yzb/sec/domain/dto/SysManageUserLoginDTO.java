package com.yzb.sec.domain.dto;

import lombok.Data;

/**
 * Created by brander on 2019/1/27
 */
@Data
public class SysManageUserLoginDTO {
    private String account;
    private String pass;
    private String code;

}
