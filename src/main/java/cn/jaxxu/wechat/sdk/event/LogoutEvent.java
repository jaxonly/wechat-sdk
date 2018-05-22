package cn.jaxxu.wechat.sdk.event;

@FunctionalInterface
public interface LogoutEvent {
    /**
     * 用户退出后响应事件
     */
    void logout();
}
