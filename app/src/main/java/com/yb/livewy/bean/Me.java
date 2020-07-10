package com.yb.livewy.bean;

/**
 * create by liu
 * on 2020/4/15 2:46 PM
 **/
public class Me {


    /**
     * userid : 327
     * nickName : 18339355729
     * name : 122
     * type : 1
     */

    private int userid;
    private String nickName;
    private String name;
    private String type;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Me{" +
                "userid=" + userid +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
