package cn.jaxxu.wechat.sdk.service;

import cn.jaxxu.wechat.sdk.SysConstant;
import cn.jaxxu.wechat.sdk.cache.WeChatContactCache;
import cn.jaxxu.wechat.sdk.constant.RequestType;
import cn.jaxxu.wechat.sdk.model.*;
import cn.jaxxu.wechat.sdk.util.ConfigStoreUtil;
import cn.jaxxu.wechat.sdk.util.OkHttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 微信联系人服务
 */
@Slf4j
public class WeChatContactService {

    /**
     * 得到通讯录信息，只有保存到通讯录的群才能获取到
     *
     * @return
     */
    @SneakyThrows
    public List<Contact> getNewContacts() {
        //https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact").newBuilder();
        urlBuilder.addQueryParameter("lang", SysConstant.LANG);
        urlBuilder.addQueryParameter("r", String.valueOf(System.currentTimeMillis()));
        urlBuilder.addQueryParameter("seq", "0");
        urlBuilder.addQueryParameter("skey", ConfigStoreUtil.signInfo.getSkey());
        urlBuilder.addQueryParameter("pass_ticket", ConfigStoreUtil.signInfo.getPassTicket());
        String url = urlBuilder.build().toString();
        if (log.isDebugEnabled()) {
            log.debug("获取微信通讯录信息, url: {}", url);
        }
        OkHttpClient client = OkHttpClientUtil.getClent();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("获取微信通讯录信息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
            throw new RuntimeException();
        }
        String responseBody = response.body().string();

        JSONObject jsonObject = JSONObject.parseObject(responseBody);
        return JSONObject.parseArray(jsonObject.get("MemberList").toString(), Contact.class);
    }

    /**
     * 得到群组详情
     *
     * @return 详细信息
     */
    @SneakyThrows
    public List<Contact> getGroupDetail(List<String> userNames) {
        List<Contact> contacts = new ArrayList<>();
        int num = (int) userNames.size() / 50 + 1;

        for (int i = 0; i < num; i++) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxbatchgetcontact").newBuilder();
            urlBuilder.addQueryParameter("lang", SysConstant.LANG);
            urlBuilder.addQueryParameter("r", String.valueOf(System.currentTimeMillis()));
            urlBuilder.addQueryParameter("type", "ex");
            urlBuilder.addQueryParameter("pass_ticket", ConfigStoreUtil.signInfo.getPassTicket());
            String url = urlBuilder.build().toString();
            if (log.isDebugEnabled()) {
                log.debug("获取指定userName详细信息, url: {}", url);
            }
            JSONObject paramObject = new JSONObject();
            paramObject.put("BaseRequest", ConfigStoreUtil.baseRequestUinInt);

            int toIndex = (i + 1) * 50;
            int fromIndex = i * 50;
            List<String> userNamesSub = userNames.subList(fromIndex, toIndex > userNames.size() ? userNames.size() : toIndex);
            paramObject.put("Count", userNamesSub.size());
            List<Map<String, String>> list = new ArrayList<>();
            Map<String, String> map;
            for (String userName : userNamesSub) {
                map = new HashMap<>(2);
                map.put("UserName", userName);
                list.add(map);
            }
            paramObject.put("List", list);
            OkHttpClient client = OkHttpClientUtil.getClent();
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(RequestType.REQUEST_BODY, paramObject.toJSONString()))
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                log.error("获取指定userName详细信息, 请求微信服务失败。code: {}, msg: {}", response.code(), response.message());
                throw new RuntimeException();
            }
            String responseBody = response.body().string();

            JSONObject jsonObject = JSONObject.parseObject(responseBody);

            contacts.addAll(JSONObject.parseArray(jsonObject.get("ContactList").toString(), Contact.class));
        }
        return contacts;
    }

    /**
     * 初始化联系人数据
     */
    public void init() {
        //获取联系人数据
        List<Contact> newContacts = getNewContacts();
        List<String> groupUserNames = new ArrayList<>();
        for (Contact newContact : newContacts) {
            if (newContact.getUserName().startsWith("@@")) {
                // 群聊用户
                groupUserNames.add(newContact.getUserName());
            } else {
                // 联系人
                ContactUser contactUser = new ContactUser();
//                contactUser.setAlias(newContact.getAlias());
                contactUser.setCity(newContact.getCity());
                contactUser.setDisplayName(newContact.getDisplayName());
                contactUser.setHeadImgUrl(newContact.getHeadImgUrl());
                contactUser.setNickName(newContact.getNickName());
                contactUser.setProvince(newContact.getProvince());
                contactUser.setRemarkName(newContact.getRemarkName());
                contactUser.setSex(newContact.getSex());
                contactUser.setSignature(newContact.getSignature());
                contactUser.setStarFriend(newContact.getStarFriend() != 0);
                contactUser.setUserName(newContact.getUserName());
                WeChatContactCache.contactUsers.add(contactUser);
            }
        }
        List<Contact> groupDetail = getGroupDetail(groupUserNames);
        for (Contact contact : groupDetail) {
            insertContactGroupCache(contact);
        }
    }

    public void insertContactGroupCache(Contact contact) {
        if (!WeChatContactCache.userGroupNameTemp.containsKey(contact.getUserName())) {
            ContactGroup contactGroup = new ContactGroup();
            contactGroup.setDisplayName(contact.getDisplayName());
            contactGroup.setHeadImgUrl(contact.getHeadImgUrl());
            contactGroup.setNickName(contact.getNickName());
            contactGroup.setSignature(contact.getSignature());
            contactGroup.setUserName(contact.getUserName());
            contactGroup.setChatRoomId(contact.getChatRoomId());
            contactGroup.setOwner(contact.getIsOwner() != 0);

            List<ContactGroupMember> contactGroupMembers = new ArrayList<>(contact.getMemberList().size() * 4 + 1);
            ContactGroupMember contactGroupMember;
            for (Member member : contact.getMemberList()) {
                contactGroupMember = new ContactGroupMember();
                //这里的displayName为群昵称，先取这个
                String displayName = member.getDisplayName();
                String nickName = !"".equals(displayName) ? displayName : member.getNickName();
                contactGroupMember.setNickName(nickName);
                contactGroupMember.setUserName(member.getUserName());
                contactGroupMembers.add(contactGroupMember);

                if (member.getUserName().equals(ConfigStoreUtil.initInfoRespEntity.getUser().getUserName())) {
                    contactGroup.setRemarkName(nickName);
                }
            }
            contactGroup.setMembers(contactGroupMembers);

            WeChatContactCache.contactGroups.add(contactGroup);
            WeChatContactCache.userGroupNameTemp.put(contactGroup.getUserName(), contactGroup.getRemarkName());
        }
    }
//
//    public List<ContactUser> findUser(ContactUser user) {
//        // 存放过滤结果的列表
//        List<ContactUser> result = null;
//
//        result = WeChatContactCache.contactUsers.stream()
//                .filter((ContactUser b) -> {
//                    return true;
//                })
//                .collect(Collectors.toList());
//
//        return result;
//    }
}
