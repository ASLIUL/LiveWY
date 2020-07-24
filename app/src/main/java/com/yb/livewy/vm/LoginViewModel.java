package com.yb.livewy.vm;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.yb.livewy.bean.LoginUser;
import com.yb.livewy.net.Result;
import com.yb.livewy.net.RetrofitClient;
import com.yb.livewy.net.RetrofitUtil;
import com.yb.livewy.net.UserApi;
import com.yb.livewy.ui.activity.ForgotPasswordActivity;
import com.yb.livewy.ui.activity.LiveStreamActivity;
import com.yb.livewy.ui.activity.RegisterActivity;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.SaveUserData;
import com.yb.livewy.util.ToastUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * create by liu
 * on 2020/3/30 10:53 AM
 **/
public class LoginViewModel extends AndroidViewModel {


    private UserApi userApi;
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private static final String TAG = "LoginViewModel";
    MediatorLiveData<Object> result;
    private AppCompatActivity appCompatActivity;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        initData();
    }

    public MediatorLiveData<Object> getResult(){
        if (result==null){
            result = new MediatorLiveData<>();
        }
        return result;
    }

    private void initData() {
        userName.setValue(SaveUserData.getInstance(getApplication()).getUserPhone());
        password.setValue(SaveUserData.getInstance(getApplication()).getUserPwd());
        if (TextUtils.isEmpty(userName.getValue()) || TextUtils.isEmpty(password.getValue()) ){
            return;
        }else {
            loginUser();
        }
    }

    public void loginUser(){
        if (TextUtils.isEmpty(userName.getValue()) || TextUtils.isEmpty(password.getValue())){
            ToastUtil.showToast(NetConstant.ERROR_NOTINPUT);
            return;
        }
        userApi = RetrofitClient.getInstance(getApplication()).createService(UserApi.class);
        Log.d(TAG, "loginUser: "+userName.getValue()+"\t"+password.getValue());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("mobile",userName.getValue());
        hashMap.put("password",password.getValue());
        hashMap.put("type",2);
        Call<Result<LoginUser>> meCall = userApi.loginUser(RetrofitUtil.createJsonRequest(hashMap));
        meCall.enqueue(new Callback<Result<LoginUser>>() {
            @Override
            public void onResponse(Call<Result<LoginUser>> call, Response<Result<LoginUser>> response) {
                try {
                    Result<LoginUser> result = response.body();
                    if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
                        Log.d(TAG, "onResponse: "+result.getData().toString());
                        LoginInfo info = new LoginInfo(result.getData().getAccid(), result.getData().getImToken(),NetConstant.IM_APP_KEY);
                        SaveUserData.getInstance(getApplication()).saveUserId(result.getData().getId()+"");
                        RequestCallback<LoginInfo> loginInfoRequestCallback = new RequestCallback<LoginInfo>() {
                            @Override
                            public void onSuccess(LoginInfo param) {
                                SaveUserData.getInstance(getApplication()).saveUserAccid(param.getAccount());
                                SaveUserData.getInstance(getApplication()).saveUserImToken(param.getToken());
                                SaveUserData.getInstance(getApplication()).saveUserPwd(password.getValue());
                                SaveUserData.getInstance(getApplication()).saveUserPhone(userName.getValue());
                                SaveUserData.getInstance(getApplication()).saveUserId(result.getData().getId()+"");
                                SaveUserData.getInstance(getApplication()).saveUserToken(result.getData().getToken()+"");
                                SaveUserData.getInstance(getApplication()).saveUserName(result.getData().getName());
                                ToastUtil.showToast(NetConstant.IM_LOGIN_SUCCESS);
                                handler.sendEmptyMessage(1);

                            }
                            @Override
                            public void onFailed(int code) {
                                ToastUtil.showToast(NetConstant.IM_LOGIN_FAILED+code);
                            }

                            @Override
                            public void onException(Throwable exception) {
                                Log.d(TAG, "onException: "+exception.getMessage());
                            }
                        };
                        NIMClient.getService(AuthService.class).login(info).setCallback(loginInfoRequestCallback);
                    }else {
                        Message m = new Message();
                        m.obj = result.getMsg();
                        m.what = 2;
                        handler.sendMessage(m);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    handler.sendEmptyMessage(3);
                }

            }

            @Override
            public void onFailure(Call<Result<LoginUser>> call, Throwable t) {
                handler.sendEmptyMessage(3);
            }
        });

    }
    public void goToRegister(){
        Intent intent = new Intent(getApplication(), RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }
    public void goToUpdatePassword(){
        Intent intent = new Intent(getApplication(), ForgotPasswordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    ToastUtil.showToast(getApplication(),"网络错误");
                    break;
                case 1:
                    Intent intent = new Intent(getApplication(), LiveStreamActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                    if (appCompatActivity!=null){
                        appCompatActivity.finish();
                    }
                    break;
                case 2:
                    ToastUtil.showToast((String) msg.obj);
                    break;
                case 3:
                    ToastUtil.showToast("服务器异常");
                    break;
            }
        }
    };

}
