package com.yb.livewy.bean;

public class RTMP{


    /**
     * cid : d8be163c1b3640c1bc30338e9da451b9
     * ctime : 1591953480090
     * name : 242
     * pushUrl : rtmp://p591d6e0f.live.126.net/live/d8be163c1b3640c1bc30338e9da451b9?wsSecret=bbc866dfd181f2439bb919dbcd090478&wsTime=1591953480
     * httpPullUrl : http://flv591d6e0f.live.126.net/live/d8be163c1b3640c1bc30338e9da451b9.flv?netease=flv591d6e0f.live.126.net
     * hlsPullUrl : http://pullhls591d6e0f.live.126.net/live/d8be163c1b3640c1bc30338e9da451b9/playlist.m3u8
     * rtmpPullUrl : rtmp://v591d6e0f.live.126.net/live/d8be163c1b3640c1bc30338e9da451b9
     * type : null
     */

    private String cid;
    private long ctime;
    private String name;
    private String pushUrl;
    private String httpPullUrl;
    private String hlsPullUrl;
    private String rtmpPullUrl;
    private Object type;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
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

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RTMP{" +
                "cid='" + cid + '\'' +
                ", ctime=" + ctime +
                ", name='" + name + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", httpPullUrl='" + httpPullUrl + '\'' +
                ", hlsPullUrl='" + hlsPullUrl + '\'' +
                ", rtmpPullUrl='" + rtmpPullUrl + '\'' +
                ", type=" + type +
                '}';
    }
}
