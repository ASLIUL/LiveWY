package com.yb.livewy.bean;

import java.io.Serializable;

public class LiveVideoBean implements Serializable {


    /**
     * id : 19
     * cover_img : http://192.168.0.100:8080/img/live/1594192494970-19763.jpg
     * httpPullUrl : http://flv591d6e0f.live.126.net/live/ee7720efb272484bb65a35d240a865b7.flv?netease=flv591d6e0f.live.126.net
     * hlsPullUrl : http://pullhls591d6e0f.live.126.net/live/ee7720efb272484bb65a35d240a865b7/playlist.m3u8
     * rtmpPullUrl : rtmp://v591d6e0f.live.126.net/live/ee7720efb272484bb65a35d240a865b7
     * name : 更高
     * room_id : null
     * user_id : 12
     * userName : adasds
     * headImg : http://192.168.0.100:8080/img/1592981195716-28459.jpg
     * fansNum : 0
     * isFocus : 1
     */

    private int id;
    private String cover_img;
    private String httpPullUrl;
    private String hlsPullUrl;
    private String rtmpPullUrl;
    private String name;
    private String room_id;
    private int user_id;
    private String userName;
    private String headImg;
    private int fansNum;
    private int isFocus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getHttpPullUrl() {
        return httpPullUrl;
    }

    public void setHttpPullUrl(String httpPullUrl) {
        this.httpPullUrl = httpPullUrl;
    }

    public String getHlsPullUrl() {
        return hlsPullUrl;
    }

    public void setHlsPullUrl(String hlsPullUrl) {
        this.hlsPullUrl = hlsPullUrl;
    }

    public String getRtmpPullUrl() {
        return rtmpPullUrl;
    }

    public void setRtmpPullUrl(String rtmpPullUrl) {
        this.rtmpPullUrl = rtmpPullUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }
}
