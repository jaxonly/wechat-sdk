package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * 微信检查新信息返回实体
 */
@Data
public class AddMsg {

    /**
     * 消息Id
     */
    @JSONField(name = "MsgId")
    private String msgId;

    /**
     * 消息来源
     */
    @JSONField(name = "FromUserName")
    private String fromUserName;

    /**
     * 消息接收人
     */
    @JSONField(name = "toUserName")
    private String toUserName;

    /**
     * 消息类型
     */
    @JSONField(name = "MsgType")
    private int msgType;

    /**
     * 消息正文
     */
    @JSONField(name = "Content")
    private String content;

    /**
     * todo
     */
    @JSONField(name = "Status")
    private int status;

    /**
     * todo
     */
    @JSONField(name = "ImgStatus")
    private int imgStatus;

    /**
     * 消息发送时间
     */
    @JSONField(name = "CreateTime")
    private Date createTime;

    /**
     * 语音消息长度
     */
    @JSONField(name = "VoiceLength")
    private int voiceLength;

    /**
     * todo
     */
    @JSONField(name = "PlayLength")
    private int playLength;

    /**
     * 文件名，网页微信和电脑端微信能传送文件，但它还能是app通知之类的名字，比如“转账过期退还通知”，当AppMsgType为AUDIO/VIDEO/URL/ATTACH时会用到
     */
    @JSONField(name = "FileName")
    private String fileName;

    /**
     * FileSize,文件大小
     */
    @JSONField(name = "FileSize")
    private String fileSize;

    /**
     * 当AppMsgType为APPMSGTYPE_ATTACH时会用到，构造MMAppMsgDownloadUrl这个看起来像是下载链接的东西时作为mediaid参数
     */
    @JSONField(name = "MediaId")
    private String mediaId;

    /**
     * 跟这条消息相关的Url，比如LOCATION的这里会是打开腾讯地图的url
     */
    @JSONField(name = "Url")
    private String url;

    /**
     * todo
     */
    @JSONField(name = "AppMsgType")
    private int appMsgType;

    /**
     * todo
     */
    @JSONField(name = "StatusNotifyCode")
    private int statusNotifyCode;

    /**
     * 跟通知事件有关的用户名
     */
    @JSONField(name = "StatusNotifyUserName")
    private String statusNotifyUserName;

    /**
     * 是否是转发的内容
     */
    @JSONField(name = "ForwardFlag")
    private int forwardFlag;

    /**
     * 当MsgType为EMOTION时，会判断HasProductId是否非0，如果非0，则这个表情消息不受支持，作为文本消息处理
     */
    @JSONField(name = "HasProductId")
    private int hasProductId;

    /**
     * ticket
     */
    @JSONField(name = "Ticket")
    private String ticket;

    /**
     * 表情图片尺寸
     */
    @JSONField(name = "ImgHeight")
    private int imgHeight;

    /**
     * 表情图片尺寸
     */
    @JSONField(name = "ImgWidth")
    private int imgWidth;

    /**
     * 常量，只会被用来判断是不是LOCATION位置消息
     */
    @JSONField(name = "SubMsgType")
    private int subMsgType;

    /**
     * todo
     */
    @JSONField(name = "NewMsgId")
    private long newMsgId;

    /**
     * 位置消息时，包含了这个定位的相关信息
     *     <?xml version="1.0"?>
     *     <msg>
     *         <location x="39.983166" y="116.491669" scale="16" label="" maptype="0" poiname="" />
     *     </msg>
     */
    @JSONField(name = "OriContent")
    private String oriContent;

    /**
     * todo
     */
    @JSONField(name = "EncryFileName")
    private String encryFileName;

    //todo RecommendInfo
    //todo AppInfo
}
