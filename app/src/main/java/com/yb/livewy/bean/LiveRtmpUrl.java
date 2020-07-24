package com.yb.livewy.bean;

import java.io.Serializable;

public class LiveRtmpUrl implements Serializable {


    /**
     * id : 19
     * cid : ee7720efb272484bb65a35d240a865b7
     * name : 善良
     * pushUrl : rtmp://p591d6e0f.live.126.net/live/ee7720efb272484bb65a35d240a865b7?wsSecret=eae20d13e484ed132610268397e21d2e&wsTime=1592376174
     * httpPullUrl : http://flv591d6e0f.live.126.net/live/ee7720efb272484bb65a35d240a865b7.flv?netease=flv591d6e0f.live.126.net
     * hlsPullUrl : http://pullhls591d6e0f.live.126.net/live/ee7720efb272484bb65a35d240a865b7/playlist.m3u8
     * rtmpPullUrl : rtmp://v591d6e0f.live.126.net/live/ee7720efb272484bb65a35d240a865b7
     * type : null
     * cover_img : null
     * room_id:2
     */

    private int id;
    private String cid;
    private String name;
    private String pushUrl;
    private String httpPullUrl;
    private String hlsPullUrl;
    private String rtmpPullUrl;
    private int type;
    private String cover_img;
    private int room_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", cid='" + cid + '\'' +
                ", name='" + name + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", httpPullUrl='" + httpPullUrl + '\'' +
                ", hlsPullUrl='" + hlsPullUrl + '\'' +
                ", rtmpPullUrl='" + rtmpPullUrl + '\'' +
                ", type=" + type +
                ", cover_img='" + cover_img + '\'' +
                ", room_id=" + room_id +
                '}';
    }
}
