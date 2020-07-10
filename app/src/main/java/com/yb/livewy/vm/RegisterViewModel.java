package com.yb.livewy.vm;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.yb.livewy.net.Result;
import com.yb.livewy.net.RetrofitClient;
import com.yb.livewy.net.RetrofitUtil;
import com.yb.livewy.net.UserApi;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.ToastUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * created  by liu
 * on 2020-01-16 11:17
 */
public class RegisterViewModel extends AndroidViewModel {

    private UserApi userApi;
    private static final String TAG = "RegisterViewModel";

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userApi = RetrofitClient.getInstance(getApplication()).createService(UserApi.class);
    }


    /**
     * 发送验证码
     * @param phone
     */
    public void sendVeriCode(String phone){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("shouji",phone);
        //Call<Result> call = userApi.sendCode(RetrofitUtil.createJsonRequest(hashMap));
//        call.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//               Result result = response.body();
//               if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
//                    handler.sendEmptyMessage(1);
//               }else {
//                   handler.sendEmptyMessage(2);
//               }
//            }
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                handler.sendEmptyMessage(0);
//            }
//        });
    }
    /**
     * 注册账号
     */

    public void registerAccount(String username, String password){
        Log.d(TAG, "registerAccount: "+username+"\t"+password);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("mobile",username);
        hashMap.put("password",password);
        //hashMap.put("nickname",nickname);
        //hashMap.put("yzm",code);
        Call<Result> register = userApi.registerUser(RetrofitUtil.createJsonRequest(hashMap));
        register.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d(TAG, "onResponse: "+response.body().toString());
                Result result = response.body();
                if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
                    handler.sendEmptyMessage(3);
                }else {
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    ToastUtil.showToast(NetConstant.ERROR_NETWORK);
                    break;
                case 1:
                    ToastUtil.showToast(NetConstant.SUCCESS_VERICODE);
                    break;
                case 2:
                    ToastUtil.showToast(NetConstant.ERROR_VERICODE);
                    break;
                case 3:
                    ToastUtil.showToast(NetConstant.REGISTER_SUCCESS);
                    break;
            }
        }
    };
}
