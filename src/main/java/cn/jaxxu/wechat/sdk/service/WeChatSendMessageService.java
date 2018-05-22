package cn.jaxxu.wechat.sdk.service;

import cn.jaxxu.wechat.sdk.constant.MsgType;
import cn.jaxxu.wechat.sdk.model.Message;
import cn.jaxxu.wechat.sdk.model.Msg;
import cn.jaxxu.wechat.sdk.model.SendMessageRespEntity;
import cn.jaxxu.wechat.sdk.util.ConfigStoreUtil;
import cn.jaxxu.wechat.sdk.util.OkHttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;

import static cn.jaxxu.wechat.sdk.constant.RequestType.REQUEST_BODY;

/**
 * 微信信息发送服务
 */
@Slf4j
public class WeChatSendMessageService {

    private WeChatMediaUploadService weChatMediaUploadService = new WeChatMediaUploadService();

    @SneakyThrows
    public boolean sendMessage(String message, String toUserName) {
        long localId = System.currentTimeMillis() + (int) ((Math.random() * 9 + 1) * 1000);
        Message messageEntity = Message.builder()
                .baseRequest(ConfigStoreUtil.baseRequestUinInt)
                .msg(
                        Msg.builder()
                                .type(MsgType.TEXT)
                                .toUserName(toUserName)
                                .localID(localId)
                                .clientMsgId(localId)
                                .fromUserName(ConfigStoreUtil.initInfoRespEntity.getUser().getUserName())
                                .content(message)
                                .build()
                )
                .build();

        String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg";
        if (log.isDebugEnabled()) {
            log.debug("发送文字信息, url: {}", url);
        }

        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(REQUEST_BODY, JSONObject.toJSONString(messageEntity)))
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("发送文字信息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();

        SendMessageRespEntity sendMessageRespEntity = JSONObject.parseObject(responseBody, SendMessageRespEntity.class);
        return sendMessageRespEntity.getBaseResponse().isSuccess();
    }

    @SneakyThrows
    public boolean sendFile(File file, String toUserName) {
        String name = file.getName();
        String[] split = name.split("\\.");
        String fileName, fileSuffixName;
        if (split.length == 1) {
            fileName = name;
            fileSuffixName = "";
        }else {
            fileName = name.substring(0, name.lastIndexOf("."));
            fileSuffixName = split[split.length - 1];
        }
        long localId = System.currentTimeMillis() + (int) ((Math.random() * 9 + 1) * 1000);
        Message messageEntity = Message.builder()
                .baseRequest(ConfigStoreUtil.baseRequestUinInt)
                .msg(
                        Msg.builder()
                                .type(MsgType.FILE)
                                .toUserName(toUserName)
                                .localID(localId)
                                .clientMsgId(localId)
                                .fromUserName(ConfigStoreUtil.initInfoRespEntity.getUser().getUserName())
                                .content("<appmsg appid='wxeb7ec651dd0aefa9' sdkver=''>" +
                                        "<title>" + fileName + "</title>" +
                                        "<des></des>" +
                                        "<action></action>" +
                                        "<type>6</type>" +
                                        "<content></content>" +
                                        "<url></url>" +
                                        "<lowurl></lowurl>" +
                                        "<appattach>" +
                                        "<totallen>" + file.length() + "</totallen>" +
                                        "<attachid>" + weChatMediaUploadService.uplaodDoc(file, toUserName).getMediaId() + "</attachid>" +
                                        "<fileext>" + fileSuffixName + "</fileext>" +
                                        "</appattach>" +
                                        "<extinfo></extinfo>" +
                                        "</appmsg>")
                                .build()
                )
                .build();

        String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendappmsg?fun=async&f=json";
        if (log.isDebugEnabled()) {
            log.debug("发送文件信息, url: {}", url);
        }

        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(REQUEST_BODY, JSONObject.toJSONString(messageEntity)))
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("发送文件信息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();

        SendMessageRespEntity sendMessageRespEntity = JSONObject.parseObject(responseBody, SendMessageRespEntity.class);
        return sendMessageRespEntity.getBaseResponse().isSuccess();
    }

    @SneakyThrows
    public boolean sendVideo(File file, String toUserName) {

        long localId = System.currentTimeMillis() + (int) ((Math.random() * 9 + 1) * 1000);
        Message messageEntity = Message.builder()
                .baseRequest(ConfigStoreUtil.baseRequestUinInt)
                .msg(
                        Msg.builder()
                                .type(MsgType.VIDEO)
                                .toUserName(toUserName)
                                .localID(localId)
                                .clientMsgId(localId)
                                .fromUserName(ConfigStoreUtil.initInfoRespEntity.getUser().getUserName())
                                .mediaId(weChatMediaUploadService.uplaodVideo(file, toUserName).getMediaId())
                                .build()
                )
                .build();

        String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendvideomsg?fun=async&f=json&pass_ticket=" + ConfigStoreUtil.signInfo.getPassTicket();
        if (log.isDebugEnabled()) {
            log.debug("发送视频信息, url: {}", url);
        }

        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(REQUEST_BODY, JSONObject.toJSONString(messageEntity)))
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("发送视频信息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();
        SendMessageRespEntity sendMessageRespEntity = JSONObject.parseObject(responseBody, SendMessageRespEntity.class);
        return sendMessageRespEntity.getBaseResponse().isSuccess();
    }

    @SneakyThrows
    public boolean sendImage(File file, String toUserName) {

        long localId = System.currentTimeMillis() + (int) ((Math.random() * 9 + 1) * 1000);
        Message messageEntity = Message.builder()
                .baseRequest(ConfigStoreUtil.baseRequestUinInt)
                .msg(
                        Msg.builder()
                                .type(MsgType.IMAGE)
                                .toUserName(toUserName)
                                .localID(localId)
                                .clientMsgId(localId)
                                .fromUserName(ConfigStoreUtil.initInfoRespEntity.getUser().getUserName())
                                .mediaId(weChatMediaUploadService.uplaodPic(file, toUserName).getMediaId())
                                .build()
                )
                .build();

        String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsgimg?fun=async&f=json";

        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(REQUEST_BODY, JSONObject.toJSONString(messageEntity)))
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("发送图片信息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();

        SendMessageRespEntity sendMessageRespEntity = JSONObject.parseObject(responseBody, SendMessageRespEntity.class);
        return sendMessageRespEntity.getBaseResponse().isSuccess();
    }
}
