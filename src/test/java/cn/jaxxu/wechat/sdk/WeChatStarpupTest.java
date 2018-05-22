package cn.jaxxu.wechat.sdk;

import cn.jaxxu.wechat.sdk.model.Contact;
import cn.jaxxu.wechat.sdk.service.WeChatContactService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WeChatStarpupTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

//    @Test
    public void getInitInfo() {
        WeChatStarpup weChatStarpup = new WeChatStarpup();
        weChatStarpup.onQrcodeLoad(url -> {
            log.info("成功获取登录连接!" + url);
        }).onQrcodeChange((oldUrl, newUrl) -> {
            log.info("登录状态失效，重新获取二维码连接!" + oldUrl + " | " + newUrl);
        }).onUserScan(userAvatar -> {
            log.info("用户扫描二维码进入确认页面了!" + userAvatar);
        }).onLoginSuccess(() -> {
            log.info("登录成功");
        }).onInitUserData(initInfoRespEntity -> {
            log.info("获取到初始化信息了!" + initInfoRespEntity.toString());
//            new WeChatSendMessageService().sendImage(new File("/Users/xuyihao/Documents/QQ20180502-161126@2x.png"), "filehelper");
//            new WeChatSendMessageService().sendVideo(new File("/Users/xuyihao/Downloads/QQ20180503-100940-HD.mp4"), "filehelper");
//            new WeChatSendMessageService().sendFile(new File("/Users/xuyihao/Downloads/QQ20180503-100940-HD.mp4"), "filehelper");
//            new WeChatSendMessageService().sendFile(new File("/Users/xuyihao/Documents/QQ20180502-161126@2x.png"), "filehelper");
            weChatStarpup.sendMessage("123123", initInfoRespEntity.getUser().getUserName());
//            new WeChatSendMessageService().sendFile(new File("/Users/xuyihao/Documents/未命名"), "filehelper");
            List<Contact> newContacts = new WeChatContactService().getNewContacts();
            log.info(JSONObject.toJSONString(newContacts));
            log.info(String.valueOf(newContacts.size()));
            List<String> list = new ArrayList<>();
            for (Contact newContact : newContacts) {
//                if (newContact.getUserName().startsWith("@@")) {
                    list.add(newContact.getUserName());
//                }
            }

            List<Contact> contacts = new WeChatContactService().getGroupDetail(list);
            log.info(JSONObject.toJSONString(contacts));
            log.info(String.valueOf(contacts.size()));
        }).watchTextMsg(((fromUserName, content) -> {
        })).watchGroupTextMsg(((groupUserName, content, isAt, userName) -> {

        })).openPrintQrcode().login();

        weChatStarpup.logout(() -> {
            log.info("退出成功");
        });
    }

//    @Test
    public void getDevice() {
        WeChatStarpup weChatStarpup = new WeChatStarpup();
        weChatStarpup.onLoginSuccess(() -> {
            // 登陆成功给文件传输助手发送一条消息
            weChatStarpup.sendMessage("123123", "filehelper");
        }).watchTextMsg(((fromUserName, content) -> {
            // watchTextMsg 新增监听联系人文字消息事件，可多个事件叠加
            weChatStarpup.sendMessage(content, fromUserName);
        })).openPrintQrcode().login().watch();
        // openPrintQrcode 在控制台中打印登录二维码,也可在SDK提供的生命周期回调中获取二维码地址
        // login 调用登录
        weChatStarpup.logout();
    }
}