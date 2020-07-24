package com.yb.livewy.net;

/**
 * create by liu
 * on 2020/4/10 2:25 PM
 **/
public class YBRYIMURL {

    public static final String BaseUrl = "http://192.168.0.103:9000/";

    //账号密码登陆
    public static final String login = "api/loginByPassword";

    //注册
    //public static final String REGISTER = "zb/user/register";
    public static final String REGISTER = "api/loginByPassword";

    //获取推流地址
    //public static final String GETPULLRTMP = "zb/zbManage/createChannel";
    public static final String GETPULLRTMP = "live/video/createChannel";
    //创建聊天室
    public static final String CreateLiveRoom = "createImRoom";
    //获取聊天账号
    public static final String getChatAccount = "createIm";

    //关闭直播间
    public static final String closeLive = "live/video/modifyLiveStauts";

    //获取好友列表
    public static final String getFriendsList = "live/api/link/findLinkUser";



}
