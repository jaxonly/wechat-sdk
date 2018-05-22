package cn.jaxxu.wechat.sdk.event;

public interface GroupTextRecvResponseEvent {

    /**
     * 群文字信息响应事件
     * @param groupUserName 群UserName
     * @param content 发送内容
     * @param isAt 是否@本微信号
     * @param userName 发送人userName
     */
    void recv(String groupUserName, String content, boolean isAt, String userName);
}
