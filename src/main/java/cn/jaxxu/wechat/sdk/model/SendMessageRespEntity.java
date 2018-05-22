package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 微信查询登录接口返回信息实体
 */
@Data
public class SendMessageRespEntity {
//        {
//            "BaseResponse": {
//            "Ret": 0,
//                    "ErrMsg": ""
//        }
//,
//            "MsgID": "2864424161118593160",
//                "LocalID": "1525316121463"
//        }
    @JSONField(name = "BaseResponse")
    private BaseResponse baseResponse;

    @JSONField(name = "MsgID")
    private String lsgID;

    @JSONField(name = "LocalID")
    private String localID;
}
