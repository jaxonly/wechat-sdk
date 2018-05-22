package cn.jaxxu.wechat.sdk.model;

import lombok.Data;

import java.util.List;

@Data
public class ContactGroup {

    /**
     * 唯一标识
     */
    private String userName;

    /**
     * 群名称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImgUrl;

    /**
     * 群成员详情
     */
    private List<ContactGroupMember> members;

    /**
     * 群公告
     */
    private String signature;

    /**
     * 显示名称,NickName为空时取该字段
     */
    private String displayName;

    /**
     * 群聊Id
     */
    private int chatRoomId;

    /**
     * 是否是该群聊拥有者
     */
    private boolean isOwner;

    /**
     * 在该群的群昵称
     */
    private String remarkName;
}
