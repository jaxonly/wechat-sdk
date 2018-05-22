package cn.jaxxu.wechat.sdk.model;

import lombok.Data;

/**
 * 微信检查新信息返回实体
 */
@Data
public class SyncCheckRespEntity {

    /**
     * retcode
     */
    private String retcode;

    /**
     * selector
     */
    private String selector;
}
