package com.yzb.sec.config.sms;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import com.yunpian.sdk.model.Template;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * 云片短信
 *
 * @author wangban
 * @date 19:52 2018/8/3
 */
public class SmsYpUtils implements SmsConfig, ISmsUtils {

    private final Logger logger = LoggerFactory.getLogger(SmsYpUtils.class);

    /**
     * 通用验证码
     */
    public static final String SMS_YP = "2711532";

    /**
     * 国际验证码
     */
    public static final String SMS_INT = "2556214";


    /**
     * 编码格式。发送编码格式统一用UTF-8
     */
    private static String ENCODING = "UTF-8";

    /**
     * 国内国家代码
     */
    public static final String COUNTRY_SIDE = "86";

    /**
     * 发送短信
     *
     * @param phoneNumber 电话号码
     * @param countryCode 国家代码
     * @param message     信息
     * @param template    模板
     * @return 发送短信结果
     */
    @Override
    public boolean sendSms(String phoneNumber, String countryCode, String message, String template) {
        boolean result = false;
        try {
            if (COUNTRY_SIDE.equals(countryCode)) {
                //国内短信
                phoneNumber = "+" + countryCode + phoneNumber;
                result = sendCountryside(phoneNumber, message, template);
            } else {
                //国际短息
                phoneNumber = "+" + countryCode + phoneNumber;
                result = sendCountryside(phoneNumber, message, SMS_INT);

            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 国内短信
     *
     * @param phoneNumber 电话号码
     * @param message     信息
     * @param template    模板
     * @return 返回结果
     */
    private boolean sendCountryside(String phoneNumber, String message, String template) {
        YunpianClient clnt = new YunpianClient(API_KEY).init();
        //获取模板
        Map<String, String> params2 = clnt.newParam(1);
        //发送的模版id
        params2.put(YunpianClient.TPL_ID, template);
        Result<List<Template>> res = clnt.tpl().get(params2);
        //获取模板内容
        if (res == null) {
            return false;
        }
        if (res.getData() == null || res.getData().size() <= 0) {
            return false;
        }
        String temp = res.getData().get(0).getTpl_content();
        if (StringUtils.isBlank(temp)) {
            return false;
        }

        Map<String, String> params = clnt.newParam(3);
        params.put(YunpianClient.MOBILE, phoneNumber);
        //替换# #之间的数据
        String regex = "(\\#.*?\\#)";
        temp = temp.replaceAll(regex, message);
        temp = temp.replaceAll("#", "");

        params.put(YunpianClient.TEXT, temp);
        Result<SmsSingleSend> r = clnt.sms().single_send(params);
        if (r == null) {
            logger.error("sendSms 发送失败---->" + message + "--->" + phoneNumber + ":");
            return false;
        }
        if (r.getData() == null) {
            logger.error("sendSms 发送失败---->" + message + "--->" + phoneNumber + ":" + r.toString());
            return false;
        }
        if (r.getData().getCode() == 0) {
            return true;
        }
        logger.error("sendSms 发送失败---->" + message + "--->" + phoneNumber + ":" + r.toString());
        return false;
    }


}
