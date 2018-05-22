package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 与微信获取信息的实体
 */
@Data
public class User {

    /**
     * 当前用户Uin
     */
    @JSONField(name = "Uin")
    private int uin;

    /**
     * 当前用户UserName
     */
    @JSONField(name = "UserName")
    private String userName;

    /**
     * 当前用户昵称
     */
    @JSONField(name = "NickName")
    private String nickName;

    /**
     * 当前用户头像地址
     */
    @JSONField(name = "HeadImgUrl")
    private String headImgUrl;

    /**
     * 备注名
     */
    @JSONField(name = "RemarkName")
    private String remarkName;

    /**
     * 用户昵称拼音首字母大写
     */
    @JSONField(name = "PYInitial")
    private String pyInitial;

    /**
     * 用户昵称全拼小写
     */
    @JSONField(name = "PYQuanPin")
    private String pyQuanPin;

    /**
     * 用户备注名拼音首字母大写
     */
    @JSONField(name = "RemarkPYInitial")
    private String remarkPYInitial;

    /**
     * 用户备注名全拼小写
     */
    @JSONField(name = "RemarkPYQuanPin")
    private String remarkPYQuanPin;

    /**
     * todo
     */
    @JSONField(name = "HideInputBarFlag")
    private int hideInputBarFlag;

    /**
     * 是否是星标好友，0为否，非0为是
     */
    @JSONField(name = "StarFriend")
    private int starFriend;

    /**
     * 性别，0为未定义、1为男性、2为女性
     */
    @JSONField(name = "Sex")
    private int sex;

    /**
     * 个性签名，或群组的公告，或公众号的说明
     */
    @JSONField(name = "Signature")
    private String signature;

    /**
     * todo
     */
    @JSONField(name = "AppAccountFlag")
    private int appAccountFlag;

    /**
     * 一般个人公众号/服务号：8 ,一般企业的服务号：24 ,微信官方账号微信团队：56
     */
    @JSONField(name = "VerifyFlag")
    private int verifyFlag;

    /**
     * todo://不清楚意义，但经常联系的数值比较高，猜测类似亲密值
     */
    @JSONField(name = "ContactFlag")
    private int contactFlag;

    /**
     * todo
     */
    @JSONField(name = "WebWxPluginSwitch")
    private int webWxPluginSwitch;

    /**
     * todo
     */
    @JSONField(name = "HeadImgFlag")
    private int headImgFlag;

    /**
     * todo
     */
    @JSONField(name = "SnsFlag")
    private int snsFlag;
}
