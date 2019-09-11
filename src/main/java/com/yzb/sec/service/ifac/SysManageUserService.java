package com.yzb.sec.service.ifac;


import com.yzb.sec.domain.dto.SysManageUserChangePwdConvertDTO;
import com.yzb.sec.domain.dto.SysManageUserLockDTO;
import com.yzb.sec.domain.dto.SysManageUserUpdateDTO;
import com.yzb.sec.domain.orm.PageParamDTO;
import com.yzb.sec.domain.orm.SysManageUser;
import com.yzb.sec.domain.vo.SysManageUserVO;

import java.util.List;

/**
 * 系统用户相关业务
 *
 * @author b
 */
public interface SysManageUserService {

    /**
     * 根据登录名获取用户系统信息
     *
     * @param account 系统用户登录名
     * @return 系统用户信息
     */
    SysManageUser getSysMangeUserMessageByAccount(String account);

    /**
     * 根据系统用户id获取用户系统信息
     *
     * @param id 系统用户id
     * @return 系统用户信息
     */
    SysManageUser getSysMangeUserMessageById(Integer id);

    /**
     * 根据系统用户id获取用户系统视图信息
     *
     * @param id 系统用户id
     * @return 系统用户信息
     */
    SysManageUserVO getSysMangeUserVOMessageById(Integer id);

    /**
     * 获取系统用户列表
     *
     * @param pageParamsDTO 分页参数
     * @return 系统用户列表
     */
    List<SysManageUserVO> listSysMangeUserMessage(PageParamDTO pageParamsDTO);

    /**
     * 获取系统管理用户数量
     *
     * @return 管理用户数量
     */
    int getSysMangeUserCount();

    /**
     * 判断是否存在此手机号
     *
     * @param mobile 电话号码
     * @return 查询结果 true 存在，false 不存在
     */
    boolean getMobileInfo(String mobile);


    /**
     * 获取用户信息
     *
     * @param mobile 顶阿虎
     * @return 用户信息
     */
    SysManageUser getUserInfo(String mobile);


    /**
     * 保存系统管理用户信息
     *
     * @param sysManageUser 系统管理用户信息
     * @return true保存成功，false 保存失败
     */
    boolean saveSysManageUser(SysManageUser sysManageUser);


    /**
     * 更新管理用户信息
     *
     * @param sysManageUserUpdateDTO 系统用户更新信息
     * @return 更新结果
     */
    boolean updateManageUser(SysManageUserUpdateDTO sysManageUserUpdateDTO);

    /**
     * 锁定管理用户
     *
     * @param sysManageUserLockDTO 系统用户锁定信息
     * @return 锁定结果
     */
    boolean lockManageUser(SysManageUserLockDTO sysManageUserLockDTO);


    /**
     * 更新管理用户信息密码
     *
     * @param userChangePwdConvertDTO 系统用户更新密码信息
     * @return 锁定结果
     */
    boolean changeManageUserPwd(SysManageUserChangePwdConvertDTO userChangePwdConvertDTO);
}
