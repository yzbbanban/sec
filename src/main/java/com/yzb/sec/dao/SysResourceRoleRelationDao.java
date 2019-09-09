package com.yzb.sec.dao;

import com.yzb.sec.domain.dto.SysManageResourceRoleDTO;
import com.yzb.sec.domain.orm.SysResourceRoleRelation;

/**
 * 资源角色相关业务
 *
 * @author wangban
 * @date 9:48 2018/7/31
 */
public interface SysResourceRoleRelationDao {
    /**
     * 保存资源对应的角色信息
     *
     * @param sysManageResourceRoleDTO 角色与资源
     * @return 保存结果
     */
    int saveResRoles(SysManageResourceRoleDTO sysManageResourceRoleDTO);

    /**
     * 根据角色和资源的id的数量
     *
     * @param sysResourceRoleRelation 角色和资源
     * @return 数量>0则存在此数据
     */
    int getResourceRole(SysResourceRoleRelation sysResourceRoleRelation);

    /**
     * 查询角色id数量，用于判断您是否存在此信息
     *
     * @param sysRoleId 角色id
     * @return 数量
     */
    int getResourceRoleByRoleId(Integer sysRoleId);

    /**
     * 删除角色与资源的字段
     *
     * @param sysRoleId 角色
     * @return 删除结果>0删除成功
     */
    int deleteRoleByRoleId(Integer sysRoleId);
}
