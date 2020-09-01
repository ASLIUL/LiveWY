package com.yb.livewy.ui.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.netease.LSMediaCapture.lsLogUtil;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.LSMediaCapture.lsMessageHandler;
import com.netease.LSMediaCapture.video.VideoCallback;
import com.netease.vcloud.video.effect.VideoEffect;
import com.netease.vcloud.video.render.NeteaseView;
import com.yb.livewy.bean.Beauty;
import com.yb.livewy.bean.PublishParam;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.SaveUserData;
import com.yb.livewy.util.SystemUtil;
import com.yb.livewy.util.ToastUtil;

import static com.netease.LSMediaCapture.lsMediaCapture.FormatType.RTMP;
import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.AV;

public class LiveStreamPanel implements lsMessageHandler {

    private PublishParam publishParam;

    private AppCompatActivity appCompatActivity;


    // SDK 相关参数
    //滤镜
    private boolean userFilter = false;
    //水印
    private boolean needWater = false;
    //涂鸦
    private boolean needGraffiti = false;

    private lsMediaCapture capture = null;
    private lsMediaCapture.LiveStreamingPara liveStreamingPara;

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

    private long clickTime = 0L;

    private static final String TAG = "LiveStreamPanel";

    //广播
    private MessageReceiver messageReceiver = null;
    private AudioMixVolumeMessageReceiver audioMixVolumeMessageReceiver = null;

    //美颜程度
    private Beauty beauty = new Beauty(0,50,50);

    //检测是已经点击了开始的还是没有 ，默认没有
    private boolean isOnce = false;

    private int liveId = 0;

    private NeteaseView neteaseView;

    private Thread liveThread;

    private Handler handler = new Handler();

    //第三方滤镜
    private FuVideoEffect fuVideoEffect;

    private FURenderer fuRenderer;

    public LiveStreamPanel(AppCompatActivity appCompatActivity,NeteaseView neteaseView){
        this.appCompatActivity = appCompatActivity;
        this.neteaseView = neteaseView;
        initPublishParam();
    }


    public void init(String pushUrl){
        publishParam.setPushUrl(pushUrl);
        liveStreamingPara = new lsMediaCapture.LiveStreamingPara();
        liveStreamingPara.setStreamType(publishParam.getStreamType());
        liveStreamingPara.setFormatType(publishParam.getFormatType());
        liveStreamingPara.setRecordPath(publishParam.getRecordPath());
        liveStreamingPara.setQosOn(publishParam.isQosEnable());

        //是否是前置摄像头
//        boolean frontCamera = publishParam.isFrontCamera();
        //是否强制16x9
//        boolean scale_16x9 = publishParam.isScale_16x9();

//        if (publishParam.getStreamType() != lsMediaCapture.StreamType.AUDIO){
//            lsMediaCapture.VideoQuality v = publishParam.getVideoQuality();
//            //capture.startVideoPreview(neteaseView,frontCamera,userFilter,v,scale_16x9);
//        }

        //伴音操作
        audioManager = (AudioManager) appCompatActivity.getSystemService(Context.AUDIO_SERVICE);
        //动态注册广播，接受service的消息
        messageReceiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("AudioMix");
        appCompatActivity.registerReceiver(messageReceiver,intentFilter);

        //另一个广播
        audioMixVolumeMessageReceiver = new AudioMixVolumeMessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("AudioMixVolume");
        appCompatActivity.registerReceiver(audioMixVolumeMessageReceiver,filter);
    }

    public void startPreview(){
        initFuVideoEffect();
        userFilter = publishParam.isUseFilter();
        needGraffiti = publishParam.isGraffitiOn();
        needWater = publishParam.isWatermark();
        try {
            //创建直播实例
            lsMediaCapture.LsMediaCapturePara para = new lsMediaCapture.LsMediaCapturePara();
            para.setContext(appCompatActivity.getApplicationContext());
            para.setMessageHandler(this);
            para.setLogLevel(lsLogUtil.LogLevel.INFO);
            para.setUploadLog(true);
            capture = new lsMediaCapture(para);
            capture.startVideoPreview(neteaseView,true,userFilter,lsMediaCapture.VideoQuality.HD1080P,publishParam.isScale_16x9());
        }catch (Exception e){
            e.printStackTrace();
        }
        startVideoCamera = true;
        if (userFilter){
            capture.setBeautyLevel(beauty.getGrinding()/20);
            capture.setFilterStrength(beauty.getFilter()/100);
            capture.setFilterType(publishParam.getFilterType());
        }
        if (videoCallback){
            Log.d(TAG, "startPreview: 滤镜");
            //在这里可以自定义滤镜
            capture.setCaptureRawDataCB(new VideoCallback() {
                @Override
                public int onVideoCapture(byte[] data, int textureId, int width, int height, int orientation) {
                    if (fuRenderer!=null){
                        return fuRenderer.onDrawFrame(data,textureId,width, height);
                    }else {
                        return 0;
                    }
                }
            });
        }

        capture.setCaptureRawDataCB(new VideoCallback() {
            @Override
            public int onVideoCapture(byte[] data, int textureId, int width, int height, int orientation) {
                if (fuRenderer!=null){
                    return fuRenderer.onDrawFrame(data,textureId,width, height);
                }else {
                    return 0;
                }
            }
        });

        if (audioCallback){
            //音频采集原始数据回调，在这里可以进行降噪，消除回音等
            capture.setAudioRawDataCB((data,len)->{

            } );
        }

    }

    private void initFuVideoEffect(){
        if (fuVideoEffect == null){
            fuVideoEffect = new FuVideoEffect();
            fuVideoEffect.filterInit(appCompatActivity);
        }
    }

    public void setFuRenderer(FURenderer fuRenderer) {
        this.fuRenderer = fuRenderer;
        fuRenderer.onSurfaceCreated();
        fuRenderer.cameraChanged();
    }

    private boolean startLive(){
        //初始化直播
        liveStreamingInitFinished = capture.initLiveStream(liveStreamingPara,publishParam.getPushUrl());
        if (capture != null && liveStreamingInitFinished){
            capture.startLiveStreaming();
            //标记开始直播
            liveStreamingOn = true;

            //水印，涂鸦 添加在这里

            return true;
        }
        return liveStreamingInitFinished;
    }
    public void stopLive(){
        SaveUserData.getInstance(appCompatActivity).saveLiveStatus(1);
        ToastUtil.showToast(NetConstant.STOP_LIVE);
        try {
            if (capture!=null){
                capture.stopLiveStreaming();
                ToastUtil.showToast(NetConstant.STOP_LIVE_FINISH);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onConfigurationChanged(){
        if (capture!=null){
            capture.onConfigurationChanged();
        }
    }

    public void initLive(){
        long time = System.currentTimeMillis();
        if (time - clickTime < 1000){
            return;
        }
        clickTime = time;
        //如果没有开始直播
        if (!liveStreamingOn){
            if (liveThread!=null){
                ToastUtil.showToast(NetConstant.START_LIVEING);
                return;
            }
            ToastUtil.showToast(NetConstant.INIT_LIVEING);
            liveThread = new Thread(){
                @Override
                public void run() {
                    if (!startLive()) {
                        ToastUtil.showToast(NetConstant.INIT_LIVE_ERROR);
                        //执行退出操作
                        handler.postDelayed(() -> {
                            appCompatActivity.finish();
                        }, 3000);
                    }
                    liveThread = null;
                }
            };
            liveThread.start();
        }
    }

    public lsMediaCapture getCapture() {
        return capture;
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
                    appCompatActivity.finish();
                },2000);
                break;
            case MSG_START_LIVESTREAMING_ERROR:
                ToastUtil.showToast(NetConstant.START_LIVE_ERROR);
                break;
            case MSG_STOP_LIVESTREAMING_ERROR:
                if (liveStreamingOn){
                    ToastUtil.showToast(NetConstant.STOP_LIVE_ERROR);
                }
                handler.postDelayed(()->{
                    appCompatActivity.finish();
                },2000);
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
                    appCompatActivity.finish();
                },2000);
                break;
            case MSG_RTMP_URL_ERROR:
                ToastUtil.showToast(NetConstant.NETWORK_CAN_CONNECT);
                handler.postDelayed(()->{appCompatActivity.finish();},2000);
                break;
            case MSG_URL_NOT_AUTH:
                ToastUtil.showToast(NetConstant.SERVICE_ERROR);
                break;
            case MSG_START_LIVESTREAMING_FINISHED:
                ToastUtil.showToast(NetConstant.START_LIVE_SUCCESS);
                Log.d(TAG, "handleMessage: 直播开始");
                break;
        }
    }



    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    public void onDestroy(){
        try {
            appCompatActivity.unregisterReceiver(messageReceiver);
            appCompatActivity.unregisterReceiver(audioMixVolumeMessageReceiver);
            if (fuRenderer!=null){
                fuRenderer.onSurfaceDestroyed();
            }
            if (capture!=null && liveStreamingOn){
                stopLive();
                if (startVideoCamera){
                    //关闭相机预览
                    capture.stopVideoPreview();
                    //滤镜等记得在这里释放资源
                    capture.destroyVideoPreview();
                }
                //反初始化推流实例，当他与stopLiveStreaming连续调用时，参数为false
                capture.uninitLsMediaCapture(false);
                capture = null;

                intentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished",2);
                appCompatActivity.sendBroadcast(intentLiveStreamingStopFinished);
            }else if (capture != null && startVideoCamera ){
                capture.stopVideoPreview();
                capture.destroyVideoPreview();
                capture.uninitLsMediaCapture(true);
                capture = null;
                intentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished",1);
                appCompatActivity.sendBroadcast(intentLiveStreamingStopFinished);
            }else if (!liveStreamingInitFinished){
                intentLiveStreamingStopFinished.putExtra("LiveStreamingStopFinished",1);
                appCompatActivity.sendBroadcast(intentLiveStreamingStopFinished);

                //
                capture.uninitLsMediaCapture(true);
            }

            if (liveStreamingOn){
                liveStreamingOn = false;
            }
        }catch (Exception e){

        }
    }

    public class AudioMixVolumeMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    private lsMediaCapture.VideoPara getMaxDefinition(){
        lsMediaCapture.VideoPara videoPara = new lsMediaCapture.VideoPara();
        videoPara.setHeight(SystemUtil.getScreenHeight(appCompatActivity));
        videoPara.setWidth(SystemUtil.getScreenWidth(appCompatActivity));
        videoPara.setFps(30);
        videoPara.setBitrate(1280*720);
        return videoPara;
    }

    private void initPublishParam(){
        publishParam = new PublishParam();
        publishParam.setPushUrl("");
        publishParam.setStreamType(AV);
        publishParam.setFormatType(RTMP);
        publishParam.setRecordPath(Environment.getDataDirectory().getPath());
        publishParam.setVideoQuality(lsMediaCapture.VideoQuality.HD1080P);
        publishParam.setScale_16x9(true);
        publishParam.setUseFilter(false);
        publishParam.setFilterType(VideoEffect.FilterType.none);
        publishParam.setFrontCamera(true);
        publishParam.setWatermark(false);
        publishParam.setQosEnable(true);
        publishParam.setQosEncodeMode(2);
        publishParam.setGraffitiOn(false);
        publishParam.setUploadLog(true);
    }


}
