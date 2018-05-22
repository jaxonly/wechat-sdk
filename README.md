# 基于微信网页API的JavaSDK实现

## 安装
- maven
**先 clone 本项目，使用 ```mvn install``` 后 ，在pom文件中加入以下依赖**
```xml
<dependency>
    <groupId>cn.jaxxu</groupId>
    <artifactId>wechat4j</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 使用
复读机
```
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
    // watch 开始监听,目前阻塞
    weChatStarpup.logout();
```
更多例子及文档正在补充中....

## 已实现

- [x] 登录
- 发送消息
    - [x] 文字消息
    - [x] 文件消息
    - [x] 图片消息
    - [x] 视频消息

- 监听消息()
    - [x] 联系人/群 文字消息

- [x] 附件
    - [x] 上传图片、视频及文档
- 通讯录
    - [x] 初始化通讯录

## future

- 发送消息
    - [ ] 群消息回复支持@群成员

- 完善监听消息(未实现消息支持自行解析,欢迎提pr一起完善)
    - [ ] 联系人/群 图片消息
    - [ ] 联系人/群 视频消息
    - [ ] 联系人/群 地理位置消息
    - [ ] 联系人/群 名片消息
    - [ ] 联系人/群 语音消息
    - [ ] 联系人/群 动画表情
    - [ ] 联系人/群 普通连接或应用分享消息
    - [ ] 联系人/群 音乐连接消息
    - [ ] 联系人/群 红包消息
    - [ ] 联系人/群 系统消息

- 附件
    - [ ] 根据mediaId下载附件

- [ ] 通讯录
    - [ ] 更新通讯录
    - [ ] 查询指定条件的联系人或群聊（没想好怎么实现，欢迎pr讨论）