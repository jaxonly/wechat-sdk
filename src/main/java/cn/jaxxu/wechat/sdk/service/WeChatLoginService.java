package cn.jaxxu.wechat.sdk.service;

import cn.jaxxu.wechat.sdk.SysConstant;
import cn.jaxxu.wechat.sdk.model.LoginSignInfoRespEntity;
import cn.jaxxu.wechat.sdk.model.LoginStatusInfoRespEntity;
import cn.jaxxu.wechat.sdk.util.OkHttpClientUtil;
import cn.jaxxu.wechat.sdk.util.URLUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;

import static cn.jaxxu.wechat.sdk.constant.RequestType.REQUEST_BODY;

@Slf4j
public class WeChatLoginService {

    /**
     * 得到微信登录Uuid
     *
     * @return 微信登录Uuid
     */
    @SneakyThrows
    public String getUuid() {
        String url = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=" + SysConstant.timestamp++;
        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("申请微信登录Uuid, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();
        if (!responseBody.contains("code = 200")) {
            log.error("申请微信登录Uuid, 请求微信服务返回异常。responseBody: {}", responseBody);
            throw new RuntimeException();
        }
        if (log.isDebugEnabled()) {
            log.debug("申请微信登录Uuid, url: {}", url);
        }
        String uuid = responseBody.substring(50, responseBody.length() - 2);
        if (log.isDebugEnabled()) {
            log.debug("申请微信登录Uuid, uuid: {}", uuid);
        }
        return uuid;
    }

    /**
     * 得到二维码Url
     *
     * @return 得到二维码Url
     */
    public String getQrcode(String uuid) {
        //https://login.weixin.qq.com/qrcode/
        String url = "https://login.weixin.qq.com/qrcode/" + uuid;
        return url;
    }

    /**
     * 得到二维码Source
     *
     * @return 得到二维码Source
     */
    public String getQrcodeSource(String uuid) {
        //https://login.weixin.qq.com/l/4fYqyiHKPw==
        String url = "https://login.weixin.qq.com/l/" + uuid;
        return url;
    }

    /**
     * 得到二维码字节数组
     *
     * @return
     */
    @SneakyThrows
    public byte[] getQrcodeBytes(String uuid) {
        String url = getQrcode(uuid);
        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("申请微信登录Uuid, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        return response.body().bytes();
    }

    /**
     * 查询微信扫码登录状态信息
     *
     * @param uuid      登录uuid
     * @param loginicon 是否需要返回头像信息
     * @param tip       首次查询传1，其余传2
     * @return 查询返回对象
     */
    @SneakyThrows
    public LoginStatusInfoRespEntity queryQrcodeLoginStatusInfo(String uuid, boolean loginicon, String tip) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login").newBuilder();
        urlBuilder.addQueryParameter("loginicon", String.valueOf(loginicon));
        urlBuilder.addQueryParameter("uuid", uuid);
        urlBuilder.addQueryParameter("tip", tip);
        urlBuilder.addQueryParameter("r", String.valueOf(System.currentTimeMillis()));
        urlBuilder.addQueryParameter("_", String.valueOf(SysConstant.timestamp++));
        String url = urlBuilder.build().toString();
        if (log.isDebugEnabled()) {
            log.debug("查询微信扫码登录状态信息, url: {}", url);
        }
        OkHttpClient client = OkHttpClientUtil.getClent(30);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("查询微信扫码登录状态信息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();
        String code = responseBody.substring(12, 15);

        LoginStatusInfoRespEntity loginStatusInfoRespEntity = new LoginStatusInfoRespEntity();
        loginStatusInfoRespEntity.setCode(code);
        if ("201".equals(code)) {
            //            window.code=201;window.userAvatar = 'data:img/jpg;base64,';
            if (loginicon) {
                loginStatusInfoRespEntity.setUserAvatar(responseBody.substring(37, responseBody.length() - 2));
            }
        } else if ("200".equals(code)) {
//            window.code=200;window.redirect_uri="";
            loginStatusInfoRespEntity.setRedirectUri(responseBody.substring(38, responseBody.length() - 2));
        }
        return loginStatusInfoRespEntity;
    }


    /**
     * 打印二维码
     */
    @SneakyThrows
    public void printQrcode(String uuid) {
        String black = "\033[40m  \033[0m";
        String white = "\033[50m  \033[0m";

        int width = 31;
        int height = 31;

        HashMap<EncodeHintType, Object> hints = new HashMap<>(5);
        // 设置编码方式utf-8
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置二维码的纠错级别为h
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = new MultiFormatWriter().encode(getQrcodeSource(uuid),
                BarcodeFormat.QR_CODE, width, height, hints);
        log.info("==============================================================");
        log.info("=========================微信登录二维码=========================");
        log.info("==============================================================");
        for (int i = 0; i < width; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < height; j++) {
                if (matrix.get(i, j)) {
//                    打印黑
                    sb.append(white);
                } else {
                    sb.append(black);
                }
//                打印白
            }
            log.info(sb.toString());
        }
    }

    /**
     * 获取微信凭证信息
     *
     * @param redirectUri 扫码成功事件后返回的Url
     * @return 凭证信息
     */
    @SneakyThrows
    public LoginSignInfoRespEntity getSignInfo(String redirectUri) {
        String url = redirectUri + "&fun=new&version=v2";
        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("获取凭证信息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(response.body().byteStream());
        Element rootElement = document.getRootElement();
//        <error>
//            <ret>0</ret>
//            <message></message>
//            <skey>@crypt_e17b1c75_a97009863b20b42a65cdd09913c0f2eb</skey>
//            <wxsid>Ji+f8dFUJeTJNVaP</wxsid>
//            <wxuin>279165593</wxuin>
//            <pass_ticket>e4eUJdFCfdwQdQtWMdaleNT8ST9rzSodP0PafiSt87QOzV3ZbhQZdRTuZb%2Bo5FFV</pass_ticket>
//            <isgrayscale>1</isgrayscale>
//        </error>
        String ret = rootElement.element("ret").getText();
        if (!"0".equals(ret)) {
            String message = rootElement.element("message").getText();
            throw new RuntimeException(ret + " " + message);
        }
        String skey = rootElement.element("skey").getText();
        String sid = rootElement.element("wxsid").getText();
        String uin = rootElement.element("wxuin").getText();
        String passTicket = URLUtils.decode(rootElement.element("pass_ticket").getText());

        LoginSignInfoRespEntity loginSignInfoRespEntity = new LoginSignInfoRespEntity();
        loginSignInfoRespEntity.setSkey(skey);
        loginSignInfoRespEntity.setSid(sid);
        loginSignInfoRespEntity.setUin(uin);
        loginSignInfoRespEntity.setPassTicket(passTicket);
        return loginSignInfoRespEntity;
    }

    /**
     * 退出登录
     */
    @SneakyThrows
    public void logout(String skey, String sid, String uin) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxlogout").newBuilder();
        urlBuilder.addQueryParameter("redirect", "1");
        urlBuilder.addQueryParameter("type", "1");
        urlBuilder.addQueryParameter("skey", skey);
        String url = urlBuilder.build().toString();
        if (log.isDebugEnabled()) {
            log.debug("退出登录状态, url: {}", url);
        }
        JSONObject postParam = new JSONObject();
        postParam.put("sid", sid);
        postParam.put("uin", uin);

        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(REQUEST_BODY, postParam.toJSONString()))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("退出登录状态, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
    }
}
