package cn.jaxxu.wechat.sdk.event;

@FunctionalInterface
public interface LoginSuccessEvent {
    /**
     * 用户登录成功后响应事件
     */
    void userLoginSuccess();
}
