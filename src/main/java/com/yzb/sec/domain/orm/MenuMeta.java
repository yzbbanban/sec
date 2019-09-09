package com.yzb.sec.domain.orm;

import lombok.Data;

import java.io.Serializable;

/**
  * @author wangban
  * @data 2019/9/5 10:40
  */
@Data
public class MenuMeta implements Serializable {

    private boolean keepAlive;
    private boolean requireAuth;

}
