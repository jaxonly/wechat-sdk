package cn.jaxxu.wechat.sdk.service;

import cn.jaxxu.wechat.sdk.model.LoginSignInfoRespEntity;
import cn.jaxxu.wechat.sdk.model.LoginStatusInfoRespEntity;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

@Slf4j
public class WeChatLoginServiceTest {

    private WeChatLoginService weChatLoginService = new WeChatLoginService();

//    @Test
    public void getUuid() {
        String uuid = weChatLoginService.getUuid();
        Assert.assertTrue("数据格式错误, uuid: " + uuid, uuid.endsWith("=="));
    }

//    @Test
    public void getQrcode() {
        String uuid = weChatLoginService.getUuid();
        String qrcodeUrl = weChatLoginService.getQrcode(uuid);
        Assert.assertEquals(qrcodeUrl, "https://login.weixin.qq.com/qrcode/" + uuid);
    }

//    @Test
    public void getQrcodeBytes() {
        weChatLoginService.getQrcodeBytes(weChatLoginService.getUuid());
    }

//    @Test
    public void queryQrcodeLoginStatusInfo() {
        String uuid = weChatLoginService.getUuid();
        String qrcodeUrl = weChatLoginService.getQrcode(uuid);
        LoginStatusInfoRespEntity loginStatusInfoRespEntity = weChatLoginService.queryQrcodeLoginStatusInfo(uuid, false, "0");
        log.info(JSONObject.toJSONString(loginStatusInfoRespEntity));
    }

//    @Test
    public void getSignInfo() {
        String uuid = weChatLoginService.getUuid();
        String qrcodeUrl = weChatLoginService.getQrcode(uuid);
        while (true) {
            LoginStatusInfoRespEntity loginStatusInfoRespEntity = weChatLoginService.queryQrcodeLoginStatusInfo(uuid, false, "0");
            log.info(JSONObject.toJSONString(loginStatusInfoRespEntity));
            if ("200".equals(loginStatusInfoRespEntity.getCode())) {
                LoginSignInfoRespEntity signInfo = weChatLoginService.getSignInfo(loginStatusInfoRespEntity.getRedirectUri());
                log.info("凭证信息, {}", signInfo.toString());
                break;
            } else if ("400".equals(loginStatusInfoRespEntity.getCode())) {
                uuid = weChatLoginService.getUuid();
                qrcodeUrl = weChatLoginService.getQrcode(uuid);
            }
        }
    }
}