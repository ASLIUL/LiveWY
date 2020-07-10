package com.yb.livewy.vm;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.yb.livewy.bean.LiveRtmpUrl;
import com.yb.livewy.net.Result;
import com.yb.livewy.net.RetrofitClient;
import com.yb.livewy.net.UserApi;
import com.yb.livewy.ui.activity.LoginActivity;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.ToastUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveStreamViewModel extends AndroidViewModel {

    private UserApi userApi;

    private MutableLiveData<LiveRtmpUrl> liveRtmpUrlLiveData = new MutableLiveData<>();

    public LiveStreamViewModel(@NonNull Application application) {
        super(application);
        userApi = new RetrofitClient(getApplication()).createService(UserApi.class);
    }

    public MutableLiveData<LiveRtmpUrl> getLiveRtmpUrlLiveData() {
        return liveRtmpUrlLiveData;
    }

    //开始直播，提交参数
    public void startLive(boolean isUploadCover, String filePath,String title){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型

        File file = new File(filePath);
        if (isUploadCover){
            if (!file.exists()){
                ToastUtil.showToast(NetConstant.UPLOADCOVER);
                return;
            }else {
                RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型
                builder.addFormDataPart("file",file.getName(),body); //添加图片数据，body创建的请求体
            }
        }

        builder.addFormDataPart("channel_id","1");
        builder.addFormDataPart("name",title);
        List<MultipartBody.Part> parts=builder.build().parts();

        Call<Result<LiveRtmpUrl>> call = userApi.getRTMPUrl(parts);
        call.enqueue(new Callback<Result<LiveRtmpUrl>>() {
            @Override
            public void onResponse(Call<Result<LiveRtmpUrl>> call, Response<Result<LiveRtmpUrl>> response) {
                try {
                    Result<LiveRtmpUrl> result = response.body();
                    if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
                        liveRtmpUrlLiveData.setValue(result.getData());
                    }else if (result.getCode() == NetConstant.TOKEN_CODE){
                        ToastUtil.showToast(NetConstant.TOKEN_FILED);
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result<LiveRtmpUrl>> call, Throwable t) {
                ToastUtil.showToast(NetConstant.SERVICE_ERROR);
            }
        });
    }
    //第一进入，获取历史封面以及标题
    public void getHistoryData(){
        Call<Result<LiveRtmpUrl>> call = userApi.getRTMPUrlData();
        call.enqueue(new Callback<Result<LiveRtmpUrl>>() {
            @Override
            public void onResponse(Call<Result<LiveRtmpUrl>> call, Response<Result<LiveRtmpUrl>> response) {
                try {
                    Result<LiveRtmpUrl> result = response.body();
                    if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
                        liveRtmpUrlLiveData.setValue(result.getData());
                    }else if (result.getCode() == NetConstant.TOKEN_CODE){
                        ToastUtil.showToast(NetConstant.TOKEN_FILED);
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }else {
                        ToastUtil.showToast(result.getMsg());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result<LiveRtmpUrl>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //下播
    public void closeLive(){
            userApi.closeLive(liveRtmpUrlLiveData.getValue().getId()).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });
    }
}



