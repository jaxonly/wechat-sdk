package cn.jaxxu.wechat.sdk;

import cn.jaxxu.wechat.sdk.constant.MsgType;
import cn.jaxxu.wechat.sdk.event.*;
import cn.jaxxu.wechat.sdk.model.*;
import cn.jaxxu.wechat.sdk.service.*;
import cn.jaxxu.wechat.sdk.util.ConfigStoreUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信SDK入口类
 */
@Slf4j
public class WeChatStarpup {

    /**
     * 是否保存登录状态, 短期内才有效
     */
    @Getter
    @Setter
    private boolean isSaveLogin = false;

    /**
     * 是否打印二维码到日志
     */
    private boolean printQrcode = false;

    // =========== Event ===========

    private LoginQrcodeUrlLoadEvent loginQrcodeUrlLoadEvent = null;

    private LoginQrcodeUrlChangeEvent loginQrcodeUrlChangeEvent = null;

    private UserScanQrcodeEvent userScanQrcodeEvent = null;

    private LoginSuccessEvent loginSuccessEvent = null;

    private InitUserDataEvent initUserDataEvent = null;


    // =========== service ===========

    private WeChatLoginService weChatLoginService = new WeChatLoginService();

    private WeChatInitService weChatInitService = new WeChatInitService();

    private WeChatSendMessageService weChatSendMessageService = new WeChatSendMessageService();

    private WeChatRecvMessageService weChatRecvMessageService = new WeChatRecvMessageService();

    private WeChatContactService weChatContactService = new WeChatContactService();

    WeChatStarpup() {
        ConfigStoreUtil.deviceId = getDeviceID();
        if (log.isDebugEnabled()) {
            log.debug("deviceId:{}", ConfigStoreUtil.deviceId);
        }
    }

    /**
     * 得到一个DeviceId，由e + 十五位随机数组成
     *
     * @return
     */
    private String getDeviceID() {
        return "e" + System.currentTimeMillis() + (int) ((Math.random() * 9 + 1) * 10);
    }

    /**
     * 设置得到二维码后的回调
     *
     * @param loginQrcodeUrlLoadEvent 函数
     * @return this
     */
    public WeChatStarpup onQrcodeLoad(LoginQrcodeUrlLoadEvent loginQrcodeUrlLoadEvent) {
        this.loginQrcodeUrlLoadEvent = loginQrcodeUrlLoadEvent;
        return this;
    }

    /**
     * 设置得到二维码刷新后的回调
     *
     * @param loginQrcodeUrlChangeEvent 函数
     * @return this
     */
    public WeChatStarpup onQrcodeChange(LoginQrcodeUrlChangeEvent loginQrcodeUrlChangeEvent) {
        this.loginQrcodeUrlChangeEvent = loginQrcodeUrlChangeEvent;
        return this;
    }

    /**
     * 扫描二维码后响应事件
     *
     * @param userScanQrcodeEvent 事件
     * @return this
     */
    public WeChatStarpup onUserScan(UserScanQrcodeEvent userScanQrcodeEvent) {
        this.userScanQrcodeEvent = userScanQrcodeEvent;
        return this;
    }

    /**
     * 用户手机确认登录成功后响应事件
     *
     * @param loginSuccessEvent 事件
     * @return this
     */
    public WeChatStarpup onLoginSuccess(LoginSuccessEvent loginSuccessEvent) {
        this.loginSuccessEvent = loginSuccessEvent;
        return this;
    }

    /**
     * 初始化用户数据后后响应事件
     *
     * @param initUserDataEvent 事件
     * @return this
     */
    public WeChatStarpup onInitUserData(InitUserDataEvent initUserDataEvent) {
        this.initUserDataEvent = initUserDataEvent;
        return this;
    }

    /**
     * 是否打印二维码到日志
     *
     * @return this
     */
    public WeChatStarpup openPrintQrcode() {
        this.printQrcode = true;
        return this;
    }

    public WeChatStarpup saveLoginInfo() {
        this.isSaveLogin = true;
        return this;
    }

    /**
     * 登录
     *
     * @return this
     */
    public WeChatStarpup login() {
        String uuid = weChatLoginService.getUuid();
        String qrcodeUrl = weChatLoginService.getQrcode(uuid);
        //https://login.weixin.qq.com/l/4fYqyiHKPw==
        /*
         * 如果如果定义了二维码加载事件，触发
         */
        if (this.printQrcode) {
            weChatLoginService.printQrcode(uuid);
        }
        if (this.loginQrcodeUrlLoadEvent != null) {
            this.loginQrcodeUrlLoadEvent.load(qrcodeUrl);
        }
        while (true) {
            LoginStatusInfoRespEntity loginStatusInfoRespEntity = weChatLoginService.queryQrcodeLoginStatusInfo(uuid, false, "0");
            if (log.isDebugEnabled()) {
                log.debug("轮询登录状态接口返回,{}", JSONObject.toJSONString(loginStatusInfoRespEntity));
            }
            if ("200".equals(loginStatusInfoRespEntity.getCode())) {
                LoginSignInfoRespEntity signInfo = weChatLoginService.getSignInfo(loginStatusInfoRespEntity.getRedirectUri());
                ConfigStoreUtil.signInfo = signInfo;
                ConfigStoreUtil.baseRequestUinInt = BaseRequestUinInt.builder()
                        .sid(signInfo.getSid())
                        .skey(signInfo.getSkey())
                        .uin(Integer.parseInt(signInfo.getUin()))
                        .deviceId(ConfigStoreUtil.deviceId)
                        .build();
                ConfigStoreUtil.baseRequest = BaseRequest.builder()
                        .sid(signInfo.getSid())
                        .skey(signInfo.getSkey())
                        .uin(signInfo.getUin())
                        .deviceId(ConfigStoreUtil.deviceId)
                        .build();
                InitInfoRespEntity initInfo = weChatInitService.getInitInfo(ConfigStoreUtil.baseRequest, signInfo.getPassTicket());

                ConfigStoreUtil.initInfoRespEntity = initInfo;

                // 将最近联系人信息中群聊信息加入缓存中
                for (Contact contact : initInfo.getContactList()) {
                    if (contact.getUserName().startsWith("@@")) {
                        weChatContactService.insertContactGroupCache(contact);
                    }
                }
                if (this.initUserDataEvent != null) {
                    this.initUserDataEvent.initUserData(initInfo);
                }
                if (this.loginSuccessEvent != null) {
                    this.loginSuccessEvent.userLoginSuccess();
                }
                break;
            } else if ("400".equals(loginStatusInfoRespEntity.getCode())) {
                uuid = weChatLoginService.getUuid();
                String oldUrl = qrcodeUrl;
                qrcodeUrl = weChatLoginService.getQrcode(uuid);

                if (this.printQrcode) {
                    weChatLoginService.printQrcode(uuid);
                }
                /*
                 * 如果定义了二维码变更事件，触发
                 */
                if (this.loginQrcodeUrlChangeEvent != null) {
                    this.loginQrcodeUrlChangeEvent.change(oldUrl, qrcodeUrl);
                }
            } else if ("201".equals(loginStatusInfoRespEntity.getCode())) {
                /*
                 * 如果定义了用户扫描事件，触发
                 */
                if (this.userScanQrcodeEvent != null) {
                    userScanQrcodeEvent.userScan(loginStatusInfoRespEntity.getUserAvatar());
                }
            }
        }
        weChatContactService.init();
        return this;
    }

    /**
     * 退出登录
     */
    public void logout(LogoutEvent logoutEvent) {
        weChatLoginService.logout(ConfigStoreUtil.baseRequest.getSkey(), ConfigStoreUtil.baseRequest.getSid(), ConfigStoreUtil.baseRequest.getUin());
        if (logoutEvent != null) {
            logoutEvent.logout();
        }
    }

    /**
     * 退出登录
     */
    public void logout() {
        logout(null);
    }

    public boolean sendMessage(String message, String toUserName) {
        return weChatSendMessageService.sendMessage(message, toUserName);
    }

    public boolean sendImage(File file, String toUserName) {
        return weChatSendMessageService.sendImage(file, toUserName);
    }

    public boolean sendVideo(File file, String toUserName) {
        return weChatSendMessageService.sendVideo(file, toUserName);
    }

    public boolean sendFile(File file, String toUserName) {
        return weChatSendMessageService.sendFile(file, toUserName);
    }

    /**
     * 普通文字信息事件响应列表
     */
    private List<TextRecvResponseEvent> textRecvResponseEvents = new ArrayList<>();

    public WeChatStarpup watchTextMsg(TextRecvResponseEvent event) {
        textRecvResponseEvents.add(event);
        return this;
    }

    /**
     * 群文字信息事件响应列表
     */
    private List<GroupTextRecvResponseEvent> groupTextRecvResponseEvents = new ArrayList<>();

    public WeChatStarpup watchGroupTextMsg(GroupTextRecvResponseEvent event) {
        groupTextRecvResponseEvents.add(event);
        return this;
    }

    public WeChatStarpup watch() {
        //开启轮询
//        new Thread(() -> {
        while (true) {
            SyncCheckRespEntity syncCheckRespEntity = weChatRecvMessageService.syncCheck();
            //检测是否被挤下线
            if ("1100".equals(syncCheckRespEntity.getRetcode())) {
                log.error("微信被挤下线");
                break;
            }
            if ("2".equals(syncCheckRespEntity.getSelector())) {
                //拉取最新信息
                SyncMsgRespEntity syncMsgRespEntity = weChatRecvMessageService.syncMsg();
                List<AddMsg> addMsgList = syncMsgRespEntity.getAddMsgList();
                for (AddMsg addMsg : addMsgList) {
                    if (log.isDebugEnabled()) {
                        log.debug("接收到信息：{}", JSONObject.toJSONString(addMsg));
                    }
                    //根据类型响应信息
                    if (addMsg.getMsgType() == MsgType.TEXT) {
                        //判断为群信息还是普通信息
                        String fromUserName = addMsg.getFromUserName();
                        String content = addMsg.getContent();
                        if (fromUserName.startsWith("@@")) {
                            //在该群的昵称
                            String nickName = ConfigStoreUtil.initInfoRespEntity.getUser().getNickName();
                            boolean isAt = content.contains("@" + nickName + (char) (8197));
                            String userName = content.split(":")[0];
                            for (GroupTextRecvResponseEvent groupTextRecvResponseEvent : groupTextRecvResponseEvents) {
                                groupTextRecvResponseEvent.recv(fromUserName, content, isAt, userName);
                            }
                        } else {
                            for (TextRecvResponseEvent textRecvResponseEvent : textRecvResponseEvents) {
                                textRecvResponseEvent.recv(fromUserName, content);
                            }
                        }
                    }
                }
                //更新SyncKey
                ConfigStoreUtil.initInfoRespEntity.setSyncKey(syncMsgRespEntity.getSyncKey());
            }
        }
//        }).start();
        return this;
    }
}