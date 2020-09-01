package com.yb.livewy.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.wildma.pictureselector.PictureSelectUtils;
import com.yb.livewy.R;
import com.yb.livewy.bean.ChatRoomMsg;
import com.yb.livewy.bean.Filter;
import com.yb.livewy.bean.MessageEvent;
import com.yb.livewy.bean.YBZBIMEnum;
import com.yb.livewy.databinding.ActivityLiveStreamUiBinding;
import com.yb.livewy.ui.adapter.LivePlayerRecyclerAdapter;
import com.yb.livewy.ui.inter.LiveSteramInterface;
import com.yb.livewy.ui.inter.OnFUControlListener;
import com.yb.livewy.ui.model.FURenderer;
import com.yb.livewy.ui.model.LiveSteamUITitleAction;
import com.yb.livewy.ui.model.LiveStreamLeftPanel;
import com.yb.livewy.ui.model.LiveStreamPanel;
import com.yb.livewy.util.CameraUtils;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.ToastUtil;
import com.yb.livewy.vm.LiveStreamViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.wildma.pictureselector.PictureSelectUtils.GET_BY_ALBUM;
import static com.yb.livewy.ui.model.FURenderer.EXTERNAL_INPUT_TYPE_VIDEO;

public class LiveStreamActivity extends BaseAppActivity<ActivityLiveStreamUiBinding, LiveStreamViewModel> implements LiveSteramInterface,FURenderer.OnFUDebugListener,FURenderer.OnTrackingStatusChangedListener {


    //直播工具类
    private LiveStreamPanel liveStreamPanel;
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

    private static final String TAG = "LiveStreamActivity";

    private OnFUControlListener fuControlListener;

    private FURenderer fuRenderer;

    private ArrayList<ChatRoomMsg> chatMessageBeans = new ArrayList<>();
    private LivePlayerRecyclerAdapter livePlayerRecyclerAdapter;


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
        fuRenderer = initFURenderer();
        this.fuControlListener = fuRenderer;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0.7f;
        getWindow().setAttributes(params);
        liveStreamPanel = new LiveStreamPanel(LiveStreamActivity.this,binding.neteaseView);
        liveStreamPanel.setFuRenderer(fuRenderer);
        liveStreamPanel.startPreview();
        leftPanel = new LiveStreamLeftPanel(binding.getRoot().getRootView(),liveStreamPanel.getCapture(),LiveStreamActivity.this);
        leftPanel.setFuControlListener(fuRenderer);
        titleAction = new LiveSteamUITitleAction(LiveStreamActivity.this,binding.getRoot().getRootView(),this);
        viewModel.getHistoryData();
        titleAction.setIsLiveStreaming(isStartLive);
        viewModel.setIsLiveing(isStartLive);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        binding.chatRecycler.setLayoutManager(linearLayoutManager);
        livePlayerRecyclerAdapter = new LivePlayerRecyclerAdapter(context,chatMessageBeans);
        binding.chatRecycler.setAdapter(livePlayerRecyclerAdapter);
    }

    @Override
    protected void initViewListener() {
        binding.startLive.setOnClickListener(this);
        binding.title.liveCover.setOnClickListener(this);
        binding.inputPanel.setOnClickListener(this);
        viewModel.getLiveRtmpUrlLiveData().observe(this,liveRtmpUrl -> {
            if (liveRtmpUrl!=null){
                if (!TextUtils.isEmpty(liveRtmpUrl.getCover_img())){
                    titleAction.setLiveRtmpUrl(liveRtmpUrl);
                    isUploadCover = false;
                }
                if (isStartLive){
                    liveStreamPanel.init(liveRtmpUrl.getPushUrl());
                    liveStreamPanel.initLive();
                    viewModel.loginChatRoom();
                    leftPanel.setLiveRtmpUrl(liveRtmpUrl);
                    leftPanel.setLiveStreaming(isStartLive);
                    binding.startLive.setFocusable(true);
                    binding.startLive.setEnabled(true);
                }
            }
        });

        viewModel.getChatMessageLiveData().observe(this,chatRoomMsgs -> {
            if (chatRoomMsgs!=null){
                this.chatMessageBeans.addAll(chatRoomMsgs);
                livePlayerRecyclerAdapter.notifyDataSetChanged();
                binding.chatRecycler.smoothScrollToPosition(this.chatMessageBeans.size()-1);
            }
        });
        viewModel.observerChatRoomMessage();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_live_stream_ui;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_live){
            if (isStartLive){
                binding.constraintLayout.transitionToStart();
                //说明已经开始直播了
                titleAction.setIsLiveStreaming(true);
                liveStreamPanel.stopLive();
                isStartLive = false;
                viewModel.setIsLiveing(isStartLive);
                viewModel.exitLive();
                viewModel.closeLive();
                leftPanel.setLiveStreaming(isStartLive);
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
                isStartLive = true;
                viewModel.startLive(true,filePath,titleAction.getLiveTitle());
                viewModel.setIsLiveing(isStartLive);
                binding.startLive.setFocusable(false);
                binding.startLive.setEnabled(false);
                binding.constraintLayout.transitionToEnd();
            }else {
                isStartLive = true;
                viewModel.startLive(false,"",titleAction.getLiveTitle());
                viewModel.setIsLiveing(isStartLive);
                binding.constraintLayout.transitionToEnd();
            }
        }else if (v.getId() == R.id.live_cover){
            if (!isStartLive){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GET_BY_ALBUM);
            }
        }else if (v.getId() == R.id.input_panel){
            Intent intent = new Intent(this, LivePlayerBottomInputActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        filePath = PictureSelectUtils.onActivityResult(LiveStreamActivity.this,requestCode,resultCode,data,true,450,300,3,2);
        titleAction.setFileCover(filePath);
        if (!TextUtils.isEmpty(filePath))
            isUploadCover = true;
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (liveStreamPanel!=null){
            liveStreamPanel.onDestroy();
        }
    }

    @Override
    public void addMessage(IMMessage message) {

    }

    protected FURenderer initFURenderer() {
        return new FURenderer
                .Builder(this)
                .maxFaces(2)
                .setNeedFaceBeauty(true)
                .setExternalInputType(EXTERNAL_INPUT_TYPE_VIDEO)
                .inputImageOrientation(CameraUtils.getFrontCameraOrientation())
                .inputTextureType(FURenderer.FU_ADM_FLAG_EXTERNAL_OES_TEXTURE)
                .setOnFUDebugListener(this)
                .setOnTrackingStatusChangedListener(this)
                .setOnSystemErrorListener(error -> {
                    Log.d(TAG, "initFURenderer:SDK遇到错误\t"+error);
                })
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEvent(MessageEvent messageEvent){
        if (messageEvent==null){
            return;
        }
        if (messageEvent.getMessageType() == YBZBIMEnum.MessageType.CHOOSEFILTER){
            Filter filter = (Filter) messageEvent.getObject();
            binding.chooseFilter.setText("滤镜 | "+context.getResources().getString(filter.getNameId()));
            fuControlListener.onFilterNameSelected(filter.getName());
        }
        if (messageEvent.getMessageType() == YBZBIMEnum.MessageType.FILTERSTRENGTH){
            int progress = (int) messageEvent.getObject();
            if (progress<0){
                return;
            }
            fuControlListener.onFilterLevelSelected((float) (progress)/100);
        }
        if (messageEvent.getMessageType() == YBZBIMEnum.MessageType.CHATROOMSENDMSG){
            String msg = (String) messageEvent.getObject();
            viewModel.sendMsg(msg);
        }
    }

    @Override
    public void onTrackStatusChanged(int type, int status) {
        Log.d(TAG, "onTrackStatusChanged: type=\t"+type+"\tstatus=\t"+status);
    }

    @Override
    public void onFpsChange(double fps, double renderTime) {

    }
}
