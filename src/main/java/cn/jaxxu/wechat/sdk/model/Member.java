package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 微信成员类
 */
@Data
public class Member {

    /**
     * 群成员Uin
     */
    @JSONField(name = "Uin")
    private int uin;

    /**
     * 群成员userName
     */
    @JSONField(name = "UserName")
    private String userName;

    /**
     * 群成员昵称
     */
    @JSONField(name = "NickName")
    private String nickName;

    /**
     * todo
     */
    @JSONField(name = "AttrStatus")
    private long attrStatus;

    /**
     * 名字拼音首字母大写
     */
    @JSONField(name = "PYInitial")
    private String pyInitial;

    /**
     * 名字拼音全拼小写
     */
    @JSONField(name = "PYQuanPin")
    private String pyQuanPin;

    /**
     * 备注名称拼音首字母大写
     */
    @JSONField(name = "RemarkPYInitial")
    private String remarkPYInitial;

    /**
     * 备注名称拼音全拼小写
     */
    @JSONField(name = "RemarkPYQuanPin")
    private String remarkPYQuanPin;

    /**
     * todo
     */
    @JSONField(name = "MemberStatus")
    private int memberStatus;

    /**
     * 显示名称，一般都为空，取NickName
     */
    @JSONField(name = "DisplayName")
    private String displayName;

    /**
     * 查找关键词
     */
    @JSONField(name = "KeyWord")
    private String keyWord;
}
