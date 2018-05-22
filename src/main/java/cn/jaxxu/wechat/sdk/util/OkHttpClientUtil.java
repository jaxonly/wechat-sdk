package cn.jaxxu.wechat.sdk.util;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.jaxxu.wechat.sdk.util.ConfigStoreUtil.cookieStoreMap;

public class OkHttpClientUtil {

    /**
     * 默认超时时间
     */
    private static final int DEFAULT_TIME_OUT = 10;

    public static OkHttpClient getClent(int timeout) {
        return new OkHttpClient.Builder()
                //设置读取超时时间
                .readTimeout(timeout, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        ConfigStoreUtil.cookieStore = cookies;
                        for (Cookie cookie : cookies) {
                            cookieStoreMap.put(cookie.name(), cookie.value());
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = ConfigStoreUtil.cookieStore;
                        return cookies != null ? cookies : new ArrayList<>();
                    }
                })
                .build();
    }

    public static OkHttpClient getClent() {
        return getClent(DEFAULT_TIME_OUT);
    }
}
