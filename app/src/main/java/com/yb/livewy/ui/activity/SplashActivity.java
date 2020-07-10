package com.yb.livewy.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.yb.livewy.R;
import com.yb.livewy.databinding.ActivitySplashBinding;
import com.yb.livewy.vm.SplashViewModel;

public class SplashActivity extends BaseAppActivity<ActivitySplashBinding, SplashViewModel> {


    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void onClick(View v) {

    }
}
