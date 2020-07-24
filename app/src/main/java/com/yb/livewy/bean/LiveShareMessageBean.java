package com.yb.livewy.bean;

import java.io.Serializable;

public class LiveShareMessageBean implements Serializable {

    //标示
    private int code;
    //信息
    private String msg;
    //分享人的id
    private String shareUserId;
    //直播间的封面
    private String liveCover;
    //直播标题
    private String liveTitle;
    //直播间id
    private String liveId;
    //分享给对方的id
    private String toAccount;

    //主播的用户名
    private String liveUsername;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(String shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getLiveCover() {
        return liveCover;
    }

    public void setLiveCover(String liveCover) {
        this.liveCover = liveCover;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getLiveUsername() {
        return liveUsername;
    }

    public void setLiveUsername(String liveUsername) {
        this.liveUsername = liveUsername;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", shareUserId='" + shareUserId + '\'' +
                ", liveCover='" + liveCover + '\'' +
                ", liveTitle='" + liveTitle + '\'' +
                ", liveId='" + liveId + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", liveUsername='" + liveUsername + '\'' +
                '}';
    }
}
