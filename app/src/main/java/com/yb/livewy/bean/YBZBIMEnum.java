package com.yb.livewy.bean;

import android.app.NotificationManager;

/**
 * create by liu
 * on 2020-02-24 22:11
 **/
public class YBZBIMEnum {

    public enum LineState{

        //在线
        ONLINE("online",0),
        //离线
        OFFLINE("offline",1),
        //隐身
        INVISIBLE("invisible",2),
        //离开
        LEAVE("leave",3),
        //忙碌
        BEBUSY("bebusy",4);




        private String typeName;
        private int typeValue;

        LineState(String typeName, int typeValue){
            this.typeName = typeName;
            this.typeValue = typeValue;
        }

    }
    public enum MessageType{

        REFRESH_HOME_DATA("刷新首页数据",0),
        REFRESH_HOME_THEME("刷新首页主题",1),
        REFRSH_UI_NIGHT("夜间模式",2),
        OPEN_HOME_SIDE("打开侧滑栏",3),
        SINGLEMESSAGE("收到单聊信息",4),
        SENDSINGLEMESSAGETIMEOUT("发送单聊信息超时",5),
        GROUPMESSAGE("收到群聊信息",6),
        SENDGROUPMESSAGETIMEOUT("发送群聊超时信息",7),
        ONLINEMESSAGE("收到好友上线信息",8),
        CALLMESSAGE("收到通话请求信息",9),
        SERVICEMESSAGE("收到服务器已经收到信息",10),
        SENDUNLIMITEDGROUPMESSAGE("收到无限大群消息",11),
        SENDUNLIMITEDGROUPMESSAGETIMEOUT("发送无限大群消息超时",12),
        FRIENDSONLINEMESSAGE("收到好友上线消息",13),
        CALLAGREEDMESSAGE("会话接通",14),
        CALLCLOSEDMESSAGE("会话关闭",15),
        CALLDATAMESSAGE("会话数据",16),
        BEAUTYMODECHANGE("修改美颜强度",17),
        UPDATEFILTERTYPE("更改滤镜",18),
        CHOOSEFILTER("选择的滤镜",19),
        FILTERSTRENGTH("滤镜强度",20),
        BEAUTYSTRENGTH("美颜强度",21);


        private String typeName;
        private int typeValue;
        MessageType(String typeName, int typeValue){
            this.typeName = typeName;
            this.typeValue = typeValue;
        }

        public String getTypeName() {
            return typeName;
        }

        public int getTypeValue() {
            return typeValue;
        }
    }

    public enum QuestionCode{

        LOGIN_FAIL("登陆失败",10001),
        LOGIN_SUCCESS("登陆成功",10002);

        private String codeName;
        private int codeValue;
        QuestionCode(String codeName, int codeValue){
            this.codeName = codeName;
            this.codeValue = codeValue;
        }

        public String getCodeName() {
            return codeName;
        }

        public int getCodeValue() {
            return codeValue;
        }
    }

    public enum NotificationCode{

        FRIENDS_MESSAGE(10001,"10011","好友消息通知", NotificationManager.IMPORTANCE_MAX),
        GROUP_MESSAGE(10002,"10022","群消息通知", NotificationManager.IMPORTANCE_DEFAULT),
        AD_MESSAGE(10003,"10033","好友消息通知", NotificationManager.IMPORTANCE_HIGH);


        private int notifId;
        private String channelId;
        private String notifName;
        private int importantlevel;

        NotificationCode(int notifId, String channelId, String notifName, int importantlevel){
            this.notifId = notifId;
            this.channelId = channelId;
            this.notifName = notifName;
            this.importantlevel = importantlevel;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelId() {
            return channelId;
        }

        public int getNotifId() {
            return notifId;
        }

        public void setNotifId(int notifId) {
            this.notifId = notifId;
        }

        public String getNotifName() {
            return notifName;
        }

        public void setNotifName(String notifName) {
            this.notifName = notifName;
        }

        public int getImportantlevel() {
            return importantlevel;
        }

        public void setImportantlevel(int importantlevel) {
            this.importantlevel = importantlevel;
        }
    }

    public enum  Status {
        SUCCESS,
        ERROR,
        LOADING
    }






}
