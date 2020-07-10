package com.yb.wyzbclient.vm;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.exoplayer2.C;
import com.google.gson.JsonObject;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.yb.wyzbclient.net.RetrofitUtil;
import com.yb.wyzbclient.net.UserApi;
import com.yb.wyzbclient.util.ToastUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LivePlayViewModel extends AndroidViewModel {

    private MutableLiveData<String> im_account = new MutableLiveData<>();
    public MutableLiveData<String> msg = new MutableLiveData<>();
    private UserApi userApi;
    private static final String TAG = "LivePlayViewModel";

    public LivePlayViewModel(@NonNull Application application) {
        super(application);
        userApi = new RetrofitUtil(getApplication()).createService(UserApi.class);
    }

    public void setIm_account(String account) {
        this.im_account.setValue(account);
    }

    public void sendMsg() {
        if (TextUtils.isEmpty(im_account.getValue())) {
            ToastUtil.showToast("加载失败，请稍后");
            return;
        }
        IMMessage textMsg = MessageBuilder.createTextMessage(im_account.getValue(), SessionTypeEnum.P2P, msg.getValue());
        NIMClient.getService(MsgService.class).sendMessage(textMsg, false);
        msg.setValue("");
    }


    public void getVideoUrl(int liveId){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("id",liveId);
        Call<JsonObject> call = userApi.getLiveUrlById(RetrofitUtil.createJsonRequest(hashMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
             