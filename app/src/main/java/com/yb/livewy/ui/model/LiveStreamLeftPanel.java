package com.yb.livewy.ui.model;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.netease.LSMediaCapture.lsMediaCapture;
import com.yb.livewy.R;

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

    private lsMediaCapture capture = null;

    private AppCompatActivity appCompatActivity;

    public LiveStreamLeftPanel(View rootView,lsMediaCapture capture,AppCompatActivity appCompatActivity){
        this.rootView = rootView;
        this.capture = capture;
        this.appCompatActivity = appCompatActivity;
        initView();
    }

    private void initView(){
        reversal = rootView.findViewById(R.id.reversal);
        share = rootView.findViewById(R.id.share);
        beauty = rootView.findViewById(R.id.beauty);
        filter = rootView.findViewById(R.id.filter);
        reversal.setOnClickListener(this);
        share.setOnClickListener(this);
        beauty.setOnClickListener(this);
        filter.setOnClickListener(this);
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
                break;
            case R.id.beauty:
                break;
            case R.id.filter:
                break;
        }
    }
}
