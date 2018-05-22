package cn.jaxxu.wechat.sdk.service;

import cn.jaxxu.wechat.sdk.enums.UploadMediaTypeEnum;
import cn.jaxxu.wechat.sdk.model.UploadMediaReqEntity;
import cn.jaxxu.wechat.sdk.model.UploadMediaRespEntity;
import cn.jaxxu.wechat.sdk.util.ConfigStoreUtil;
import cn.jaxxu.wechat.sdk.util.MD5Utils;
import cn.jaxxu.wechat.sdk.util.OkHttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.Date;

/**
 * 微信上传媒体文件
 */
@Slf4j
public class WeChatMediaUploadService {

    /**
     *
     * @param mediaType pic、video、doc
     */
    @SneakyThrows
    public UploadMediaRespEntity uplaod(File file, UploadMediaTypeEnum mediaType, String toUserName) {
        String mimeType = new MimetypesFileTypeMap().getContentType(file);

        RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), file);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("filename", file.getName(), fileBody)
                .addFormDataPart("pass_ticket", ConfigStoreUtil.signInfo.getPassTicket())
                .addFormDataPart("webwx_data_ticket", ConfigStoreUtil.cookieStoreMap.get("webwx_data_ticket"))
                .addFormDataPart("uploadmediarequest", JSONObject.toJSONString(UploadMediaReqEntity.builder()
                        .baseRequest(ConfigStoreUtil.baseRequestUinInt)
                        .clientMediaId(System.currentTimeMillis())
                        .totalLen(file.length())
                        .fromUserName(ConfigStoreUtil.initInfoRespEntity.getUser().getUserName())
                        .toUserName(toUserName)
                        .fileMd5(MD5Utils.getFileMD5String(file))
                        .build()))
                .addFormDataPart("mediatype", mediaType.value)
                .addFormDataPart("size", String.valueOf(file.length()))
                .addFormDataPart("lastModifiedDate", String.valueOf(new Date(file.lastModified())))
                .addFormDataPart("type", mimeType)
                .addFormDataPart("name", file.getName())
                .addFormDataPart("id", "WU_FILE_0")
                .build();

        Request request = new Request.Builder()
                .url("https://file.wx2.qq.com/cgi-bin/mmwebwx-bin/webwxuploadmedia?f=json")
                .post(requestBody)
                .build();
        OkHttpClient clent = OkHttpClientUtil.getClent();
        Response response = clent.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("上传媒体文件, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();
        UploadMediaRespEntity uploadMediaRespEntity = JSONObject.parseObject(responseBody, UploadMediaRespEntity.class);

        return uploadMediaRespEntity;
    }

    @SneakyThrows
    public UploadMediaRespEntity uplaodPic(File file, String toUserName) {
        return uplaod(file, UploadMediaTypeEnum.PIC, toUserName);
    }

    @SneakyThrows
    public UploadMediaRespEntity uplaodDoc(File file, String toUserName) {
        return uplaod(file, UploadMediaTypeEnum.DOC, toUserName);
    }

    @SneakyThrows
    public UploadMediaRespEntity uplaodVideo(File file, String toUserName) {
        return uplaod(file, UploadMediaTypeEnum.VIDEO, toUserName);
    }
}
