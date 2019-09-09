package com.yzb.sec.dao;

import com.yzb.sec.domain.orm.SysUserRoleRelation;

/**
 * 用户与角色关系
 *
 * @author wangban
 */
public interface SysUserRoleRelationDao {
    /**
     * 添加用户权限
     *
     * @param sysUserRoleRelation 用户与角色
     * @return 保存结果>0保存成功
     */
    int saveUserRole(SysUserRoleRelation sysUserRoleRelation);

    /**
     * 根据管理用户id查询角色id
     *
     * @param sysUserId 管理用户id
     * @return 角色id
     */
    Integer getRoleIdByUserId(Integer sysUserId);

    /**
     * 更新用户角色
     *
     * @param sysUserRoleRelation 管理用户id、角色id
     * @return 更新
     */
    int updateUserRole(SysUserRoleRelation sysUserRoleRelation);
}
