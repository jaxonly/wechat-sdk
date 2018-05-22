package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

/**
 * 微信交互时提交的基础响应对象
 */
@Data
public class BaseResponse {

    @JSONField(name = "Ret")
    private int ret;

    @JSONField(name = "ErrMsg")
    private String errMsg;

    public boolean isSuccess() {
        return ret == 0;
    }
}
