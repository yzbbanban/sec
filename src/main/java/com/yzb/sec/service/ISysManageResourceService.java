package com.yzb.sec.service;


import com.yzb.sec.domain.dto.SysManageResourceAddChildrenDTO;
import com.yzb.sec.domain.dto.SysManageResourceAddParentDTO;
import com.yzb.sec.domain.dto.SysManageResourceUpdateDTO;
import com.yzb.sec.domain.dto.SysManageResourceUpdateStateDTO;
import com.yzb.sec.domain.vo.Menu;

import java.util.List;

/**
 * 系统管理资源业务
 *
 * @author wangban
 * @date 15:17 2018/7/30
 */
public interface ISysManageResourceService {
    /**
     * 添加父节点系统管理资源
     *
     * @param resourceAddParentDTO 系统管理资源信息
     * @return 添加结果>0保存成功
     */
    boolean addSysManageParentResource(SysManageResourceAddParentDTO resourceAddParentDTO);

    /**
     * 添加子节点系统管理资源
     *
     * @param resourceAddChildrenDTO 系统管理资源信息
     * @return 添加结果>0保存成功
     */
    boolean addSysManageChildrenResource(SysManageResourceAddChildrenDTO resourceAddChildrenDTO);

    /**
     * 根据角色信息获取资源
     *
     * @param userId 用户id
     * @return 资源树
     */
    List<Menu> listRoleTreeByRole(Integer userId);

    /**
     * 根据角色信息获取全部资源并选中哪些是角色有的资源
     *
     * @param roleId 角色id
     * @return 资源树
     */
    List<Menu> listRoleTreeByRoleWithChecked(Integer roleId);

    /**
     * 更新系统管理资源
     *
     * @param resourceUpdateDTO 系统管理资源
     * @return 更新结果
     */
    boolean updateSysManageResource(SysManageResourceUpdateDTO resourceUpdateDTO);

    /**
     * 根据id删除系统管理资源
     *
     * @param id 资源id
     * @return 删除结果
     */
    boolean deleteSysManageSysResource(Integer id);

    /**
     * 根据id更新系统资源的可用状态
     *
     * @param updateStateDTO 系统资源状态
     * @return 更新成功或失败
     */
    boolean updateSysManageResourceState(SysManageResourceUpdateStateDTO updateStateDTO);


    /**
     * 根据权限获取所有菜单
     *
     * @return 菜单
     */
    List<Menu> getAllMenu();

}
