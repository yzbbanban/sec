package com.yzb.sec.config.sms;

/**
 * 发送短信接口
 *
 * @author wangban
 * @date 18:45 2018/8/3
 */
public interface ISmsUtils {
    /**
     * 发送短信
     *
     * @param phoneNumber 电话号码
     * @param countryCode 国家代码
     * @param message     信息
     * @param template    模板
     * @return 发送短信成功失败
     */
    boolean sendSms(String phoneNumber, String countryCode, String message, String template);
}
