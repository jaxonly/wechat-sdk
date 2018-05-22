package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送消息报文实体
 */
@Data
@Builder
public class Message {

    @JSONField(name = "BaseRequest")
    private BaseRequestUinInt baseRequest;

    @JSONField(name = "Msg")
    private Msg msg;

    @JSONField(name = "Scene")
    private int scene = 0;
}
