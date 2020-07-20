package com.yb.livewy.bean;

import java.io.Serializable;

public class ChatRoomMsg implements Serializable {

    //聊天账户
    private String chatAccount;
    //用户名字
    private String userName;
    //发送的文本内容
    private String chatConnect;
    //用户登记
    private int userLevel;
    //用户身份，管理员，主播等
    private int userType;

    public ChatRoomMsg(String chatAccount, String userName, String chatConnect, int userLevel, int userType) {
        this.chatAccount = chatAccount;
        this.userName = userName;
        this.chatConnect = chatConnect;
        this.userLevel = userLevel;
        this.userType = userType;
    }

    public String getChatAccount() {
        return chatAccount;
    }

    public void setChatAccount(String chatAccount) {
        this.chatAccount = chatAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChatConnect() {
        return chatConnect;
    }

    public void setChatConnect(String chatConnect) {
        this.chatConnect = chatConnect;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "{" +
                "chatAccount='" + chatAccount + '\'' +
                ", userName='" + userName + '\'' +
                ", chatConnect='" + chatConnect + '\'' +
                ", userLevel=" + userLevel +
                ", userType=" + userType +
                '}';
    }
}
