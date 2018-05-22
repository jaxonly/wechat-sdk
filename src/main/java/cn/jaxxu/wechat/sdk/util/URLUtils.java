package cn.jaxxu.wechat.sdk.util;

import lombok.SneakyThrows;

import java.net.URLDecoder;

public class URLUtils {

    @SneakyThrows
    public static String decode(String url) {
        return URLDecoder.decode(url, "UTF-8");
    }
}
