package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传文件实体
 */
@Data
@Builder
public class UploadMediaReqEntity {

    /**
     * 上传类型，都是2
     */
    @JSONField(name = "UploadType")
    private int uploadType = 2;

    /**
     * 固定0
     */
    @JSONField(name = "StartPos")
    private int startPos = 0;

    /**
     * 文件长度
     */
    @JSONField(name = "DataLen")
    private int dataLen = 0;

    /**
     * 固定4
     */
    @JSONField(name = "MediaType")
    private int mediaType = 4;





    @JSONField(name = "BaseRequest")
    private BaseRequestUinInt baseRequest;

    /**
     * 本地媒体Id,时间戳即可
     */
    @JSONField(name = "ClientMediaId")
    private long clientMediaId;

    /**
     * 文件长度
     */
    @JSONField(name = "TotalLen")
    private long totalLen;

    /**
     * 当前用户名
     */
    @JSONField(name = "FromUserName")
    private String fromUserName;

    /**
     * 发送目标用户名
     */
    @JSONField(name = "ToUserName")
    private String toUserName;

    /**
     * 文件MD5
     */
    @JSONField(name = "FileMd5")
    private String fileMd5;

}
