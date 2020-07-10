package com.yb.livewy.bean;

public class ChatAccount {

    /**
     * id : null
     * user_id : 30
     * accid : 061515452323315
     * token : a6a9c36c6a594c864488cdf9171d6739
     * create_time : 1592207123986
     * modify_time : null
     */

    private Object id;
    private int user_id;
    private String accid;
    private String token;
    private long create_time;
    private Object modify_time;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public Object getModify_time() {
        return modify_time;
    }

    public void setModify_time(Object modify_time) {
        this.modify_time = modify_time;
    }

    @Override
    public String toString() {
        return "ChatAccount{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", accid='" + accid + '\'' +
                ", token='" + token + '\'' +
                ", create_time=" + create_time +
                ", modify_time=" + modify_time +
                '}';
    }
}
