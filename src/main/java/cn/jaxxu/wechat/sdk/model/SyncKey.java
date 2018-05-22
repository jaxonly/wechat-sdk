package cn.jaxxu.wechat.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 与微信获取信息的实体
 */
@Data
public class SyncKey {

    @JSONField(name = "Count")
    private int count;

    @JSONField(name = "List")
    private List<SyncKeyItem> list;

    @Data
    private class SyncKeyItem {

        @JSONField(name = "Key")
        private int key;

        @JSONField(name = "Val")
        private int val;
    }

    /**
     * 缓存
     */
    private String listStrTmp;

    public String listStr() {
        if (listStrTmp != null) {
            return listStrTmp;
        }
        StringBuilder listStr = new StringBuilder();
        for (SyncKeyItem syncKeyItem : list) {
            listStr.append("|").append(syncKeyItem.getKey()).append("_").append(syncKeyItem.getVal());
        }
        listStrTmp = listStr.substring(1);
        return listStrTmp;
    }
}
