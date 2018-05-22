package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 最近联系人
 */
@Data
public class Contact {

    /**
     * 联系人uin
     */
    @JSONField(name = "Uin")
    private int uin;

    /**
     * 联系人userName。
     * 目前知道有三种,@开头为单人聊天,@@开头为多人聊天,系统特殊账户有特殊名称,filehelper为文件传输助手
     * 很重要的信息，当你需要与该联系人交互时需要用到，你可以把它理解成唯一的由你指向他的指针。UserName不是固定的，每次登录获取的同一个联系人的UserName都不一样
     */
    @JSONField(name = "UserName")
    private String userName;

    /**
     * 联系人昵称
     */
    @JSONField(name = "NickName")
    private String nickName;

    /**
     * 联系人头像连接
     */
    @JSONField(name = "HeadImgUrl")
    private String headImgUrl;

    /**
     * todo://不清楚意义，但经常联系的数值比较高，猜测类似亲密值
     */
    @JSONField(name = "ContactFlag")
    private int contactFlag;

    /**
     * 群聊成员数量，该联系人为群时
     */
    @JSONField(name = "MemberCount")
    private int memberCount;

    /**
     * 群聊成员列表
     */
    @JSONField(name = "MemberList")
    private List<Member> memberList;

    /**
     * 联系人备注名
     */
    @JSONField(name = "RemarkName")
    private String remarkName;

    /**
     * todo
     */
    @JSONField(name = "HideInputBarFlag")
    private int hideInputBarFlag;

    /**
     * 联系人性别，0为未定义、1为男性、2为女性
     */
    @JSONField(name = "Sex")
    private int sex;

    /**
     * 个性签名，或群组的公告，或公众号的说明
     */
    @JSONField(name = "Signature")
    private String signature;

    /**
     * 一般个人公众号/服务号：8 ,一般企业的服务号：24 ,微信官方账号微信团队：56
     */
    @JSONField(name = "VerifyFlag")
    private int verifyFlag;

    /**
     * 群聊所有者Uin
     */
    @JSONField(name = "OwnerUin")
    private int ownerUin;

    /**
     * 联系人昵称拼音首字母大写
     */
    @JSONField(name = "PYInitial")
    private String pyInitial;

    /**
     * 联系人昵称拼音全拼小写
     */
    @JSONField(name = "PYQuanPin")
    private String pyQuanPin;

    /**
     * 联系人备注名拼音首字母大写
     */
    @JSONField(name = "RemarkPYInitial")
    private String remarkPYInitial;

    /**
     * 联系人备注名拼音全拼小写
     */
    @JSONField(name = "RemarkPYQuanPin")
    private String remarkPYQuanPin;

    /**
     * 是否是星标好友，0为否，非0为是
     */
    @JSONField(name = "StarFriend")
    private int starFriend;

    /**
     * todo
     */
    @JSONField(name = "AppAccountFlag")
    private int appAccountFlag;

    /**
     * todo
     */
    @JSONField(name = "Statues")
    private int statues;

    /**
     * todo
     */
    @JSONField(name = "AttrStatus")
    private long attrStatus;

    /**
     * 联系人省份
     */
    @JSONField(name = "Province")
    private String province;

    /**
     * 联系人城市
     */
    @JSONField(name = "City")
    private String city;

    /**
     * 微信号，不是每个用户都能拿到微信号
     */
    @JSONField(name = "Alias")
    private String alias;

    /**
     * todo
     */
    @JSONField(name = "SnsFlag")
    private int snsFlag;

    /**
     * todo
     */
    @JSONField(name = "UniFriend")
    private int uniFriend;

    /**
     * 显示名称，一般都为空，取NickName
     */
    @JSONField(name = "DisplayName")
    private String displayName;

    /**
     * 聊天室Id
     */
    @JSONField(name = "ChatRoomId")
    private int chatRoomId;

    /**
     * 搜索关键词
     */
    @JSONField(name = "KeyWord")
    private String keyWord;

    /**
     * 加密群聊Id，目前没看到哪里用到
     */
    @JSONField(name = "EncryChatRoomId")
    private String encryChatRoomId;

    /**
     * 是否为群聊拥有者,0为否，非0为是
     */
    @JSONField(name = "IsOwner")
    private int isOwner;
}