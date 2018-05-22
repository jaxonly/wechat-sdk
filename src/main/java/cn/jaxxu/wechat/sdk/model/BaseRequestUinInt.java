package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信交互时提交的基础请求对象
 */
@Data
@Builder
public class BaseRequestUinInt {

    @JSONField(name = "Uin")
    private int uin;

    @JSONField(name = "Sid")
    private String sid;

    @JSONField(name = "Skey")
    private String skey;

    @JSONField(name = "DeviceID")
    private String deviceId;
}
