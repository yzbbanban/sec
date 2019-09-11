package com.yzb.sec.common.util;

import com.yzb.sec.domain.orm.SysAuthUser;
import com.yzb.sec.domain.orm.SysAuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by sang on 2017/12/30.
 */
public class SysManageUserUtils {
    public static SysAuthUser getCurrentHr() {
        return (SysAuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
