package com.yb.livewy.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.effect.Effect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.netease.LSMediaCapture.lsAudioCaptureCallback;
import com.netease.LSMediaCapture.lsLogUtil;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.LSMediaCapture.lsMediaCapture.StreamType;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.LSMediaCapture.video.VideoCallback;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.vcloud.video.effect.VideoEffect;
import com.yb.livewy.R;
import com.yb.livewy.bean.Beauty;
import com.yb.livewy.bean.MessageEvent;
import com.yb.livewy.bean.PublishParam;
import com.yb.livewy.bean.RTMP;
import com.yb.livewy.bean.YBZBIMEnum;
import com.yb.livewy.databinding.ActivityLiveStreamBinding;
import com.yb.livewy.ui.adapter.ChatMessageRecyclerAdapter;
import com.yb.livewy.util.AlertDialogUtil;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.SaveUserData;
import com.yb.livewy.util.SystemUtil;
import com.yb.livewy.util.ToastUtil;
import com.yb.livewy.vm.LiveStreamingViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * create by liu
 * on 2020/4/13 3:03 PM
 **/
public class LiveStreamingActivity extends BaseAppActivity<ActivityLiveStreamBinding, LiveStreamingViewModel> implements lsMessageHandler, DialogInterface.OnDismissListener {

    //商汤的滤镜
    private Effect effect;
    /* SDK 相关参数 */
    private boolean userFilter;
    private boolean needWater = false;
    private boolean needGraffiti = false;
    private lsMediaCapture capture = null;
    private lsMediaCapture.LiveStreamingPara liveStreamingPara;
    private ArrayList<IMMessage> chatMessageBeans = new ArrayList<>();
    private ChatMessageRecyclerAdapter chatMessageRecyclerAdapter;

    //消息回调
    private Handler handler;
    //帧率数值
    private int MSG_FPS = 1000;

    private boolean videoCallback = false; //是否对相机采集的数据进行回调（用户在这里可以进行自定义滤镜等）
    private boolean audioCallback = false; //是否对麦克风采集的数据进行回调（用户在这里可以进行自定义降噪等）

    //状态变量
    private boolean liveStreamingOn = false;
    private boolean liveStreamingInitFinished = false;
    private boolean tryToStopLivestreaming = false;
    private boolean startVideoCamera = false;
    private Intent intentLiveStreamingStopFinished = new Intent("LiveStreamingStopFinished");
    //伴音相关
    private AudioManager audioManager;
    //网络相关
    //private Intent mNetInfoIntent = new Intent(NetWorkInfoDialog.NETINFO_ACTION);
    private long mLastVideoProcessErrorAlertTime = 0;
    private long mLastAudioProcessErrorAlertTime = 0;
    //视频截图相关变量
    private String mScreenShotFilePath = "/sdcard/";//视频截图文件路径
    private String mScreenShotFileName = "test.jpg";//视频截图文件名
    //视频缩放相关变量
    private int mMaxZoomValue = 0;
    private int mCurrentZoomValue = 0;
    private float mCurrentDistance;
    private float mLastDistance = -1;
    //Demo广播相关变量
    //private MsgReceiver msgReceiver;
    //private audioMixVolumeMsgReceiver audioMixVolumeMsgReceiver;
    private static final String TAG = "LiveStreamingActivity";

    private long clickTime = 0L;

    //广播
    private MessageReceiver messageReceiver = null;
    AudioMixVolumeMessageReceiver audioMixVolumeMessageReceiver = null;

    private PublishParam publishParam;

    private String roomTitle;
    private String userId;

    //美颜弹窗
    private AlertDialog alertDialog;
    //美颜程度
    private Beauty beauty = new Beauty(0,50,50);

    //滤镜弹窗
    private AlertDialog filterAlert;

    //检测是已经点击了开始的还是没有 ，默认没有
    private boolean isOnce = false;

    private int liveId = 0;

    @Override
    protected void initData() {
        binding.setData(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getJsonObjectMutableLiveData().observe(this,observer);

        //应用运行时保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0.7f;
        getWindow().setAttributes(params);

        userId = getIntent().getStringExtra("userId");
        liveId = getIntent().getIntExtra("liveId",0);

        //从上个页面获取直播信息
        publishParam = (PublishParam) getIntent().getSerializableExtra("data");
        Log.d(TAG, "initData: "+publishParam.toString());
        userFilter = publishParam.isUseFilter() & !videoCallback;
        needGraffiti = publishParam.isGraffitiOn();
        needWater = publishParam.isWatermark();

        staticHandle();
        //创建直播实例
        lsMediaCapture.LsMediaCapturePara para = new lsMediaCapture.LsMediaCapturePara();
        //设置SDK上下文
        para.setContext(getApplicationContext());
        //设置消息回调
        para.setMessageHandler(this);
        //设置日志级别
        para.setLogLevel(lsLogUtil.LogLevel.INFO);
        //是否上传日志
        para.setUploadLog(publishParam.isUploadLog());
        capture = new lsMediaCapture(para);

        //设置直播参数
        liveStreamingPara = new lsMediaCapture.LiveStreamingPara();
        liveStreamingPara.setStreamType(publishParam.getStreamType());
        liveStreamingPara.setFormatType(publishParam.getFormatType());
        liveStreamingPara.setRecordPath(publishParam.getRecordPath());
        liveStreamingPara.setQosOn(publishParam.isQosEnable());

        //是否是前置摄像头
        boolean frontCamera = publishParam.isFrontCamera();
        //是否强制16x9
        boolean scale_16x9 = publishParam.isScale_16x9();
        if (publishParam.getStreamType() != StreamType.AUDIO) {
            lsMediaCapture.VideoQuality videoQuality = publishParam.getVideoQuality();//视频清晰度
            capture.startVideoPreview(binding.netease, frontCamera, userFilter, videoQuality, scale_16x9);
        }

        startVideoCamera = true;
        if (userFilter) {
            //磨皮强度
            capture.setBeautyLevel(beauty.getGrinding()/20);
            //滤镜强度
            capture.setFilterStrength(beauty.getFilter()/100);
            capture.setFilterType(publishParam.getFilterType());
        }

        // SDK 默认提供 /** 标清 480*360 */MEDIUM, /** 高清 640*480 */HIGH,
        // /** 超清 960*540 */SUPER,/** 超高清 (1280*720) */SUPER_HIGH  四个模板，
        // 用户如果需要自定义分辨率可以调用startVideoPreviewEx 接口并参考以下参数
        // 码率计算公式为 width * height * fps * 9 /100;

//		lsMediaCapture.VideoPara para = new lsMediaCapture.VideoPara();
//		para.setHeight(720);
//		para.setWidth(1280);
//		para.setFps(15);
//		para.setBitrate(1200*1024);
//		mLSMediaCapture.startVideoPreviewEx(videoView,frontCamera,mUseFilter,para);

        //编码分辨率     建议码率
        //1280x720     1200kbps
        //960x720      1000kbps
        //960x540      800kbps
        //640x480      600kbps
        //640x360      500kbps
        //320x240      250kbps
        //320x180      200kbps

        //【示例代码】设置自定义视频采集类型(如果是自定义YUV则不需要调用startVideoPreview接口)
//			mLSMediaCapture.setSourceType(lsMediaCapture.SourceType.CustomAV);
//			//自定义输入默认是横屏，正的yuv数据

        //【示例代码 customVideo】设置自定义视频采集逻辑 （自定义视频采集逻辑不要调用startPreview，也不用初始化surfaceView）
//		new Thread() {  //视频采集线程
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						if(!m_liveStreamingOn){
//							continue;
//						}
//						int width = 352;
//						int height = 288;
//						int fps = 20;
//						int bitrate = width * height * fps * 9 /100;
//						FileInputStream in = new FileInputStream(String.format(Locale.getDefault(),"/sdcard/dump_%d_%d.yuv",width,height));
//						int len = width * height * 3 / 2;
//						byte buffer[] = new byte[len];
//						int count;
//						while ((count = in.read(buffer)) != -1) {
//							if (len == count) {
//								mLSMediaCapture.sendCustomYUVData(buffer,width,height,bitrate,fps);
//							} else {
//								break;
//							}
//							sleep(50, 0);
//						}
//						in.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}.start();
//		//【示例代码】结束
//
//		//【示例代码2】设置自定义音频采集逻辑（音频采样位宽必须是16）
//		new Thread() {  //音频采集线程
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//						if(!m_liveStreamingOn){
//							continue;
//						}
//                        FileInputStream in = new FileInputStream("/sdcard/dump.pcm");
//                        int len = 2048;
//                        byte buffer[] = new byte[len];
//                        int count;
//                        while ((count = in.read(buffer)) != -1) {
//                            if (len == count) {
//                                mLSMediaCapture.sendCustomPCMData(buffer);
//                            } else {
//                                break;
//                            }
//                            sleep(20, 0);
//                        }
//                        in.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
        //【示例代码】结束


        if (videoCallback) {
            senseEffect();
            capture.setCaptureRawDataCB(new VideoCallback() {
                /**
                 * 摄像头采集数据回调
                 * @param data 摄像头采集的原始数据(NV21格式)
                 * @param textureId  摄像头采集的纹理ID
                 * @param width 视频宽
                 * @param height 视频高
                 * @param orientation 相机采集角度
                 * @return 滤镜后的纹理ID (<=0 表示没有进行滤镜或是滤镜库返回的是buffer数据(NV21格式)，sdk将会使用buffer数据进行后续处理)
                 */
                @Override
                public int onVideoCapture(byte[] data, int textureId, int width, int height, int orientation) {
//                        if (effect !=null){
//                            //返回纹理方式
//                            //return effect.
//                        }
                    //返回纹理方式 //data必须还是原来的NV21格式
                    for (int j = 0; j < width * height / 4; j++)
                        data[j] = 0;
                    return 0;
                }
            });
        }

        //********** 麦克风采集原始数据回调（开发者可以修改该数据，进行降噪、回音消除等，推出的流便随之发生变化） *************//
        if (audioCallback) {
            capture.setAudioRawDataCB(new lsAudioCaptureCallback() {
                int i = 0;

                @Override
                public void onAudioCapture(byte[] data, int len) {
                    // 这里将data直接修改，SDK根据修改后的data数据直接推流
                    if (i % 10 == 0) {
                        for (int j = 0; j < 1000; j++) {
                            data[j] = 0;
                        }
                    }
                    i++;
                }
            });
        }

        if (publishParam.getStreamType() != StreamType.AUDIO) {
            //显示本地绘制帧率 可以不写
            handler.sendEmptyMessageDelayed(MSG_FPS, 1000);
        }

        //伴音操作 获取设备音频播放的service
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //动态注册广播 接受Service的 消息
        messageReceiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("AudioMix");
        registerReceiver(messageReceiver, intentFilter);

        //动态注册广播接收器，接受service消息
        audioMixVolumeMessageReceiver = new AudioMixVolumeMessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("AudioMixVolume");
        registerReceiver(audioMixVolumeMessageReceiver, filter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        binding.chatMsg.setLayoutManager(linearLayoutManager);
        chatMessageRecyclerAdapter = new ChatMessageRecyclerAdapter(this,chatMessageBeans);
        binding.chatMsg.setAdapter(chatMessageRecyclerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        roomTitle = getIntent().getStringExtra("roomTitle");
        //viewModel.getRTMPUrl(roomTitle);

        com.netease.nimlib.sdk.Observer<List<IMMessage>> listObserver = new com.netease.nimlib.sdk.Observer<List<IMMessage>>() {
            @Override
            public void onEvent(List<IMMessage> imMessages) {
                chatMessageBeans.addAll(imMessages);
                Log.d(TAG, "onEvent: "+imMessages.get(0).getContent());
                chatMessageRecyclerAdapter.notifyDataSetChanged();
                binding.chatMsg.smoothScrollToPosition(chatMessageBeans.size());
            }
        };
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(listObserver,true);
    }

    @Override
    protected void initViewListener() {
        binding.startLive.setOnClickListener(this);
        binding.changeCamera.setOnClickListener(this);
        binding.beautyMode.setOnClickListener(this);
        binding.musicList.setOnClickListener(this);
        binding.filterMode.setOnClickListener(this);
    }

    //开始直播
    private boolean startAV() {
        //初始化直播
        liveStreamingInitFinished = capture.initLiveStream(liveStreamingPara, publishParam.getPushUrl());
        if (capture != null && liveStreamingInitFinished) {
            capture.startLiveStreaming();
            liveStreamingOn = true;

            //添加水印参数
            if (needWater) {

            }
            //添加涂鸦参数
            if (needGraffiti) {

            }
            return true;
        }
        return liveStreamingInitFinished;
    }

    private void stopAV() {
        try {
            if (capture != null) {
                capture.stopLiveStreaming();
                Glide.with(this).load(getResources().getDrawable(R.drawable.ic_pause_black)).into(binding.startLive);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //商汤滤镜，若要测试商汤滤镜请替换商汤的授权文件，demo中的授权文件可能已过期(或将手机时间修改到2017/9/1之前)
    //demo演示时将美颜的参数写死了，用户自己接入时可以修改effect里面mStBeautifyNative的具体参数
    private void senseEffect() {
        //boolean check = STLice
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (capture!=null){
            capture.onConfigurationChanged();
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_live_stream;
    }


    private Thread mThread;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_live:
                initStartLive();
                break;
            case R.id.change_camera:
                //切换前后摄像头
                if(capture != null) {
                    capture.switchCamera();
                }
                break;
            case R.id.beauty_mode:
                alertDialog = AlertDialogUtil.showBeauty(this,beauty);
                alertDialog.show();
                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.gravity = Gravity.BOTTOM;
                layoutParams.width = SystemUtil.getScreenWidth(context);
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                layoutParams.alpha = 0.6f;
                alertDialog.getWindow().setAttributes(layoutParams);
                binding.relaBg.setVisibility(View.GONE);
                alertDialog.setOnDismissListener(this);
                //alertDialog.setCanceledOnTouchOutside(false);
                break;
            case R.id.music_list:
                ToastUtil.showToast("没写");
                break;
            case R.id.filter_mode:
                filterAlert = AlertDialogUtil.showFilter(context);
                filterAlert.show();
                WindowManager.LayoutParams params = filterAlert.getWindow().getAttributes();
                params.gravity = Gravity.END;
                params.width = SystemUtil.getScreenWidth(context)/5;
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                params.alpha = 0.6f;
                filterAlert.getWindow().setAttributes(params);
                //filterAlert.setCanceledOnTouchOutside(false);
                filterAlert.setOnDismissListener(this);
                break;
        }
    }

    Observer<RTMP> observer = new Observer<RTMP>() {
        @Override
        public void onChanged(RTMP rtmpBean) {
            if (rtmpBean!=null){
                //记得补充
                publishParam.setPushUrl(rtmpBean.getPushUrl());
                if (isOnce){
                    initStartLive();
                }
            }
        }
    };

    private void initStartLive(){
        long time = System.currentTimeMillis();
        if(time - clickTime < 1000){
            return;
        }
        clickTime = time;
        binding.startLive.setClickable(false);
        if(!liveStreamingOn)
        {
            //8、初始化直播推流
            if(mThread != null){
                showToast(NetConstant.START_LIVEING);
                return;
            }
            showToast(NetConstant.INIT_LIVEING);
            mThread = new Thread(){
                public void run(){
                    //正常网络下initLiveStream 1、2s就可完成，当网络很差时initLiveStream可能会消耗5-10s，因此另起线程防止UI卡住
                    if(!startAV()){
                        showToast(NetConstant.INIT_LIVE_ERROR);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LiveStreamingActivity.this.finish();
                            }
                        },3000);
                    }
                    mThread = null;
                }
            };
            mThread.start();
        }else {
            SaveUserData.getInstance(context).saveLiveStatus(1);
            viewModel.closeLive(liveId);
            showToast(NetConstant.STOP_LIVE);
            stopAV();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setBeautyMode(MessageEvent messageEvent){
        if (messageEvent!=null){
            if (messageEvent.getMessageType()== YBZBIMEnum.MessageType.BEAUTYMODECHANGE){
                beauty = (Beauty) messageEvent.getObject();
                if (capture!=null){
                    Log.d(TAG, "setBeautyMode: "+capture.getMaxExposureCompensation()/50);
                    capture.setBeautyLevel(beauty.getGrinding()/20);
                    capture.setExposureCompensation(beauty.getExposure()*(capture.getMaxExposureCompensation()/50));
                    capture.setFilterStrength(beauty.getFilter()/100);
                    if (alertDialog.isShowing()){
                        alertDialog.dismiss();
                        binding.relaBg.setVisibility(View.VISIBLE);
                    }
                }
            }else if (messageEvent.getMessageType() == YBZBIMEnum.MessageType.UPDATEFILTERTYPE){
                capture.setFilterType((VideoEffect.FilterType) messageEvent.getObject());
            }
        }
    }


    @Override
    protected void onDestroy() {

        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        //伴音相关的Receive取消注册
        unregisterReceiver(messageReceiver);
        unregisterReceiver(audioMixVolumeMessageReceiver);
        //停止直播调用的API接口
        if (capture != null && liveStreamingOn) {
            //停止直播，释放资源
            stopAV();
            //如果音视频或单独视频直播，先关闭预览
            if (startVideoCamera){
                capture.stopVideoPreview();
                //消耗第三方滤镜
                //没写滤镜

                capture.destroyVideoPreview();
            }

            //反初始化推流实例，当他与stopLiveStreaming连续调用时，参数为false
            capture.uninitLsMediaCapture(false);
            capture = null;

            intentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished",2);
            sendBroadcast(intentLiveStreamingStopFinished);
        }else if (capture != null && startVideoCamera){
            capture.stopVideoPreview();
            //消耗第三方滤镜
            //没写滤镜

            capture.destroyVideoPreview();

            //反初始化推流实例，当它不与stopLiveStreaming连续调用时，参数为true
            capture.uninitLsMediaCapture(true);
            capture = null;

            intentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished", 1);
            sendBroadcast(intentLiveStreamingStopFinished);
        }else if (!liveStreamingInitFinished){
            intentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished",1);
            sendBroadcast(intentLiveStreamingStopFinished);

            //反初始化推流实例，当它不与stopLiveStreaming连续调用时，参数为true
            capture.uninitLsMediaCapture(true);
        }
        if (liveStreamingOn){
            liveStreamingOn = false;
        }
        super.onDestroy();
    }

    @Override
    public void handleMessage(int i, Object o) {
        switch (i){
            case MSG_INIT_LIVESTREAMING_OUTFILE_ERROR:
            case MSG_INIT_LIVESTREAMING_VIDEO_ERROR:
            case MSG_INIT_LIVESTREAMING_AUDIO_ERROR:
                Log.d(TAG, "handleMessage: "+o);
                ToastUtil.showToast(NetConstant.INIT_LIVE_ERROR);
                handler.postDelayed(()->{
                    LiveStreamingActivity.this.finish();
                },2000);
                break;
            case MSG_START_LIVESTREAMING_ERROR:
               ToastUtil.showToast(NetConstant.START_LIVE_ERROR);
               break;
            case MSG_STOP_LIVESTREAMING_ERROR:
                if (liveStreamingOn){
                    ToastUtil.showToast(NetConstant.STOP_LIVE_ERROR);
                }
                break;
            case MSG_AUDIO_PROCESS_ERROR:
                if (liveStreamingOn && System.currentTimeMillis() - mLastAudioProcessErrorAlertTime >= 10000){
                    ToastUtil.showToast(NetConstant.AUDIO_DEAL_ERROR);
                    mLastAudioProcessErrorAlertTime = System.currentTimeMillis();
                }
                break;
            case MSG_VIDEO_PROCESS_ERROR:
                if (liveStreamingOn && System.currentTimeMillis() - mLastVideoProcessErrorAlertTime >= 10000){
                    ToastUtil.showToast(NetConstant.VIDEO_DEAL_ERROR);
                    mLastVideoProcessErrorAlertTime = System.currentTimeMillis();
                }
                break;
            case MSG_START_PREVIEW_ERROR:
                Log.d(TAG, "handleMessage: 无法打开相机进行预览");
                ToastUtil.showToast(NetConstant.CANT_NOT_OPEN_CAMERA);
                break;
            case MSG_AUDIO_RECORD_ERROR:
                ToastUtil.showToast(NetConstant.CANT_NOT_OPEN_AUDIO);
                handler.postDelayed(()->{
                    LiveStreamingActivity.this.finish();
                },2000);
                break;
            case MSG_RTMP_URL_ERROR:
                ToastUtil.showToast(NetConstant.NETWORK_CAN_CONNECT);
                handler.postDelayed(()->{LiveStreamingActivity.this.finish();},2000);
                break;
            case MSG_URL_NOT_AUTH:
                ToastUtil.showToast(NetConstant.SERVICE_ERROR);
                break;
            case MSG_SEND_STATICS_LOG_ERROR://统计信息出错
                Log.d(TAG, "handleMessage: 统计信息出错");
                break;
            case MSG_SEND_HEARTBEAT_LOG_ERROR://发送心跳信息出错
                Log.d(TAG, "handleMessage: 发送心跳信息出错");
                break;
            case MSG_AUDIO_SAMPLE_RATE_NOT_SUPPORT_ERROR: //音频采集参数不支持
                Log.d(TAG, "handleMessage: 音频采集参数不支持");
                break;
            case MSG_AUDIO_PARAMETER_NOT_SUPPORT_BY_HARDWARE_ERROR://音频参数不支持
                Log.d(TAG, "handleMessage: 音频参数不支持");
                break;
            case MSG_NEW_AUDIORECORD_INSTANCE_ERROR://音频实例初始化出错
                Log.d(TAG, "handleMessage: 音频实例初始化出错");
                break;
            case MSG_QOS_TO_STOP_LIVESTREAMING: // 网络QoS极差
                ToastUtil.showToast(NetConstant.NETWORK_UPLOAD_SLOW);
                Log.d(TAG, "handleMessage: 网络QoS极差");
                break;
            case MSG_CAMERA_PREVIEW_SIZE_NOT_SUPPORT_ERROR: //camera采集参数不支持
                Log.d(TAG, "handleMessage: camera采集参数不支持");
                break;
            case MSG_START_LIVESTREAMING_FINISHED:
                ToastUtil.showToast(NetConstant.START_LIVE_SUCCESS);
                Glide.with(this).load(getResources().getDrawable(R.drawable.ic_play_black)).into(binding.startLive);
                binding.startLive.setClickable(true);
                Log.d(TAG, "handleMessage: 直播开始");
                break;
            case MSG_GET_STATICS_INFO://统计信息
                //Log.d(TAG, "handleMessage: 统计信息");
                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tryToStopLivestreaming = true;

    }
    private void staticHandle(){
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        binding.relaBg.setVisibility(View.VISIBLE);
    }

    //用于接收Service发送的消息，伴音开关
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int audioMsg = intent.getIntExtra("AudioMixMSG", 0);

        }
    }

    //用于接收Service发送的消息，伴音音量
    public class AudioMixVolumeMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int audioVolume = intent.getIntExtra("AudioMixVolumeMSG", 0);

            int streamVolume = 0;
            int maxStreamVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            streamVolume = audioVolume * maxStreamVolume / 10;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, streamVolume, 1);
        }
    }
}
