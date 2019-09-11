package com.yzb.sec.service.impl;

import com.yzb.sec.dao.SysManageUserDao;
import com.yzb.sec.domain.orm.SysManageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 系统用户业务
 *
 * @author wangban
 * @data 2019/9/2 17:14
 */
@Service
public class SysMangeServiceImpl extends BaseImpl implements UserDetailsService {


    @Autowired
    private SysManageUserDao manageUserDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String code = getHttpParam("code").get(0);
        logger.info("===code===>" + code);
        logger.info("===s===>" + s);
        SysManageUser sysManageUser = manageUserDao.loadUserByUsername(s);
        if (sysManageUser == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return sysManageUser;
    }

    /**
     * 注册
     *
     * @param username 名称
     * @param password 密码
     * @return 结果
     */
    public int mangeReg(String username, String password) {
        //如果用户名存在，返回错误
        if (manageUserDao.loadUserByUsername(username) != null) {
            return -1;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        return manageUserDao.mangeReg(username, encode);
    }
}
