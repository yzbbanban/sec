package com.yzb.sec.config.common.util;

import com.yzb.sec.domain.orm.SysManageUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by sang on 2017/12/30.
 */
public class SysManageUserUtils {
    public static SysManageUser getCurrentHr() {
        return (SysManageUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
