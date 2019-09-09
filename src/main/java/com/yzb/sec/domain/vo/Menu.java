package com.yzb.sec.domain.vo;

import com.yzb.sec.domain.orm.MenuMeta;
import com.yzb.sec.domain.orm.SysManageRole;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * menu
 *
 * @author wangban
 * @data 2019/9/5 10:34
 */
@Data
@ApiModel("菜单")
public class Menu {
    private Integer id;
    private String resName;
    private Integer parentId;
    private String resKey;
    private Integer resType;
    private String resUrl;
    private Boolean useable;
    private Integer sort;
    private List<SysManageRole> roles;
    private List<Menu> children;
    private MenuMeta meta;
    private Boolean checked;
}
