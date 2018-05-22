package cn.jaxxu.wechat.sdk.event;

import cn.jaxxu.wechat.sdk.model.InitInfoRespEntity;

@FunctionalInterface
public interface InitUserDataEvent {
    /**
     * 初始化数据后响应事件
     */
    void initUserData(InitInfoRespEntity initInfoRespEntity);
}
