package com.yb.livewy.ui.model;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netease.LSMediaCapture.lsMediaCapture;
import com.yb.livewy.R;
import com.yb.livewy.bean.BeautyBody;
import com.yb.livewy.bean.LiveRtmpUrl;
import com.yb.livewy.bean.LiveVideoBean;
import com.yb.livewy.ui.activity.ShareLiveActivity;
import com.yb.livewy.ui.adapter.FuVideoBeautyBodyRecyclerAdapter;
import com.yb.livewy.ui.inter.OnFUControlListener;
import com.yb.livewy.util.AlertDialogUtil;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.SystemUtil;
import com.yb.livewy.util.ToastUtil;

import java.util.ArrayList;

public class LiveStreamLeftPanel implements View.OnClickListener {


    private View rootView;

    //反转摄像头
    private LinearLayout reversal;
    //分享
    private LinearLayout share;
    //美化
    private LinearLayout beauty;
    //滤镜
    private LinearLayout filter;
    //商品
    private LinearLayout shop;

    private lsMediaCapture capture = null;

    private AppCompatActivity appCompatActivity;

    private OnFUControlListener fuControlListener;

    private ArrayList<BeautyBody> beautyBodies = new ArrayList<>();

    private AppCompatSeekBar seekBar;
    private MutableLiveData<Float> seekBarValue = new MutableLiveData<>();

    private BeautyBody beautyBody;

    private boolean isLiveStreaming = false;

    private LiveRtmpUrl liveRtmpUrl;

    private static final String TAG = "LiveStreamLeftPanel";

    public LiveStreamLeftPanel(View rootView,lsMediaCapture capture,AppCompatActivity appCompatActivity){
        this.rootView = rootView;
        this.capture = capture;
        this.appCompatActivity = appCompatActivity;
        initView();
        seekBarValue.setValue(0f);
    }

    private void initView(){
        reversal = rootView.findViewById(R.id.reversal);
        share = rootView.findViewById(R.id.share);
        beauty = rootView.findViewById(R.id.beauty);
        filter = rootView.findViewById(R.id.filter);
        shop = rootView.findViewById(R.id.shop);
        reversal.setOnClickListener(this);
        share.setOnClickListener(this);
        beauty.setOnClickListener(this);
        filter.setOnClickListener(this);
    }

    public void setFuControlListener(OnFUControlListener fuControlListener) {
        this.fuControlListener = fuControlListener;
        fuControlListener.onBlurTypeSelected(2);
    }

    public void setLiveRtmpUrl(LiveRtmpUrl liveRtmpUrl) {
        this.liveRtmpUrl = liveRtmpUrl;
    }

    public void setLiveStreaming(boolean liveStreaming) {
        isLiveStreaming = liveStreaming;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reversal:
                if (null != capture){
                    capture.switchCamera();
                }
                break;
            case R.id.share:
                if (isLiveStreaming){
                    Intent intent = new Intent(appCompatActivity, ShareLiveActivity.class);
                    intent.putExtra("liveData",liveRtmpUrl);
                    appCompatActivity.startActivity(intent);
                }else {
                    ToastUtil.showToast(NetConstant.START_LIVE_CAN_SHARE);
                }

                break;
            case R.id.beauty:
                beautyDialog();
                break;
            case R.id.filter:
                AlertDialog alertDialog = AlertDialogUtil.showFuVideoEffectFilterDialog(appCompatActivity);
                alertDialog.show();
                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.gravity = Gravity.BOTTOM;
                layoutParams.width = SystemUtil.getScreenWidth(appCompatActivity);
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                alertDialog.getWindow().setAttributes(layoutParams);
                break;
        }
    }

    private void beautyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(appCompatActivity,R.style.dialogAnim);
        View v = LayoutInflater.from(appCompatActivity).inflate(R.layout.dialog_fu_effect_beauty_layout,null,false);
        seekBar = v.findViewById(R.id.beauty_strength);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarValue.setValue(1.0f * (i) / 100);
                if (beautyBody!=null){
                    observerSeekBarValue();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        RecyclerView recyclerView = v.findViewById(R.id.beauty_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(appCompatActivity);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        beautyBodies.clear();
        beautyBodies.addAll(FuEffectNormalParam.FuEffectEnum.getBeautyBodyByFilterType());
        beautyBody = beautyBodies.get(0);
        FuVideoBeautyBodyRecyclerAdapter adapter =new FuVideoBeautyBodyRecyclerAdapter(appCompatActivity, beautyBodies);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, beautyBody, position) -> {
            this.beautyBody = beautyBody;
            float value = FuEffectNormalParam.FuEffectEnum.getNormalValue(beautyBody.getKey())*100f;
            seekBar.setProgress((int)value);
            if (beautyBody.getKey().equals(FuEffectNormalParam.FuEffectEnum.RESET.getKey())){
                observerSeekBarValue();
                seekBar.setEnabled(false);
                seekBar.setClickable(false);
                seekBar.setFocusable(false);
            }else {
                seekBar.setEnabled(true);
                seekBar.setClickable(true);
                seekBar.setFocusable(true);
            }
        });
        builder.setView(v);
        AlertDialog beauty = builder.create();
        beauty.show();
        WindowManager.LayoutParams params = beauty.getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = SystemUtil.getScreenWidth(appCompatActivity);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        beauty.getWindow().setAttributes(params);
    }

    public void observerSeekBarValue(){
        Log.d(TAG, "observerSeekBarValue: "+beautyBody.toString());
        switch (FuEffectNormalParam.FuEffectEnum.getFuEnum(beautyBody.getKey())){
            case RESET:
                Log.d(TAG, "observerSeekBarValue: 重置");
                FuEffectNormalParam.recoverFaceSkinToDefValue();
                fuControlListener.onBlurLevelSelected(0f);
                fuControlListener.onColorLevelSelected(0f);
                fuControlListener.onCheekThinningSelected(0f);
                fuControlListener.onIntensityNoseSelected(0f);
                fuControlListener.onEyeEnlargeSelected(0f);
                break;
            case BLURWHIT:
                fuControlListener.onBlurLevelSelected(seekBarValue.getValue());
                break;
            case BEAUTYWHITE:
                fuControlListener.onColorLevelSelected(seekBarValue.getValue());
                break;
            case THINFACE:
                fuControlListener.onCheekThinningSelected(seekBarValue.getValue());
                break;
            case THINNOSE:
                fuControlListener.onIntensityNoseSelected(seekBarValue.getValue());
                break;
            case BIGEYES:
                fuControlListener.onEyeEnlargeSelected(seekBarValue.getValue());
                break;

        }
    }

}
