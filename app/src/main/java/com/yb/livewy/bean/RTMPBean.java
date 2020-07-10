package com.yb.livewy.bean;

/**
 * create by liu
 * on 2020/4/15 2:46 PM
 **/
public class RTMPBean {

    /**
     * id : 20
     * cid : c1067f648d884316b5e4397c67230d9a
     * ctime : 1587544049911
     * name : 今天测试4
     * pushurl : rtmp://p29463a16.live.126.net/live/c1067f648d884316b5e4397c67230d9a?wsSecret=a74a5a9e062ae49c4524a657f7259dbc&wsTime=1587544049
     * httppullurl : http://flv29463a16.live.126.net/live/c1067f648d884316b5e4397c67230d9a.flv?netease=flv29463a16.live.126.net
     * hlspullurl : http://pullhls29463a16.live.126.net/live/c1067f648d884316b5e4397c67230d9a/playlist.m3u8
     * rtmppullurl : rtmp://v29463a16.live.126.net/live/c1067f648d884316b5e4397c67230d9a
     * userid : 111
     * roomid : 104
     * datatime : 2020-04-22 16:27:29
     * channelstate : 0
     * coverurl : 北冰洋
     * livetype : 1
     * identifier : fefee0772d80487f9facd6dc0acca48b
     * deleted : 0
     */

    private int id;
    private String cid;
    private long ctime;
    private String name;
    private String pushurl;
    private String httppullurl;
    private String hlspullurl;
    private String rtmppullurl;
    private int userid;
    private int roomid;
    private String datatime;
    private String channelstate;
    private String coverurl;
    private String livetype;
    private String identifier;
    private String deleted;

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

    public String getPushurl() {
        return pushurl;
    }

    public void setPushurl(String pushurl) {
        this.pushurl = pushurl;
    }

    public String getHttppullurl() {
        return httppullurl;
    }

    public void setHttppullurl(String httppullurl) {
        this.httppullurl = httppullurl;
    }

    public String getHlspullurl() {
        return hlspullurl;
    }

    public void setHlspullurl(String hlspullurl) {
        this.hlspullurl = hlspullurl;
    }

    public String getRtmppullurl() {
        return rtmppullurl;
    }

    public void setRtmppullurl(String rtmppullurl) {
        this.rtmppullurl = rtmppullurl;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getChannelstate() {
        return channelstate;
    }

    public void setChannelstate(String channelstate) {
        this.channelstate = channelstate;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getLivetype() {
        return livetype;
    }

    public void setLivetype(String livetype) {
        this.livetype = livetype;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "RTMPBean{" +
                "id=" + id +
                ", cid='" + cid + '\'' +
                ", ctime=" + ctime +
                ", name='" + name + '\'' +
                ", pushurl='" + pushurl + '\'' +
                ", httppullurl='" + httppullurl + '\'' +
                ", hlspullurl='" + hlspullurl + '\'' +
                ", rtmppullurl='" + rtmppullurl + '\'' +
                ", userid=" + userid +
                ", roomid=" + roomid +
                ", datatime='" + datatime + '\'' +
                ", channelstate='" + channelstate + '\'' +
                ", coverurl='" + coverurl + '\'' +
                ", livetype='" + livetype + '\'' +
                ", identifier='" + identifier + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }
}
