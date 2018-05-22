package cn.jaxxu.wechat.sdk.model;

import lombok.Data;

/**
 * 微信查询登录接口返回信息实体
 */
@Data
public class LoginStatusInfoRespEntity {

    /**
     * 响应代码
     * <p>
     * 200 用户登录成功，redirectUri 会返回当前需要跳转 url ，可以拿此 url 获得凭证信息
     * 201 用户扫码事件，userAvatar 会返回当前用户的 Base64 编码头像，用于前端展示
     * 408 用户未操作事件，此时重新请求
     * 400 该 uuid 已超时，需重新获取
     */
    private String code;

    /**
     * 微信接口返回重定向地址，地址后面加上 &fun=new&version=v2 返回可得到 XML 格式的凭证信息
     */
    private String redirectUri;

    /**
     * 用户扫码事件，返回的用户头像 Base64 编码头像
     * 格式为 'data:img/jpg;base64,' 需处理后使用
     */
    private String userAvatar;
}
