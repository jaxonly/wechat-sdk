package cn.jaxxu.wechat.sdk.service;

import cn.jaxxu.wechat.sdk.SysConstant;
import cn.jaxxu.wechat.sdk.model.LoginStatusInfoRespEntity;
import cn.jaxxu.wechat.sdk.model.SyncCheckRespEntity;
import cn.jaxxu.wechat.sdk.model.SyncMsgRespEntity;
import cn.jaxxu.wechat.sdk.util.ConfigStoreUtil;
import cn.jaxxu.wechat.sdk.util.OkHttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.Map;

import static cn.jaxxu.wechat.sdk.constant.RequestType.REQUEST_BODY;

/**
 * 微信信息接收服务
 */
@Slf4j
public class WeChatRecvMessageService {

    @SneakyThrows
    public SyncCheckRespEntity syncCheck() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://webpush.wx2.qq.com/cgi-bin/mmwebwx-bin/synccheck").newBuilder();
        urlBuilder.addQueryParameter("r", String.valueOf(System.currentTimeMillis()));
        urlBuilder.addQueryParameter("_", String.valueOf(SysConstant.timestamp++));
        urlBuilder.addQueryParameter("skey", ConfigStoreUtil.signInfo.getSkey());
        urlBuilder.addQueryParameter("sid", ConfigStoreUtil.signInfo.getSid());
        urlBuilder.addQueryParameter("uin", ConfigStoreUtil.signInfo.getUin());
        urlBuilder.addQueryParameter("deviceid", ConfigStoreUtil.deviceId);
        urlBuilder.addQueryParameter("synckey", ConfigStoreUtil.initInfoRespEntity.getSyncKey().listStr());
        String url = urlBuilder.build().toString();
        if (log.isDebugEnabled()) {
            log.debug("轮询微信消息, url: {}", url);
        }
        OkHttpClient client = OkHttpClientUtil.getClent(30);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("轮询微信消息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();
        String substring = responseBody.substring(17);
        return JSONObject.parseObject(substring, SyncCheckRespEntity.class);
    }

    @SneakyThrows
    public SyncMsgRespEntity syncMsg() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsync").newBuilder();
        urlBuilder.addQueryParameter("skey", ConfigStoreUtil.signInfo.getSkey());
        urlBuilder.addQueryParameter("sid", ConfigStoreUtil.signInfo.getSid());
        urlBuilder.addQueryParameter("lang", SysConstant.LANG);
        urlBuilder.addQueryParameter("pass_ticket", ConfigStoreUtil.signInfo.getPassTicket());
        String url = urlBuilder.build().toString();
        if (log.isDebugEnabled()) {
            log.debug("获取微信新消息, url: {}", url);
        }
        OkHttpClient client = OkHttpClientUtil.getClent(30);
        JSONObject postParam = new JSONObject();
        postParam.put("BaseRequest", ConfigStoreUtil.baseRequestUinInt);
        postParam.put("SyncKey", ConfigStoreUtil.initInfoRespEntity.getSyncKey());
        postParam.put("rr", ~System.currentTimeMillis());
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(REQUEST_BODY, postParam.toJSONString()))
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("获取微信新消息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();
        return JSONObject.parseObject(responseBody, SyncMsgRespEntity.class);
    }
}
