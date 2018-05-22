package cn.jaxxu.wechat.sdk.model;

import lombok.Data;

@Data
public class ContactGroupMember {

    /**
     * 唯一标识
     */
    private String userName;

    /**
     * 群名称
     */
    private String nickName;
}
