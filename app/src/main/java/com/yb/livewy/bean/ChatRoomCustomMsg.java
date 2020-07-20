package com.yb.livewy.bean;

import java.io.Serializable;

public class ChatRoomCustomMsg implements Serializable {

    private String msg;
    private int code;
    private MsgData data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MsgData getData() {
        return data;
    }

    public void setData(MsgData data) {
        this.data = data;
    }

    public static class MsgData{
        private String connect;
        private String username;
        private String userId;
        private String chatAccount;
        private int level;
        private int userType;

        public MsgData(String connect, String username, String userId, String chatAccount, int level, int userType) {
            this.connect = connect;
            this.username = username;
            this.userId = userId;
            this.chatAccount = chatAccount;
            this.level = level;
            this.userType = userType;
        }

        public MsgData() {
        }

        public String getConnect() {
            return connect;
        }

        public void setConnect(String connect) {
            this.connect = connect;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getChatAccount() {
            return chatAccount;
        }

        public void setChatAccount(String chatAccount) {
            this.chatAccount = chatAccount;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        @Override
        public String toString() {
            return "MsgData{" +
                    "connect='" + connect + '\'' +
                    ", username='" + username + '\'' +
                    ", userId='" + userId + '\'' +
                    ", chatAccount='" + chatAccount + '\'' +
                    '}';
        }
    }

    public ChatRoomCustomMsg(String msg, int code, MsgData data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public ChatRoomCustomMsg() {
    }

    @Override
    public String toString() {
        return "ChatRoomCustomMsg{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data.toString() +
                '}';
    }
}
