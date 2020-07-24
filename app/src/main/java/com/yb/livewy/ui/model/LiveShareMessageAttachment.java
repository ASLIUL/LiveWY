package com.yb.livewy.ui.model;

import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.yb.livewy.bean.LiveShareMessageBean;
import com.yb.livewy.ui.inter.LiveCustomAttachmentType;

public class LiveShareMessageAttachment extends LiveCustomAttachment {

    private final String KEY_DATA = "data";

    protected LiveShareMessageBean liveShareMessageBean;

    LiveShareMessageAttachment() {
        super(LiveCustomAttachmentType.ShareLive);
    }

    public LiveShareMessageAttachment(LiveShareMessageBean liveShareMessageBean){
        this();
        this.liveShareMessageBean = liveShareMessageBean;
    }


    @Override
    protected void parseData(JSONObject data) {
        this.liveShareMessageBean = data.getObject(KEY_DATA,LiveShareMessageBean.class);
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put(KEY_DATA,liveShareMessageBean);
        return data;
    }

    public LiveShareMessageBean getData() {
        return liveShareMessageBean;
    }

    public static CustomMessageConfig getConfig(){
        // 消息的配置选项
        CustomMessageConfig config = new CustomMessageConfig();
        // 该消息不保存到服务器
        config.enableHistory = true;
        // 该消息不漫游
        config.enableRoaming = true;
        // 该消息不同步
        config.enableSelfSync = true;
        config.enablePersist = true;
        config.enablePush = true;
        config.enablePushNick = true;
        config.enableRoaming = true;
        config.enableUnreadCount =true;
        config.enableRoute = true;
        return config;
    }

}
