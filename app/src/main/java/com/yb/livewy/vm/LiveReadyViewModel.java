package com.yb.livewy.vm;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.vcloud.video.effect.VideoEffect;
import com.yb.livewy.bean.LiveRtmpUrl;
import com.yb.livewy.bean.PublishParam;
import com.yb.livewy.net.Result;
import com.yb.livewy.net.RetrofitClient;
import com.yb.livewy.net.UserApi;
import com.yb.livewy.ui.activity.LiveStreamingActivity;
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

import static com.netease.LSMediaCapture.lsMediaCapture.FormatType.RTMP;
import static com.netease.LSMediaCapture.lsMediaCapture.StreamType.AV;

public class LiveReadyViewModel extends AndroidViewModel {


    private static final String TAG = "LiveReadyViewModel";
    private UserApi userApi;
    private MutableLiveData<String> roomTitle = new MutableLiveData<>();
    private PublishParam publishParam = new PublishParam();
    private MutableLiveData<LiveRtmpUrl> liveRtmp = new MutableLiveData<>();


    public LiveReadyViewModel(@NonNull Application application) {
        super(application);
        userApi = RetrofitClient.getInstance(getApplication()).createService(UserApi.class);
    }

    public MutableLiveData<String> getRoomTitle() {
        return roomTitle;
    }

    public MutableLiveData<LiveRtmpUrl> getLiveRtmp() {
        return liveRtmp;
    }

    public void getLiveData(String filePath){
        if (TextUtils.isEmpty(filePath)) {
            ToastUtil.showToast(NetConstant.PARAM_ERROR+"1");
            return;
        }

        if (TextUtils.isEmpty(roomTitle.getValue())){
            ToastUtil.showToast(NetConstant.PARAM_ERROR+"2");
            return;
        }
        File file = new File(filePath);
        if (!file.exists()){
            ToastUtil.showToast(NetConstant.PARAM_ERROR+"3");
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
        RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型
        builder.addFormDataPart("file",file.getName(),body); //添加图片数据，body创建的请求体
        builder.addFormDataPart("channel_id","1");
        builder.addFormDataPart("name",roomTitle.getValue());
        List<MultipartBody.Part> parts=builder.build().parts();


        Call<Result<LiveRtmpUrl>> call = userApi.getRTMPUrl(parts);
        call.enqueue(new Callback<Result<LiveRtmpUrl>>() {
            @Override
            public void onResponse(Call<Result<LiveRtmpUrl>> call, Response<Result<LiveRtmpUrl>> response) {
                try {
                    Result<LiveRtmpUrl> result = response.body();
                    if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
                        liveRtmp.setValue(result.getData());
                        startLive(liveRtmp.getValue().getId());
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

    public void getData(){
        Call<Result<LiveRtmpUrl>> call = userApi.getRTMPUrlData();
        call.enqueue(new Callback<Result<LiveRtmpUrl>>() {
            @Override
            public void onResponse(Call<Result<LiveRtmpUrl>> call, Response<Result<LiveRtmpUrl>> response) {
                try {
                    Result<LiveRtmpUrl> result = response.body();
                    if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
                        liveRtmp.setValue(result.getData());
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

    public void getDataByName(){

        if (TextUtils.isEmpty(roomTitle.getValue())){
            ToastUtil.showToast(NetConstant.PLEASEINPUTROOMTITLE);
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
        builder.addFormDataPart("name",roomTitle.getValue());
        List<MultipartBody.Part> parts=builder.build().parts();

        Call<Result<LiveRtmpUrl>> call = userApi.getRTMPUrl(parts);
        call.enqueue(new Callback<Result<LiveRtmpUrl>>() {
            @Override
            public void onResponse(Call<Result<LiveRtmpUrl>> call, Response<Result<LiveRtmpUrl>> response) {
                try {
                    Result<LiveRtmpUrl> result = response.body();
                    if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
                        liveRtmp.setValue(result.getData());
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


    public void startLive(int id){
        publishParam.setPushUrl(liveRtmp.getValue().getPushUrl());
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
        Intent intent = new Intent(getApplication(), LiveStreamingActivity.class);
        intent.putExtra("data",publishParam);
        intent.putExtra("roomTitle",roomTitle.getValue()+"");
        intent.putExtra("liveId",id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

}
