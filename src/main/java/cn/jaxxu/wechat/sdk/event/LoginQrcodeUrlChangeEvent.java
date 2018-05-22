package cn.jaxxu.wechat.sdk.event;

@FunctionalInterface
public interface LoginQrcodeUrlChangeEvent {
    /**
     * uuid过期后，刷新二维码连接响应事件
     *
     * @param oldUrl 老连接
     * @param newUrl 新连接
     */
    void change(String oldUrl, String newUrl);
}
