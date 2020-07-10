package com.yb.livewy.ui.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.wildma.pictureselector.PictureSelectUtils;
import com.yb.livewy.R;
import com.yb.livewy.bean.LiveRtmpUrl;
import com.yb.livewy.databinding.ActivityLiveReadyBinding;
import com.yb.livewy.util.SaveUserData;
import com.yb.livewy.vm.LiveReadyViewModel;

import static com.wildma.pictureselector.PictureSelectUtils.GET_BY_ALBUM;

public class LiveReadyActivity extends BaseAppActivity<ActivityLiveReadyBinding, LiveReadyViewModel> {

    private String filePath = "";
    private static final String TAG = "LiveReadyActivity";
    private boolean isSubmitData = true;
    private LiveRtmpUrl liveRtmpUrl;
    @Override
    protected void initData() {
        binding.setData(viewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void initViewListener() {

        viewModel.getLiveRtmp().observe(this,liveRtmpUrl -> {
            if (liveRtmpUrl!=null){
                this.liveRtmpUrl = liveRtmpUrl;
                if (!TextUtils.isEmpty(liveRtmpUrl.getCover_img())){
                    Glide.with(context).load(liveRtmpUrl.getCover_img()).into(binding.addCover);
                    if (!TextUtils.isEmpty(liveRtmpUrl.getName())){
                        viewModel.getRoomTitle().setValue(liveRtmpUrl.getName());
                        isSubmitData = false;
                    }
                }

            }
        });
        viewModel.getData();
        binding.addCover.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, GET_BY_ALBUM);
        });

        binding.startLive.setOnClickListener( v -> {

            if (SaveUserData.getInstance(context).getLiveStatus() == 1){
                viewModel.getDataByName();
                SaveUserData.getInstance(context).saveLiveStatus(0);
                return;
            }

            Log.d(TAG, "initViewListener: +filePath=\t"+filePath);
            if (isSubmitData || filePath.length()>10){
                viewModel.getLiveData(filePath);
            }else {
                viewModel.startLive(liveRtmpUrl.getId());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        filePath =  PictureSelectUtils.onActivityResult(LiveReadyActivity.this,requestCode,resultCode,data,true,300,300,1,1);
        Glide.with(LiveReadyActivity.this).load(filePath).into(binding.addCover);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_live_ready;
    }

    @Override
    public void onClick(View v) {

    }
}
