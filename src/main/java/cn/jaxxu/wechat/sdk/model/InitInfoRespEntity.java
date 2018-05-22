package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 微信初始化接口返回信息实体
 */
@Data
public class InitInfoRespEntity {

    @JSONField(name = "BaseResponse")
    private BaseResponse baseResponse;

    /**
     * 最近联系人数量
     */
    @JSONField(name = "Count")
    private int count;

    /**
     * 最近联系人信息
     */
    @JSONField(name = "ContactList")
    private List<Contact> contactList;

    /**
     * 同步 Key
     */
    @JSONField(name = "SyncKey")
    private SyncKey syncKey;

    /**
     * 当前用户信息
     */
    @JSONField(name = "User")
    private User user;

    /**
     * 最近联系人的UserName，与上面contactList中一致，以","分割
     */
    @JSONField(name = "ChatSet")
    private String chatSet;

    /**
     * Skey
     */
    @JSONField(name = "Skey")
    private String skey;

    /**
     * 客户端版本号
     */
    @JSONField(name = "ClientVersion")
    private int clientVersion;

    /**
     * 当前时间戳
     */
    @JSONField(name = "SystemTime")
    private int systemTime;

    /**
     * 是否灰度测试，1不是
     */
    @JSONField(name = "GrayScale")
    private int grayScale;

    /**
     * todo
     */
    @JSONField(name = "InviteStartCount")
    private int inviteStartCount;

    /**
     * 有多少个公众号推送了新文章
     */
    @JSONField(name = "MPSubscribeMsgCount")
    private int mpSubscribeMsgCount;

    /**
     * 公众号文章更新详情
     */
    @JSONField(name = "MPSubscribeMsgList")
    private List<MPSubscribeMsg> mpSubscribeMsgList;

    /**
     * todo
     */
    @JSONField(name = "ClickReportInterval")
    private int clickReportInterval;
}