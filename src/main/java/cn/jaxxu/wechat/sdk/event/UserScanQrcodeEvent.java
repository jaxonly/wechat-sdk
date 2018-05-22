package cn.jaxxu.wechat.sdk.event;

@FunctionalInterface
public interface UserScanQrcodeEvent {
    /**
     * 用户扫描二维码后响应事件
     *
     * @param userAvatar 此处返回前端Img标签使用的Base64路径，带'data:img/jpg;base64,'前缀
     */
    void userScan(String userAvatar);
}
