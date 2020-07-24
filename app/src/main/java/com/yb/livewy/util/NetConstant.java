package com.yb.livewy.util;

/**
 * created  by liu
 * on 2020-01-17 10:46
 * 常量
 */
public class NetConstant {

    /**
     * 网络方面的常量
     */
    public static final int API_CONNECT_TIME_OUT = 10;
    public static final int API_READ_TIME_OUT = 10;
    public static final int API_WRITE_TIME_OUT = 10;
    public final static String API_SP_NAME_NET = "net";
    public final static String API_SP_KEY_NET_COOKIE_SET = "cookie_set";
    public final static String API_SP_KEY_NET_HEADER_AUTH = "header_auth";
    public final static int REQUEST_SUCCESS_CODE = 200;
    public final static int REQUEST_LOADING_CODE = 233;
    public final static long appId = 2882303761518351873L;
    public final static String appKey = "5891835178873";
    public final static String appSecret = "yLzJFm0VG8BVReVgwSGbfg==";
    public final static String regionKey = "REGION_CN";
    public static int TIMEOUT_ON_LAUNCHED = 1 * 30 * 1000;
    public static int STATE_TIMEOUT = 0;
    public static int STATE_AGREE = 1;
    public static int STATE_REJECT = 2;
    public static int STATE_INTERRUPT = 3;
    public static Object lock = new Object();
    public static final String audio = "AUDIO";
    public static final String video = "VIDEO";
    public static volatile int answer = NetConstant.STATE_TIMEOUT;

    public final static int EXIT_LIVE = 100;

    public static final String IM_LOGIN_FAILED = "聊天登陆失败";
    public static final String IM_LOGIN_SUCCESS = "聊天登陆成功";
    public static final String IM_APP_KEY = "5c35b9162148f25b125e4af2d052134d";

    public static final String PARAM_ERROR = "参数错误";

    /**
     * 正则校验
     */
    //常规手机号验证
    public static final String REGULAR_PHONE =  "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])|(18[0-9])|(19[8,9]))\\d{8}$";
    //邮箱校验
    public static final String REGULAR_EMAIL = "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

    /**
     * 常规常量
     */
    public static final String ERROR_NOTINPUT = "请输入账号以及密码";
    public static final String ERROR_NETWORK = "网络错误，请检查";
    public static final String ERROR_SERVICES = "服务器链接异常，请稍后重试";
    public static final String ERROR_EXCEPTION = "恭喜你，成功捕捉到一只虫子,请重启应用，帮我们赶走它";
    public static final String SUCCESS_VERICODE = "验证码已发送";
    public static final String ERROR_VERICODE = "验证码发送失败";
    public static final String ERROR_NOPHONECONENT = "请输入手机号";
    public static final String ERROR_NOTPHONE = "手机号格式错误";
    public static final String ERROR_NOTDIGIT = "%1$s的长度是%2$s位哦";
    public static final int NUMBER_FOUR = 4;
    public static final int NUMBER_SIX = 6;
    public static final String ERROR_INPUTCONTENT = "请输入%s";
    public static final String ERROR_AGAINCONTENT = "请再次输入%s";
    public static final String ERROR_PASSWORDNOTSAME = "两次输入的密码不一致";
    public static final int ANIMDURATION = 500;
    public static final String PLEASEINPUTROOMTITLE = "请输入房间标题";
    public static final String UPLOADCOVER = "请上传封面，优秀的封面会得到官方推荐首页哦";
    public static final String REGISTER_SUCCESS = "注册成功";
    //发送的文本信息
    public static final int SEND_TEXT_VIEW = 0;
    //收到的文本消息
    public static final int RECEIVE_TEXT_VIEW = 1;
    //发送图片
    public static final int SEND_PIC_VIEW = 2;
    //接受图片
    public static final int RECEIVE_PIC_VIEW = 3;
    //发送视频
    public static final int SEND_VIDEO_VIEW = 4;
    //接受视频
    public static final int RECEIVE_VIDEO_VIEW = 5;
    //发送文件
    public static final int SEND_FILE_VIEW = 6;
    //接受文件
    public static final int RECEIVE_FILE_VIEW = 7;
    public static int id = 100;

    public static final String LOADING = "正在加载，请稍后";

    public static final String START_LIVEING = "正在开启直播，请稍后";

    public static final String INIT_LIVEING = "初始化直播中，请稍后";

    public static final String LIVE_FAILED = "直播开启失败，请重新登陆后重试";

    public static final String STOP_LIVE = "停止直播中";

    public static final String STOP_LIVE_FINISH = "停止直播完成";

    public static final String INIT_LIVE_ERROR = "初始化直播出错，正在退出";

    public static final String START_LIVE_ERROR = "开始直播出错";

    public static final String STOP_LIVE_ERROR = "停止直播出错，正在退出";

    public static final String AUDIO_DEAL_ERROR = "音频处理出错";

    public static final String VIDEO_DEAL_ERROR = "视频处理出错";

    public static final String CANT_NOT_OPEN_CAMERA = "打开相机出错，请允许该权限";

    public static final String CANT_NOT_OPEN_AUDIO = "无法开始录音，请检查权限是否允许";

    public static final String NETWORK_CAN_CONNECT = "网络断开了呢，请连接网络后重试";

    public static final String SERVICE_ERROR = "连接服务器出现错误，请稍后重试";

    public static final String NETWORK_UPLOAD_SLOW = "网络不太好哦，已经自动降低上传画质";

    public static final String START_LIVE_SUCCESS = "直播已经开始了哦";

    public static final String TOKEN_FILED = "登录失效，请重新登录";

    public static final int TOKEN_CODE = 400000;

    public static final String TOKEN_FAILED = "登录失效，请重新登录";

    public static final String COVER_FAILED = "封面加载错误，请重新选择";

    public static final String NOT_INPUT_LIVE_TITLE = "请输入直播间标题";

    public static final String EXIT_LIVE_MESSAGE = "下播";

    public static final String NOT_INPUT_SOMETHING = "不可以发送空消息哦";

    public static final String CHAT_ROOM_TEXT_MESSAGE = "聊天室文本消息";

    public static final String CUSTOM_SHARE_LIVE_MESSAGE = "直播间分享消息";

    public static final String SHARE_DONE = "分享完成";

    public static final String START_LIVE_CAN_SHARE = "开始直播后才可以分享哦";





}
