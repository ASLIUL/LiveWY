package com.yb.livewy.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.vcloud.video.effect.VideoEffect;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.wildma.pictureselector.PictureSelectUtils;
import com.yb.livewy.R;
import com.yb.livewy.bean.PublishParam;
import com.yb.livewy.databinding.ActivityLiveStreamUiBinding;
import com.yb.livewy.ui.inter.LiveSteramInterface;
import com.yb.livewy.ui.model.LiveSteamUITitleAction;
import com.yb.livewy.ui.model.LiveStreamLeftPanel;
import com.yb.livewy.ui.model.LiveStreamPanel;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.ToastUtil;
import com.yb.livewy.vm.LiveStreamViewModel;

import java.util.List;

import static com.netease.LSMediaCapture.lsMediaCapture.FormatType.RTMP;
import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.AV;
import static com.wildma.pictureselector.PictureSelectUtils.GET_BY_ALBUM;

public class LiveStreamActivity extends BaseAppActivity<ActivityLiveStreamUiBinding, LiveStreamViewModel> implements LiveSteramInterface {


    //直播工具类
    private LiveStreamPanel liveStreamPanel;
    //直播sdk参数
    private PublishParam publishParam;

    //是否开始直播
    private boolean isStartLive = false;
    //title操作
    private LiveSteamUITitleAction titleAction;
    //left操作
    private LiveStreamLeftPanel leftPanel;
    //是否需要上传封面
    private boolean isUploadCover = true;
    //封面地址
    private String filePath;


    @Override
    protected void initData() {
        binding.setData(viewModel);
        binding.setLifecycleOwner(this);
        binding.title.setLifecycleOwner(this);
        PermissionX.init(this)
                .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO

                ).request(new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                if (!allGranted){
                    PermissionX.init(LiveStreamActivity.this).permissions(deniedList).request((allGranted1, grantedList1, deniedList1) -> {});
                }
            }
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0.7f;
        getWindow().setAttributes(params);
        liveStreamPanel = new LiveStreamPanel(LiveStreamActivity.this,binding.neteaseView);
        liveStreamPanel.startPreview();
        leftPanel = new LiveStreamLeftPanel(binding.getRoot().getRootView(),liveStreamPanel.getCapture(),LiveStreamActivity.this);
        initPublishParam();
        titleAction = new LiveSteamUITitleAction(LiveStreamActivity.this,binding.getRoot().getRootView(),this);
        viewModel.getHistoryData();
    }

    @Override
    protected void initViewListener() {
        binding.startLive.setOnClickListener(this);
        binding.title.liveCover.setOnClickListener(this);
        viewModel.getLiveRtmpUrlLiveData().observe(this,liveRtmpUrl -> {
            if (liveRtmpUrl!=null){
                if (!TextUtils.isEmpty(liveRtmpUrl.getCover_img())){
                    titleAction.setLiveRtmpUrl(liveRtmpUrl);
                    isUploadCover = false;
                }
                if (isStartLive){
                    publishParam.setPushUrl(liveRtmpUrl.getPushUrl());
                    liveStreamPanel.init(publishParam);
                    liveStreamPanel.initLive();
                }
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_live_stream_ui;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_live){

            if (isStartLive){
                //说明已经开始直播了

                titleAction.setIsLiveStreaming(true);
                liveStreamPanel.stopLive();
                isStartLive = false;
                viewModel.closeLive();
                return;
            }else {
                titleAction.setIsLiveStreaming(false);
            }
            if (TextUtils.isEmpty(titleAction.getLiveTitle())){
                ToastUtil.showToast(NetConstant.NOT_INPUT_LIVE_TITLE);
                return;
            }
            if (isUploadCover){
                if (TextUtils.isEmpty(filePath)){
                    ToastUtil.showToast(NetConstant.UPLOADCOVER);
                    return;
                }
                viewModel.startLive(true,filePath,titleAction.getLiveTitle());
                isStartLive = true;
            }else {
                viewModel.startLive(false,"",titleAction.getLiveTitle());
                isStartLive = true;
            }

        }else if (v.getId() == R.id.live_cover){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, GET_BY_ALBUM);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        filePath = PictureSelectUtils.onActivityResult(LiveStreamActivity.this,requestCode,resultCode,data,true,300,300,1,1);
        titleAction.setFileCover(filePath);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (liveStreamPanel!=null){
            liveStreamPanel.onDestory();
        }
    }

    private void initPublishParam(){
        publishParam = new PublishParam();
        publishParam.setPushUrl("");
        publishParam.setStreamType(AV);
        publishParam.setFormatType(RTMP);
        publishParam.setRecordPath(Environment.getDataDirectory().getPath());
        publishParam.setVideoQuality(lsMediaCapture.VideoQuality.HD1080P);
        publishParam.setScale_16x9(false);
        publishParam.setUseFilter(true);
        publishParam.setFilterType(VideoEffect.FilterType.whiten);
        publishParam.setFrontCamera(true);
        publishParam.setWatermark(false);
        publishParam.setQosEnable(true);
        publishParam.setQosEncodeMode(2);
        publishParam.setGraffitiOn(false);
        publishParam.setUploadLog(true);
    }

    @Override
    public void addMessage(IMMessage message) {

    }
}
