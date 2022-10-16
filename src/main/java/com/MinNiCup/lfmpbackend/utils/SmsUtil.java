package com.MinNiCup.lfmpbackend.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SmsUtil {

    private static final String SecretId = "";
    private static final String SecretKey = "";

    /**
     * 短信 SdkAppId，在 [短信控制台](<a href="https://console.cloud.tencent.com/smsv2/app-manage">...</a>)  添加应用后生成的实际 SdkAppId，示例如1400006666。
     */
    private static final String SmsSdkAppId = "1400735512";

    /**
     * 模板 ID，必须填写已审核通过的模板 ID。模板 ID 可前往 [国内短信](<a href="<a">href="https://console.cloud.tencent.com/smsv2/csms</a>-template">...</a>) 或 [国际/港澳台短信](<a href="https://console.cloud.tencent.com/smsv2/isms-template">...</a>) 的正文模板管理查看，若向境外手机号发送短信，仅支持使用国际/港澳台短信模板。
     */
    private static final String TemplateId = "1540553";

    /**
     * 短信签名内容，使用 UTF-8 编码，必须填写已审核通过的签名，例如：腾讯云，签名信息可前往 [国内短信](<a href="https://console.cloud.tencent.com/smsv2/csms-sign">...</a>) 或 [国际/港澳台短信](<a href="https://console.cloud.tencent.com/smsv2/isms-sign">...</a>) 的签名管理查看。
     <dx-alert infotype="notice" title="注意">发送国内短信该参数必填。</dx-alert>
     */
    private static final String SignName = "健康快乐学习每一天网";

    /**
     * templateParamSet
     * 模板参数，若无模板参数，则设置为空。
     <dx-alert infotype="notice" title="注意">模板参数的个数需要与 TemplateId 对应模板的变量个数保持一致。</dx-alert>
     */

    // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey，见《创建secretId和secretKey》小节
    private static final Credential cred = new Credential(SecretId, SecretKey);


    public static String sendSms(String phone, String[] templateParamSet) {


        // 实例化要请求产品(以cvm为例)的client对象
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);
        //第二个ap-chongqing 填产品所在的区
        SmsClient smsClient = new SmsClient(cred, "ap-beijing");
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        //appId ,见《创建应用》小节
        sendSmsRequest.setSmsSdkAppid(SmsSdkAppId);
        //发送短信的目标手机号，可填多个。
        String[] phones={"+86"+phone};
        sendSmsRequest.setPhoneNumberSet(phones);

        //模版id,见《创建短信签名和模版》小节
        sendSmsRequest.setTemplateID(TemplateId);

        //模版参数，从前往后对应的是模版的{1}、{2}等,见《创建短信签名和模版》小节

        sendSmsRequest.setTemplateParamSet(templateParamSet);

        //签名内容，不是填签名id,见《创建短信签名和模版》小节
        sendSmsRequest.setSign(SignName);
        try {
            //发送短信
            SendSmsResponse sendSmsResponse= smsClient.SendSms(sendSmsRequest);
            return sendSmsResponse.getSendStatusSet()[0].getCode();
//            if (Objects.equals(code, "Ok")) {
//                log.info("发送手机短信成功");
//            }
//            else {
//                log.warn("发送手机短信失败");
//            }
        } catch (TencentCloudSDKException e) {
            log.warn("发送手机短信失败");
            e.printStackTrace();
        }
        return null;

    }

}
