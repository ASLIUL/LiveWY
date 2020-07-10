package com.yb.livewy.bean;

/**
 * create by liu
 * on 2020/3/29 3:23 PM
 **/
public class MessageEvent {

    private YBZBIMEnum.MessageType messageType;
    private Object object;

    public MessageEvent(YBZBIMEnum.MessageType messageType, Object object) {
        this.messageType = messageType;
        this.object = object;
    }

    public MessageEvent(YBZBIMEnum.MessageType messageType){
        this.messageType = messageType;
    }

    public YBZBIMEnum.MessageType getMessageType() {
        return messageType;
    }

    public Object getObject() {
        return object;
    }
}
