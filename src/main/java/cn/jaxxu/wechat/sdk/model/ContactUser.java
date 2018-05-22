package cn.jaxxu.wechat.sdk.model;

import lombok.Data;

@Data
public class ContactUser {

    /**
     * 唯一标识
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImgUrl;

    /**
     * 备注名
     */
    private String remarkName;

    /**
     * 性别
     * 0 未定义
     * 1 男性
     * 2 女性
     */
    private int sex;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 是否星标好友
     */
    private boolean starFriend;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 微信号，基本不可用，废弃
     */
//    private String alias;

    /**
     * 显示名称,NickName为空时取该字段
     */
    private String displayName;
}
