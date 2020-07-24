package com.yb.livewy.ui.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

public class LiveCustomAttachParser implements MsgAttachmentParser {

    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";

    @Override
    public MsgAttachment parse(String attach) {

        LiveCustomAttachment liveCustomAttachment = null;
        try {
            JSONObject object = JSON.parseObject(attach);
            int type = object.getInteger(KEY_TYPE);
            JSONObject data = object.getJSONObject(KEY_DATA);
            switch (type){
                case 1:
                    liveCustomAttachment = new LiveShareMessageAttachment();
                    break;
            }
            if (liveCustomAttachment!=null){
                liveCustomAttachment.fromJson(data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liveCustomAttachment;
    }
    public static String packData(int type, JSONObject data) {
        JSONObject object = new JSONObject();
        object.put(KEY_TYPE,type);
        if (data != null){
            object.put(KEY_DATA,data);
        }
        return object.toJSONString();
    }
}
