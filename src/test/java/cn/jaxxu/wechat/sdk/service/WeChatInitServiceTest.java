package cn.jaxxu.wechat.sdk.service;

import cn.jaxxu.wechat.sdk.model.BaseRequest;
import cn.jaxxu.wechat.sdk.model.InitInfoRespEntity;
import cn.jaxxu.wechat.sdk.model.LoginSignInfoRespEntity;
import cn.jaxxu.wechat.sdk.model.LoginStatusInfoRespEntity;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeChatInitServiceTest {

    private WeChatLoginService weChatLoginService = new WeChatLoginService();

    private WeChatInitService weChatInitService = new WeChatInitService();

//    @Test
    public void getInitInfo() {
        String uuid = weChatLoginService.getUuid();
        String qrcodeUrl = weChatLoginService.getQrcode(uuid);
        while (true) {
            LoginStatusInfoRespEntity loginStatusInfoRespEntity = weChatLoginService.queryQrcodeLoginStatusInfo(uuid, false, "0");
            log.info(JSONObject.toJSONString(loginStatusInfoRespEntity));
            if ("200".equals(loginStatusInfoRespEntity.getCode())) {
                LoginSignInfoRespEntity signInfo = weChatLoginService.getSignInfo(loginStatusInfoRespEntity.getRedirectUri());
                log.info("凭证信息, {}", signInfo.toString());

                BaseRequest baseRequest = BaseRequest.builder()
                        .sid(signInfo.getSid())
                        .skey(signInfo.getSkey())
                        .uin(signInfo.getUin())
                        .deviceId("e082719690266562")
                        .build();
                InitInfoRespEntity initInfo = weChatInitService.getInitInfo(baseRequest, signInfo.getPassTicket());

                log.info("初始化信息, {}", initInfo.toString());
                break;
            } else if ("400".equals(loginStatusInfoRespEntity.getCode())) {
                uuid = weChatLoginService.getUuid();
                qrcodeUrl = weChatLoginService.getQrcode(uuid);
            }
        }
    }
}