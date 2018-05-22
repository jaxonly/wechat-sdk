package cn.jaxxu.wechat.sdk.service;

import cn.jaxxu.wechat.sdk.SysConstant;
import cn.jaxxu.wechat.sdk.model.BaseRequest;
import cn.jaxxu.wechat.sdk.model.InitInfoRespEntity;
import cn.jaxxu.wechat.sdk.util.OkHttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import static cn.jaxxu.wechat.sdk.constant.RequestType.REQUEST_BODY;

@Slf4j
public class WeChatInitService {

    /**
     * 获取微信初始化信息
     *
     * @param baseRequest 基础请求信息
     * @param passTicket  passTicket
     * @return 微信初始化信息
     */
    @SneakyThrows
    public InitInfoRespEntity getInitInfo(BaseRequest baseRequest, String passTicket) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit").newBuilder();
        urlBuilder.addQueryParameter("r", String.valueOf(System.currentTimeMillis()));
        urlBuilder.addQueryParameter("lang", SysConstant.LANG);
        urlBuilder.addQueryParameter("_", String.valueOf(SysConstant.timestamp++));
        urlBuilder.addQueryParameter("pass_ticket", passTicket);
        String url = urlBuilder.build().toString();
        if (log.isDebugEnabled()) {
            log.debug("获取微信初始化信息, url: {}", url);
        }
        JSONObject postParam = new JSONObject();
        postParam.put("BaseRequest", baseRequest);

        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(REQUEST_BODY, postParam.toJSONString()))
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("获取微信初始化信息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();
        return JSONObject.parseObject(responseBody, InitInfoRespEntity.class);
    }
}
