package cn.jaxxu.wechat.sdk.event;

@FunctionalInterface
public interface LoginQrcodeUrlLoadEvent {
    /**
     * 得到二维码连接后响应事件
     */
    void load(String url);
}
