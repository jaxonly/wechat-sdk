package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 微信检查新信息返回实体
 */
@Data
public class SyncMsgRespEntity {

    @JSONField(name = "BaseResponse")
    private BaseResponse baseResponse;

    /**
     * 消息数量
     */
    @JSONField(name = "AddMsgCount")
    private int addMsgCount;

    /**
     * 消息数量
     */
    @JSONField(name = "AddMsgList")
    private List<AddMsg> addMsgList;

    /**
     * 同步密匙
     */
    @JSONField(name = "SyncKey")
    private SyncKey syncKey;
}
