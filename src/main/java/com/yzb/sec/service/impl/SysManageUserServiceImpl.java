package com.yzb.sec.service.impl;


import com.yzb.sec.common.util.PageParamUtil;
import com.yzb.sec.dao.SysManageUserDao;
import com.yzb.sec.dao.SysUserRoleRelationDao;
import com.yzb.sec.domain.dto.SysManageUserChangePwdConvertDTO;
import com.yzb.sec.domain.dto.SysManageUserLockDTO;
import com.yzb.sec.domain.dto.SysManageUserUpdateDTO;
import com.yzb.sec.domain.orm.PageParamDTO;
import com.yzb.sec.domain.orm.SysManageUser;
import com.yzb.sec.domain.orm.SysUserRoleRelation;
import com.yzb.sec.domain.vo.SysManageUserVO;
import com.yzb.sec.service.ifac.SysManageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统用户管理服务
 *
 * @author wangban
 */
@Service
public class SysManageUserServiceImpl implements SysManageUserService {
    /**
     * 系统管理管理dao
     */
    @Autowired
    private SysManageUserDao sysManageUserDao;
    /**
     * 系统用户管理用户与角色关系
     */
    @Autowired
    private SysUserRoleRelationDao sysUserRoleRelationDao;

    /**
     * 获取系统管理用户信息
     *
     * @param account 登录名
     * @return 系统管理用户信息
     */
    @Override
    public SysManageUser getSysMangeUserMessageByAccount(String account) {
        return sysManageUserDao.getSysManageUserByAccount(account);
    }

    /**
     * 根据系统用户id获取系统用户信息
     *
     * @param id 系统用户id
     * @return 系统管理用户信息
     */
    @Override
    public SysManageUser getSysMangeUserMessageById(Integer id) {
        return sysManageUserDao.getSysManageUserById(id);
    }

    /**
     * 根据系统用户id获取用户系统视图信息
     *
     * @param id 系统用户id
     * @return 系统用户信息
     */
    @Override
    public SysManageUserVO getSysMangeUserVOMessageById(Integer id) {
        return sysManageUserDao.getSysManageUserVOById(id);
    }

    /**
     * 获取系统用户列表
     *
     * @param pageParamsDTO 分页参数
     * @return 系统用户列表
     */
    @Override
    public List<SysManageUserVO> listSysMangeUserMessage(PageParamDTO pageParamsDTO) {
        return sysManageUserDao.listSysManageUser(PageParamUtil.setPageParam(pageParamsDTO));
    }

    /**
     * 获取系统管理用户数量
     *
     * @return 系统管理用户数量
     */
    @Override
    public int getSysMangeUserCount() {
        return sysManageUserDao.getSysManageUserCount();
    }


    /**
     * 判断是否存在此手机号
     *
     * @param mobile 电话号码
     * @return true 存在
     */
    @Override
    public boolean getMobileInfo(String mobile) {
        return sysManageUserDao.getSysManageUserByPhone(mobile) > 0;
    }

    @Override
    public SysManageUser getUserInfo(String mobile) {
        return sysManageUserDao.getSysManageUser(mobile);
    }

    /**
     * 保存系统管理用户信息
     *
     * @param sysManageUser 系统管理用户信息
     * @return true保存成功，false 保存失败
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public boolean saveSysManageUser(SysManageUser sysManageUser) {
        int result = sysManageUserDao.saveSysManageUser(sysManageUser);
        if (result > 0) {
            SysManageUser manageUser = sysManageUserDao.getSysManageUserByAccount(sysManageUser.getAccount());
            if (manageUser != null) {
                int id = manageUser.getId();
                SysUserRoleRelation sysUserRoleRelation = new SysUserRoleRelation();
                sysUserRoleRelation.setSysRoleId(1);
                sysUserRoleRelation.setSysUserId(id);
                int res = sysUserRoleRelationDao.saveUserRole(sysUserRoleRelation);
                if (res > 0) {
                    return true;
                } else {
                    throw new ServiceException("失败创建");
                }
            }
        }
        return false;
    }


    /**
     * 更新系统管理用户
     *
     * @param sysManageUserUpdateDTO 系统用户更新信息
     * @return 更新结果
     */
    @Override
    public boolean updateManageUser(SysManageUserUpdateDTO sysManageUserUpdateDTO) {
        return sysManageUserDao.updateSysManageUser(sysManageUserUpdateDTO) > 0;
    }

    /**
     * 锁定系统用户
     *
     * @param sysManageUserLockDTO 系统用户锁定信息
     * @return 锁定结果
     */
    @Override
    public boolean lockManageUser(SysManageUserLockDTO sysManageUserLockDTO) {
        return sysManageUserDao.updateSysManageUserLockInfo(sysManageUserLockDTO) > 0;
    }

    /**
     * 修改系统管理用户密码
     *
     * @param userChangePwdConvertDTO 系统用户更新密码信息
     * @return 修改结果
     */
    @Override
    public boolean changeManageUserPwd(SysManageUserChangePwdConvertDTO userChangePwdConvertDTO) {
        return sysManageUserDao.updateSysManageUserPwd(userChangePwdConvertDTO) > 0;
    }
}
