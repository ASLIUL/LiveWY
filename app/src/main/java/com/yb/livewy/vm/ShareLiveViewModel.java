package com.yb.livewy.vm;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.yb.livewy.bean.Contacts;
import com.yb.livewy.bean.FriendsInfoBean;
import com.yb.livewy.bean.LiveShareMessageBean;
import com.yb.livewy.net.Result;
import com.yb.livewy.net.RetrofitClient;
import com.yb.livewy.net.UserApi;
import com.yb.livewy.ui.activity.RegisterActivity;
import com.yb.livewy.ui.model.LiveShareMessageAttachment;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.SaveUserData;
import com.yb.livewy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareLiveViewModel extends AndroidViewModel {

    private UserApi userApi;
    private MutableLiveData<List<Contacts>> contactsLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> sendNumLiveData = new MutableLiveData<>();
    private static final String TAG = "ShareLiveViewModel";
    public ShareLiveViewModel(@NonNull Application application) {
        super(application);
        userApi = RetrofitClient.getInstance(getApplication()).createService(UserApi.class);
    }

    public MutableLiveData<List<Contacts>> getContactsLiveData() {
        return contactsLiveData;
    }

    public MutableLiveData<Integer> getSendNumLiveData() {
        return sendNumLiveData;
    }

    public void getMyFriends(){
        Call<Result<FriendsInfoBean>> call = userApi.getMyFriends(1,100);
        call.enqueue(new Callback<Result<FriendsInfoBean>>() {
            @Override
            public void onResponse(Call<Result<FriendsInfoBean>> call, Response<Result<FriendsInfoBean>> response) {
                try {
                    Result<FriendsInfoBean> result = response.body();
                    if (result.getCode()== NetConstant.REQUEST_SUCCESS_CODE){

                        List<Contacts> contactsList = new ArrayList<>();
                        FriendsInfoBean beans = result.getData();
                        for (int i = 0; i < beans.getPage().getRows().size(); i++) {
                            Contacts contacts = new Contacts();
                            contacts.setChatAccount(beans.getPage().getRows().get(i).getAccid());
                            contacts.setHeader(beans.getPage().getRows().get(i).getHeamImg());
                            contacts.setId(beans.getPage().getRows().get(i).getId());
                            contacts.setName(beans.getPage().getRows().get(i).getName());
                            contacts.setContactType(1);
                            contactsList.add(contacts);
                        }
                        contactsLiveData.setValue(contactsList);
                    }else if (result.getCode() == NetConstant.TOKEN_CODE){
                        ToastUtil.showToast(NetConstant.TOKEN_FAILED);
                        Intent intent = new Intent(getApplication(), RegisterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }else {
                        ToastUtil.showToast(result.getMsg());
                    }
                }catch (Exception e){
                }
            }

            @Override
            public void onFailure(Call<Result<FriendsInfoBean>> call, Throwable t) {
                ToastUtil.showToast(NetConstant.ERROR_NETWORK);
            }
        });
    }
    public void sendShareMessage(List<Contacts> contacts,String liveCover,String liveTitle,String liveId,String zbName,String userId){
        sendNumLiveData.setValue(0);
        if (TextUtils.isEmpty(userId)){
            userId = SaveUserData.getInstance(getApplication()).getUserId();
        }

        List<LiveShareMessageBean> beans = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            LiveShareMessageBean bean = new LiveShareMessageBean();
            bean.setCode(NetConstant.REQUEST_SUCCESS_CODE);
            bean.setMsg(NetConstant.CUSTOM_SHARE_LIVE_MESSAGE);
            bean.setLiveCover(liveCover);
            bean.setLiveId(liveId);
            bean.setShareUserId(userId);
            bean.setLiveTitle(liveTitle);
            bean.setLiveUsername(zbName);
            bean.setToAccount(contacts.get(i).getChatAccount());
            beans.add(bean);
        }
        for (LiveShareMessageBean messageBean:beans) {
            LiveShareMessageAttachment attachment = new LiveShareMessageAttachment(messageBean);
            IMMessage textMsg = MessageBuilder.createCustomMessage(messageBean.getToAccount(), SessionTypeEnum.P2P, "分享消息",attachment,LiveShareMessageAttachment.getConfig());
            NIMClient.getService(MsgService.class).sendMessage(textMsg, false).setCallback(new RequestCallback<Void>() {
                @Override
                public void onSuccess(Void param) {
                    sendNumLiveData.postValue(sendNumLiveData.getValue()+1);
                }

                @Override
                public void onFailed(int code) {

                }

                @Override
                public void onException(Throwable exception) {

                }
            });
        }

    }
}
