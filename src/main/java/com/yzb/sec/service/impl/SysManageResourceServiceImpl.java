package com.yzb.sec.service.impl;


import com.yzb.sec.dao.SysManageResourceDao;
import com.yzb.sec.dao.SysResourceRoleRelationDao;
import com.yzb.sec.dao.SysUserRoleRelationDao;
import com.yzb.sec.domain.dto.SysManageResourceAddChildrenDTO;
import com.yzb.sec.domain.dto.SysManageResourceAddParentDTO;
import com.yzb.sec.domain.dto.SysManageResourceUpdateDTO;
import com.yzb.sec.domain.dto.SysManageResourceUpdateStateDTO;
import com.yzb.sec.domain.orm.SysResourceRoleRelation;
import com.yzb.sec.domain.vo.Menu;
import com.yzb.sec.service.ISysManageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 系统管理资源业务
 *
 * @author wangban
 * @date 15:17 2018/7/30
 */
@Service
public class SysManageResourceServiceImpl implements ISysManageResourceService {

    @Autowired
    private SysManageResourceDao sysManageResourceDao;

    @Autowired
    private SysResourceRoleRelationDao resourceRoleRelationDao;

    @Autowired
    private SysUserRoleRelationDao sysUserRoleRelationDao;

    /**
     * 添加父节点系统管理资源
     *
     * @param resourceAddParentDTO 系统管理资源信息
     * @return 添加结果>0保存成功
     */
    @Override
    public boolean addSysManageParentResource(SysManageResourceAddParentDTO resourceAddParentDTO) {
        return sysManageResourceDao.saveSysManageParentResource(resourceAddParentDTO) > 0;
    }

    /**
     * 添加子节点系统管理资源
     *
     * @param resourceAddChildrenDTO 系统管理资源信息
     * @return 添加结果>0保存成功
     */
    @Override
    public boolean addSysManageChildrenResource(SysManageResourceAddChildrenDTO resourceAddChildrenDTO) {
        return sysManageResourceDao.saveSysManageChildrenResource(resourceAddChildrenDTO) > 0;
    }


    /**
     * 更新系统管理资源
     *
     * @param resourceUpdateDTO 系统管理资源
     * @return 更新结果
     */
    @Override
    public boolean updateSysManageResource(SysManageResourceUpdateDTO resourceUpdateDTO) {
        return sysManageResourceDao.updateSysManageResource(resourceUpdateDTO) > 0;
    }

    /**
     * 根据id删除系统资源
     *
     * @param id 资源id
     * @return 删除结果
     */
    @Override
    public boolean deleteSysManageSysResource(Integer id) {
        if (sysManageResourceDao.getSysManageResourceById(id) == null) {
            return false;
        }
        return sysManageResourceDao.deleteSysManageResource(id) > 0;
    }

    /**
     * 更新系统资源状态
     *
     * @param updateStateDTO 系统资源状态
     * @return 更新状态结果
     */
    @Override
    public boolean updateSysManageResourceState(SysManageResourceUpdateStateDTO updateStateDTO) {
        return sysManageResourceDao.updateSysManageResourceState(updateStateDTO) > 0;
    }

    /**
     * 根据角色信息获取资源
     *
     * @param userId 用户id
     * @return 资源树
     */
    @Override
    public List<Menu> listRoleTreeByRole(Integer userId) {
        Integer roleId = sysUserRoleRelationDao.getRoleIdByUserId(userId);
        if (roleId == null) {
            return null;
        }
        List<Menu> menus = sysManageResourceDao.getAllMenuWithRole(0, roleId);
        menus.forEach(menu -> {
            getRoleChildMenu(roleId, menu, menus);
        });
        return menus;
    }

    /**
     * 根据角色信息获取全部资源并选中哪些是角色有的资源
     *
     * @param roleId 角色id
     * @return 资源树
     */
    @Override
    public List<Menu> listRoleTreeByRoleWithChecked(Integer roleId) {
        List<Menu> menus = sysManageResourceDao.getAllMenu(0);
        menus.forEach(menu -> {
            getCheckRoleChildMenu(roleId, menu, menus);
        });
        return menus;
    }

    /**
     * 根据权限获取所有菜单
     *
     * @return 菜单
     */
    @Override
    public List<Menu> getAllMenu() {
        List<Menu> menus = sysManageResourceDao.getAllMenu(0);
        menus.forEach(menu -> {
            getChildMenu(menu, menus);
        });
        return menus;
    }

    /**
     * 获取所有的节点
     *
     * @param menu  父节点
     * @param menus 节点
     */
    private List<Menu> getChildMenu(Menu menu, List<Menu> menus) {
        //获取父节点的子节点数据
        List<Menu> childMenus = sysManageResourceDao.getAllMenu(menu.getId());
        if (!CollectionUtils.isEmpty(childMenus)) {
            menu.setChildren(childMenus);
            //用于保存子节点
            //遍历子节点，获取子节点为父节点的子节点
            for (Menu m : childMenus) {
                getChildMenu(m, menus);
            }
        }
        return menus;

    }

    /**
     * 根据角色获取所有节点
     *
     * @param menu  父节点
     * @param menus 节点
     */
    private List<Menu> getRoleChildMenu(Integer roleId, Menu menu, List<Menu> menus) {
        //获取父节点的子节点数据
        List<Menu> childMenus = sysManageResourceDao.getAllMenu(menu.getId());
        if (!CollectionUtils.isEmpty(childMenus)) {
            SysResourceRoleRelation rr = new SysResourceRoleRelation();
            rr.setSysResourceId(roleId);
            rr.setSysRoleId(menu.getId());
            if (resourceRoleRelationDao.getResourceRole(rr) > 0) {
                menu.setChildren(childMenus);
            }
            //用于保存子节点
            //遍历子节点，获取子节点为父节点的子节点
            for (Menu m : childMenus) {
                getChildMenu(m, menus);
            }
        }
        return menus;

    }


    /**
     * 选择角色节点
     *
     * @param roleId 角色id
     * @param menu   父节点
     * @param menus  节点
     */
    private List<Menu> getCheckRoleChildMenu(Integer roleId, Menu menu, List<Menu> menus) {
        //获取父节点的子节点数据
        List<Menu> childMenus = sysManageResourceDao.getAllMenu(menu.getId());
        if (!CollectionUtils.isEmpty(childMenus)) {
            //用于保存子节点
            //遍历子节点，获取子节点为父节点的子节点
            for (Menu m : childMenus) {
                SysResourceRoleRelation rr = new SysResourceRoleRelation();
                rr.setSysResourceId(m.getId());
                rr.setSysRoleId(roleId);
                if (resourceRoleRelationDao.getResourceRole(rr) > 0) {
                    m.setChecked(true);
                } else {
                    m.setChecked(false);
                }
                getCheckRoleChildMenu(roleId, m, menus);
            }
            menu.setChildren(childMenus);

        }
        return menus;

    }

}
