package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 与微信获取信息的实体
 */
@Data
@Builder
public class Msg {

    /**
     * 类型
     * 1  文字
     * 6  文件
     * 3  图片
     * 43 视频
     */
    @JSONField(name = "Type")
    private int type;

    /**
     * 内容
     * 文字信息及文件信息时存在
     * 文字信息为信息内容
     * 文件信息时，内容格式如下：
     *      <appmsg appid='wxeb7ec651dd0aefa9' sdkver=''>
     *          <title>${fileName}</title>
     *          <des></des>
     *          <action></action>
     *          <type>6</type>
     *          <content></content>
     *          <url></url>
     *          <lowurl></lowurl>
     *          <appattach>
     *              <totallen>${fileSize}</totallen>
     *              <attachid>${mediaId}</attachid>
     *              <fileext>${后缀名}</fileext>
     *          </appattach>
     *          <extinfo></extinfo>
     *      </appmsg>
     */
    @JSONField(name = "Content")
    private String content;

    /**
     * 发送人
     */
    @JSONField(name = "FromUserName")
    private String fromUserName;

    /**
     * 接收人
     */
    @JSONField(name = "ToUserName")
    private String toUserName;

    /**
     * 13位时间戳和4位随机数组成，与ClientMsgId一致
     */
    @JSONField(name = "LocalID")
    private long localID;

    /**
     * 13位时间戳和4位随机数组成，与localID一致
     */
    @JSONField(name = "ClientMsgId")
    private long clientMsgId;

    /**
     * 媒体Id，图片信息及视频信息时存在
     */
    @JSONField(name = "MediaId")
    private String mediaId;


}
