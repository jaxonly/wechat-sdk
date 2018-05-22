package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 与微信获取信息的实体
 */
@Data
public class MPSubscribeMsg {

    /**
     * 公众号userName
     */
    @JSONField(name = "UserName")
    private String userName;

    /**
     * 更新文章数量
     */
    @JSONField(name = "MPArticleCount")
    private String mpArticleCount;

    /**
     * 文章详情列表
     */
    @JSONField(name = "MPArticleList")
    private List<MPArticle> mpArticleList;

    /**
     * 更新时间戳
     */
    @JSONField(name = "Time")
    private int time;
    /**
     * 公众号昵称
     */
    @JSONField(name = "NickName")
    private String nickName;

    private class MPArticle {

        /**
         * 文章标题
         */
        @JSONField(name = "Title")
        private String title;

        /**
         * 文章简介
         */
        @JSONField(name = "Digest")
        private String digest;

        /**
         * 文章图片地址
         */
        @JSONField(name = "Cover")
        private String cover;

        /**
         * 文章地址
         */
        @JSONField(name = "Url")
        private String url;
    }
}
