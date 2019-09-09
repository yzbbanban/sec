package com.yzb.sec.dao;

import com.yzb.sec.domain.dto.SysManageResourceAddChildrenDTO;
import com.yzb.sec.domain.dto.SysManageResourceAddParentDTO;
import com.yzb.sec.domain.dto.SysManageResourceUpdateDTO;
import com.yzb.sec.domain.dto.SysManageResourceUpdateStateDTO;
import com.yzb.sec.domain.orm.SysManageResource;
import com.yzb.sec.domain.vo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统管理资源
 *
 * @author wangban
 */
public interface SysManageResourceDao {
    /**
     * 保存系统资源父节点信息
     *
     * @param resourceAddParentDTO 系统管理资源信息
     * @return 保存结果>0保存成功
     */
    int saveSysManageParentResource(SysManageResourceAddParentDTO resourceAddParentDTO);

    /**
     * 保存系统资源子节点信息
     *
     * @param resourceAddChildrenDTO 系统管理资源信息
     * @return 保存结果>0保存成功
     */
    int saveSysManageChildrenResource(SysManageResourceAddChildrenDTO resourceAddChildrenDTO);

    /**
     * 更新系统管理资源信息
     *
     * @param resourceUpdateDTO 系统管理资源信息
     * @return 更新结果>0更新成功
     */
    int updateSysManageResource(SysManageResourceUpdateDTO resourceUpdateDTO);

    /**
     * 更新系统管理资源信息状态
     *
     * @param updateStateDTO 系统资源状态
     * @return
     */
    int updateSysManageResourceState(SysManageResourceUpdateStateDTO updateStateDTO);

    /**
     * 根据id删除系统管理资源信息
     *
     * @param id 资源id
     * @return 删除结果>0删除成功
     */
    int deleteSysManageResource(Integer id);

    /**
     * 获取系统管理资源信息
     *
     * @param id 资源id
     * @return 系统管理资源信息
     */
    SysManageResource getSysManageResourceById(Integer id);

    /**
     * 获取所有系统管理资源信息
     *
     * @return 系统管理资源信息列表
     */
    List<SysManageResource> listSysManageResource();

    /**
     * 获取所有菜单
     *
     * @param parentId 父id 为0
     * @return 所有菜单
     */
    List<Menu> getAllMenu(Integer parentId);

    /**
     * 获取有角色的菜单
     *
     * @param parentId 父id 为0
     * @param roleId 角色id
     * @return 所有菜单
     */
    List<Menu> getAllMenuWithRole(@Param("parentId") Integer parentId, @Param("roleId") Integer roleId);

}
