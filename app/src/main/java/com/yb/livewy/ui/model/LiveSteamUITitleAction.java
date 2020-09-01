package com.yb.livewy.ui.model;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.yb.livewy.R;
import com.yb.livewy.bean.LiveRtmpUrl;
import com.yb.livewy.ui.inter.LiveSteramInterface;

import static com.wildma.pictureselector.PictureSelectUtils.GET_BY_ALBUM;

public class LiveSteamUITitleAction implements View.OnClickListener {

    private View rootView;

    //输入框
    private EditText msgConnect;

    //封面
    private ImageView cover;

    //编辑按键
    private ImageView editAction;

    //close view
    private ImageView closeAction;

    private AppCompatActivity appCompatActivity;

    private Context context;

    private LiveSteramInterface liveSteramInterface;

    // title layout
    private RelativeLayout titleLayout;

    private LiveRtmpUrl liveRtmpUrl;

    public LiveSteamUITitleAction(AppCompatActivity appCompatActivity, View rootView, LiveSteramInterface liveSteramInterface){
        this.appCompatActivity = appCompatActivity;
        this.rootView = rootView;
        this.liveSteramInterface = liveSteramInterface;
        initView();
    }

    private void initView(){
        if (rootView == null){
            return;
        }
        msgConnect = rootView.findViewById(R.id.title_connect);
        cover = rootView.findViewById(R.id.live_cover);
        editAction = rootView.findViewById(R.id.edit_title);
        closeAction = rootView.findViewById(R.id.close_view);
        titleLayout = rootView.findViewById(R.id.title_layout);
        closeAction.setOnClickListener(this);
        editAction.setOnClickListener(this);
    }

    public void setLiveRtmpUrl(LiveRtmpUrl liveRtmpUrl) {
        this.liveRtmpUrl = liveRtmpUrl;
        if (!TextUtils.isEmpty(liveRtmpUrl.getCover_img())){
            Glide.with(appCompatActivity).load(liveRtmpUrl.getCover_img()).into(cover);
            msgConnect.setText(liveRtmpUrl.getName()+"");
        }
    }

    public void setFileCover(String filePath){
        Glide.with(appCompatActivity).load(filePath).into(cover);
    }

    public void setIsLiveStreaming(boolean isLiveStreaming){
        if (isLiveStreaming){
            msgConnect.setFocusable(false);
            msgConnect.setEnabled(false);
        }else {
            msgConnect.setFocusable(true);
            msgConnect.setEnabled(true);
        }

    }

    public String getLiveTitle(){
        return msgConnect.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_view:
                titleLayout.setVisibility(View.GONE);
                break;
            case R.id.edit_title:
                msgConnect.setFocusable(true);
                msgConnect.setEnabled(true);
                break;
        }
    }
}
