package com.yzb.sec.config.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.context.annotation.Configuration;

/**
 * 短信发送工具
 *
 * @author wangban
 */
@Configuration
public class SmsAliUtils implements SmsConfig, ISmsUtils {
    /**
     * 固定产品不需要更改
     */
    private static final String PRODUCT = "Dysmsapi";
    /**
     * 固定服务无需更改
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 短信区域
     */
    public static final String CN_HANGZHOU = "cn-hangzhou";
    public static final String CN_SHANGHAI = "cn-shanghai";
    public static final String CN_BEIJING = "cn-beijing";
    public static final String CN_SHENZHEN = "cn-shenzhen";
    private static final long TIME_OUT = 10000L;
    private static final String DEFAULT_CONNECT_TIMEOUT = "sun.net.client.defaultConnectTimeout";
    private static final String DEFAULT_READ_TIMEOUT = "sun.net.client.defaultReadTimeout";
    /**
     * 短信签名-可在短信控制台中找到
     */
    private static final String SIGN_NAME = "btw";

    private static final String SEND_SUCCESS = "OK";
    /**
     * 国家代码
     */
    private String countryCode;


    /**
     * 身份验证短息模板
     */
    public static final String TEMPLATE_IDENTIFY = "SMS_139855239";
    /**
     * 登录确认
     */
    public static final String TEMPLATE_LOGIN = "SMS_139855238";
    /**
     * 登录异常
     */
    public static final String TEMPLATE_REMOTE_LOGIN = "SMS_139855237";
    /**
     * 用户注册
     */
    public static final String TEMPLATE_REGISTER = "SMS_139855236";
    /**
     * 修改密码
     */
    public static final String TEMPLATE_CHANGE_PASSWORD = "SMS_139855235";
    /**
     * 信息变更
     */
    public static final String TEMPLATE_CHANGE_USER_INFO = "SMS_139855234";
    /**
     * 异地登录（填写登录地址）
     */
    public static final String TEMPLATE_ERROR_ADDRESS_INFO = "SMS_141195797";


    /**
     * 发送短息
     *
     * @param phoneNumber 电话信息
     * @param countryCode 国家代码
     * @param message     验证码
     * @param template    模板
     * @return 发送结果
     */
    @Override
    public boolean sendSms(String phoneNumber, String countryCode, String message, String template) {
        this.countryCode = countryCode;
        switch (template) {
            case TEMPLATE_IDENTIFY:
                return sendSmsIdentity(phoneNumber, message);
            case TEMPLATE_LOGIN:
                return sendSmsLogin(phoneNumber, message);
            case TEMPLATE_REMOTE_LOGIN:
                return sendSmsRemoteLogin(phoneNumber, message);
            case TEMPLATE_REGISTER:
                return sendSmsRegister(phoneNumber, message);
            case TEMPLATE_CHANGE_PASSWORD:
                return sendSmsChangePassword(phoneNumber, message);
            case TEMPLATE_CHANGE_USER_INFO:
                return sendSmsChangeUserInfo(phoneNumber, message);
            case TEMPLATE_ERROR_ADDRESS_INFO:
                return sendSmsErrorMessageInfo(phoneNumber, message);
            default:
                break;
        }
        return false;

    }

    /**
     * 异地登录提示短信
     *
     * @param phoneNumber 电话号码
     * @param code        需要发送的字段，如：地点名称
     * @return 返回短信结果
     */
    private boolean sendSmsErrorMessageInfo(String phoneNumber, String code) {
        return sendSms(phoneNumber, code, TEMPLATE_ERROR_ADDRESS_INFO, null, null, "name");
    }

    /**
     * 身份验证验证码
     *
     * @param phoneNumber 电话号码
     * @param code        需要发送的字段，如：验证码
     * @return 返回短信结果
     */
    private boolean sendSmsIdentity(String phoneNumber, String code) {
        return sendSms(phoneNumber, code, TEMPLATE_IDENTIFY, null, null, null);
    }

    /**
     * 登录确认验证码
     *
     * @param phoneNumber 电话号码
     * @param code        需要发送的字段，如：验证码
     * @return 返回短信结果
     */
    private boolean sendSmsLogin(String phoneNumber, String code) {
        return sendSms(phoneNumber, code, TEMPLATE_LOGIN, null, null, null);
    }

    /**
     * 登录异常验证码
     *
     * @param phoneNumber 电话号码
     * @param code        需要发送的字段，如：验证码
     * @return 返回短信结果
     */
    private boolean sendSmsRemoteLogin(String phoneNumber, String code) {
        return sendSms(phoneNumber, code, TEMPLATE_REMOTE_LOGIN, null, null, null);
    }

    /**
     * 用户注册验证码
     *
     * @param phoneNumber 电话号码
     * @param code        需要发送的字段，如：验证码
     * @return 返回短信结果
     */
    private boolean sendSmsRegister(String phoneNumber, String code) {
        return sendSms(phoneNumber, code, TEMPLATE_REGISTER, null, null, null);
    }

    /**
     * 修改密码验证码
     *
     * @param phoneNumber 电话号码
     * @param code        需要发送的字段，如：验证码
     * @return 返回短信结果
     */
    private boolean sendSmsChangePassword(String phoneNumber, String code) {
        return sendSms(phoneNumber, code, TEMPLATE_CHANGE_PASSWORD, null, null, null);
    }

    /**
     * 信息变更验证码
     *
     * @param phoneNumber 电话号码
     * @param code        需要发送的字段，如：验证码
     * @return 返回短信结果
     */
    private boolean sendSmsChangeUserInfo(String phoneNumber, String code) {
        return sendSms(phoneNumber, code, TEMPLATE_CHANGE_USER_INFO, null, null, null);
    }

    /**
     * 自定义验证码
     *
     * @param phoneNumber 电话号码
     * @param template    短信模板
     * @param code        需要发送的字段，如：验证码
     * @param regionId    区域 如：CN_HANGZHOU，CN_SHANGHAI
     * @param signName    签名文件
     * @return 返回短信结果
     */
    private boolean sendSmsNormal(String phoneNumber, String template, String code, String regionId, String signName) {
        return sendSms(phoneNumber, code, template, regionId, signName, null);
    }


    /**
     * 身份验证验证码
     *
     * @param phoneNumber 电话号码
     * @param code        验证码
     * @return 返回短信结果
     */
    private boolean sendSms(String phoneNumber, String code, String template, String regionId, String signName, String templateType) {
        try {
            System.setProperty(DEFAULT_CONNECT_TIMEOUT, String.valueOf(TIME_OUT));
            System.setProperty(DEFAULT_READ_TIMEOUT, String.valueOf(TIME_OUT));

            //初始化acsClient,暂不支持region化,需要设置
            if (regionId == null) {
                regionId = CN_HANGZHOU;
            }
            IClientProfile profile = DefaultProfile.getProfile(regionId, ACCESSKEY_ID, ACCESSKEY_SECRET);
            DefaultProfile.addEndpoint(regionId, regionId, PRODUCT, DOMAIN);

            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            if (this.countryCode != null) {
                phoneNumber = "00" + this.countryCode + phoneNumber;
            } else {
                phoneNumber = "00" + "86" + phoneNumber;
            }
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            if (signName != null) {
                request.setSignName(signName);
            } else {
                request.setSignName(SMS_SIGN_NAME);
            }
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(template);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            if (templateType == null) {
                request.setTemplateParam("{\"code\":\"" + code + "\"}");
            } else {
                request.setTemplateParam("{\"" + templateType + "\":\"" + code + "\"}");
            }
            SendSmsResponse response = acsClient.getAcsResponse(request);
            if (response.getCode() != null && response.getCode().equals(SEND_SUCCESS)) {
                return true;
            }
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }


}
