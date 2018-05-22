package cn.jaxxu.wechat.sdk.event;

public interface TextRecvResponseEvent{

    /**
     * 文字信息响应事件
     * @param fromUserName 发送人UserName
     * @param content 发送内容
     */
    void recv(String fromUserName, String content);
}
