package com.yzb.sec.api;

import com.yzb.sec.common.util.RandomUtils;
import com.yzb.sec.config.cache.LocalCache;
import com.yzb.sec.config.redis.RedisClusterClient;
import com.yzb.sec.config.sms.ISmsUtils;
import com.yzb.sec.config.sms.SmsYpUtils;
import com.yzb.sec.domain.dto.SmsMessageDTO;
import com.yzb.sec.domain.result.MessageKey;
import com.yzb.sec.domain.result.ResultJson;
import com.yzb.sec.domain.result.ResultStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.yzb.sec.domain.constant.MessageConstant.SMS_REPEAT;

/**
 * base
 *
 * @author wangban
 * @data 2019/9/11 10:55
 */
public class BaseApi {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    protected LocalCache localCache;
    @Autowired
    protected RedisClusterClient redisClient;

    /**
     * 发送短信
     *
     * @param messageDTO 短信内容
     * @param key        短信 key
     * @return 发送结果
     */
    protected ResultJson<String> getStringResultJson(SmsMessageDTO messageDTO, String key, String account) {
        ISmsUtils sms = new SmsYpUtils();
        int codeLength = 6;
        ResultJson<String> resultJson = new ResultJson<>();
        String code = RandomUtils.generateMixNum(codeLength);

        if (isCodeRepeat(resultJson, account)) {
            return resultJson;
        }
        boolean result = sms.sendSms(messageDTO.getPhoneNumber(), messageDTO.getCountryCode(), code, SmsYpUtils.SMS_YP);

        if (result) {
//            localCache.setCache(key, code);
//            localCache.setCache(SMS_REPEAT + account, code);
            redisClient.set(key, code, 90);
            redisClient.set(SMS_REPEAT + account, code, 60);
            return ResultJson.createBySuccess();
        }
        return ResultJson.createByError();
    }

    /**
     * 是否重复发送
     *
     * @param resultJson 接口返回值
     * @param account    用户
     * @return 是否正确
     */
    protected boolean isCodeRepeat(ResultJson resultJson, String account) {
//        String smsCode = localCache.getCache(SMS_REPEAT + account);
        String smsCode = redisClient.get(SMS_REPEAT + account);
        if (StringUtils.isBlank(smsCode)) {
            resultJson.setStatus(ResultStatus.OK);
            return false;
        }
        resultJson.setStatus(ResultStatus.ERROR);
        resultJson.setMessage(MessageKey.CODE_REPEAT);
        return true;
    }


}
