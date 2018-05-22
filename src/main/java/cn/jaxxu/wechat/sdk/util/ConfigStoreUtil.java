package cn.jaxxu.wechat.sdk.util;

import cn.jaxxu.wechat.sdk.model.BaseRequest;
import cn.jaxxu.wechat.sdk.model.BaseRequestUinInt;
import cn.jaxxu.wechat.sdk.model.InitInfoRespEntity;
import cn.jaxxu.wechat.sdk.model.LoginSignInfoRespEntity;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigStoreUtil {

    /**
     * 存储Cookie
     * key为域名
     */
    public static List<Cookie> cookieStore = new ArrayList<>();

    /**
     * 存储Cookie
     * key为域名
     */
    public static Map<String, String> cookieStoreMap = new ConcurrentHashMap<>();

    /**
     * 登陆后获取到凭证信息
     */
    public static LoginSignInfoRespEntity signInfo;

    /**
     * 基本请求信息UinInt
     */
    public static BaseRequestUinInt baseRequestUinInt;

    /**
     * 基本请求信息
     */
    public static BaseRequest baseRequest;

    /**
     * 初始化信息
     */
    public static InitInfoRespEntity initInfoRespEntity;

    /**
     * 设备Id
     */
    public static String deviceId;
}
