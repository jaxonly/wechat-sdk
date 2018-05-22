package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传文件响应实体
 */
@Data
public class UploadMediaRespEntity {

    @JSONField(name = "BaseResponse")
    private BaseResponse baseResponse;

    /**
     * 媒体Id
     */
    @JSONField(name = "MediaId")
    private String mediaId;

    /**
     * todo
     */
    @JSONField(name = "StartPos")
    private int startPos;

    /**
     * todo
     */
    @JSONField(name = "CDNThumbImgHeight")
    private int cdnThumbImgHeight;

    /**
     * todo
     */
    @JSONField(name = "CDNThumbImgWidth")
    private int cdnThumbImgWidth;

    /**
     * todo
     */
    @JSONField(name = "EncryFileName")
    private String encryFileName;
}
