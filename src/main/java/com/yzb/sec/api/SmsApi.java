package com.yzb.sec.api;

import com.yzb.sec.domain.dto.SmsMessageDTO;
import com.yzb.sec.domain.orm.SysManageUser;
import com.yzb.sec.domain.result.MessageKey;
import com.yzb.sec.domain.result.ResultJson;
import com.yzb.sec.service.ifac.SysManageUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yzb.sec.domain.constant.MessageConstant.SYSTEM_SMS_MANAGE_LOGIN_CODE_ACCOUNT;

/**
 * @author wangban
 * @data 2019/9/11 16:14
 */
@RestController


@RequestMapping("v1/manage/sms")
public class SmsApi extends BaseApi {

    @Autowired
    private SysManageUserService manageUserService;

    @ApiOperation(value = "发送获取登录短信")
    @PostMapping(value = "login")
    public ResultJson<String> loginSms(String account) {
        logger.info("account {} ", account);
        SysManageUser user = manageUserService.getSysMangeUserMessageByAccount(account);
        if (user == null) {
            return ResultJson.createByErrorMsg(MessageKey.CORRECT_PARAMS);
        }
        SmsMessageDTO messageDTO = new SmsMessageDTO();
        messageDTO.setCountryCode("86");
        messageDTO.setPhoneNumber(user.getMobile());
        String key = SYSTEM_SMS_MANAGE_LOGIN_CODE_ACCOUNT + account;
        return getStringResultJson(messageDTO, key, account);
    }

}
