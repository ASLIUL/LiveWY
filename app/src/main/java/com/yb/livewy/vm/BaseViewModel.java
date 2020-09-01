package com.yb.livewy.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;

public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
        isOffLineLiveData.postValue(false);
    }
    MutableLiveData<Boolean> isOffLineLiveData = new MutableLiveData<Boolean>();

    public void monitorWyLogin(){
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus((Observer<StatusCode>) statusCode -> {
            if (statusCode.wontAutoLogin()){
                isOffLineLiveData.postValue(true);
            }
        }, true);
    }

}
