package cn.jaxxu.wechat.sdk.cache;

import cn.jaxxu.wechat.sdk.model.ContactGroup;
import cn.jaxxu.wechat.sdk.model.ContactUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeChatContactCache {

    /**
     * 个人用户信息缓存
     */
    public static List<ContactUser> contactUsers = new ArrayList<>();

    /**
     * 群组信息缓存
     */
    public static List<ContactGroup> contactGroups = new ArrayList<>();

    /**
     * 这里维护一份当前用户在各群聊的备注名，高频
     */
    public static Map<String, String> userGroupNameTemp = new HashMap<>();
}
