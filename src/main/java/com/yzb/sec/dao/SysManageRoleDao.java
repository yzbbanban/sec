package com.yzb.sec.dao;


import com.yzb.sec.domain.dto.SysManageRoleDTO;
import com.yzb.sec.domain.dto.SysManageRoleStateDTO;
import com.yzb.sec.domain.dto.SysManageRoleUpdateDTO;
import com.yzb.sec.domain.orm.PageParamDTO;
import com.yzb.sec.domain.orm.SysManageRole;

import java.util.List;

/**
 * 系统管理角色
 *
 * @author wangban
 */
public interface SysManageRoleDao {

    /**
     * 保存系统管理角色信息
     *
     * @param sysManageRoleDTO 系统管理角色
     * @return 保存结果>0保存成功
     */
    int saveSysManageRole(SysManageRoleDTO sysManageRoleDTO);

    /**
     * 更新系统管理角色信息
     *
     * @param sysManageRoleUpdateDTO 系统管理角色
     * @return 更新结果>0更新成功
     */
    int updateSysManageRole(SysManageRoleUpdateDTO sysManageRoleUpdateDTO);

    /**
     * 更新系统管理角色状态
     *
     * @param sysManageRoleStateDTO 系统角色状态
     * @return 更新结果>0更新成功
     */
    int updateSysManageRoleState(SysManageRoleStateDTO sysManageRoleStateDTO);

    /**
     * 获取系统管理角色列表
     *
     * @param pageParamsDTO 页码信息
     * @return 系统管理角色列表信息
     */
    List<SysManageRole> listSysManageRole(PageParamDTO pageParamsDTO);

    /**
     * 获取系统管理角色数量
     *
     * @return 数量
     */
    int getSysManageRoleCount();


}
