package com.yb.livewy.vm;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.yb.livewy.bean.RTMP;
import com.yb.livewy.net.Result;
import com.yb.livewy.net.RetrofitClient;
import com.yb.livewy.net.UserApi;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.SaveUserData;
import com.yb.livewy.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * create by liu
 * on 2020/4/13 3:45 PM
 **/
public class LiveStreamingViewModel extends AndroidViewModel {

    private static final String TAG = "LiveStreamViewModel";
    private UserApi api;

    public LiveStreamingViewModel(@NonNull Application application) {
        super(application);
        api = RetrofitClient.getInstance(getApplication()).createService(UserApi.class);
    }

    MutableLiveData<RTMP> jsonObjectMutableLiveData;


    public MutableLiveData<RTMP> getJsonObjectMutableLiveData() {
        if (jsonObjectMutableLiveData==null){
            jsonObjectMutableLiveData = new MutableLiveData<>();
        }
        return jsonObjectMutableLiveData;
    }

    public void getRTMPUrl(String roomTitle){
        String token = SaveUserData.getInstance(getApplication()).getUserToken();
        Call<Result<RTMP>> rtmpBeanCall = api.getRTMPUrl(roomTitle);
        rtmpBeanCall.enqueue(new Callback<Result<RTMP>>() {
            @Override
            public void onResponse(Call<Result<RTMP>> call, Response<Result<RTMP>> response) {
                //记得判断实际错误情况
                try {
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    Result<RTMP> result = response.body();
                    Log.d(TAG, "onResponse: "+result.toString());
                    if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
                        RTMP rtmpBean = result.getData();
                        jsonObjectMutableLiveData.setValue(rtmpBean);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    ToastUtil.showToast(NetConstant.ERROR_SERVICES);
                }


            }

            @Override
            public void onFailure(Call<Result<RTMP>> call, Throwable t) {
                handler.sendEmptyMessage(0);
            }
        });

    }

    public void closeLive(int id){
        Call<Result> call = api.closeLive(id);
        try {
          call.enqueue(new Callback<Result>() {
              @Override
              public void onResponse(Call<Result> call, Response<Result> response) {

              }

              @Override
              public void onFailure(Call<Result> call, Throwable t) {

              }
          });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    ToastUtil.showToast("网络连接失败，请稍后重试");
                    break;
            }
        }
    };
}
