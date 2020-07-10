package com.yb.livewy.bean;

/**
 * create by liu
 * on 2020/5/9 11:36 AM
 **/
public class LiveChatMessageBean {

    private String name;
    private String content;

    public LiveChatMessageBean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LiveChatMessageBean{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
