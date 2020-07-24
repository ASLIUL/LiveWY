package com.yb.livewy.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.vanniktech.emoji.EmojiPopup;
import com.yb.livewy.R;
import com.yb.livewy.bean.MessageEvent;
import com.yb.livewy.bean.YBZBIMEnum;
import com.yb.livewy.databinding.ActivityLivePlayerInputPanelLayoutBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class LivePlayerBottomInputActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LivePlayerBottomInputAc";
    ActivityLivePlayerInputPanelLayoutBinding binding;
    private LivePlayerBottomInputActivity livePlayerBottomInputActivity = this;
    private EmojiPopup emojiPopup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,initLayout());
        initData();
        showSoftInput();
    }

    protected void initData() {

        binding.setLifecycleOwner(this);
        emojiPopup = EmojiPopup.Builder.fromRootView(binding.getRoot()).build(binding.msgConnect);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        binding.sendMsg.setOnClickListener(this);
        binding.emojiBtn.setOnClickListener(this);
    }

    /**
     * 自动弹软键盘
     *
     */
    public void showSoftInput() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                livePlayerBottomInputActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.msgConnect.setFocusable(true);
                        binding.msgConnect.setFocusableInTouchMode(true);
                        //请求获得焦点
                        binding.msgConnect.requestFocus();
                        //调用系统输入法
                        InputMethodManager inputManager = (InputMethodManager) binding.msgConnect.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(binding.msgConnect, 0);
                    }
                });
            }
        }, 200);
    }
    protected int initLayout() {
        return R.layout.activity_live_player_input_panel_layout;
    }
    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_msg:
                EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.CHATROOMSENDMSG,binding.msgConnect.getText().toString()));
                hideInput();
                LivePlayerBottomInputActivity.this.finish();
                break;
            case R.id.emoji_btn:
                emojiPopup.toggle();
                break;
        }
    }
}