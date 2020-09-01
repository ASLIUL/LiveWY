package com.yb.livewy.vm;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.chatroom.ChatRoomMessageBuilder;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.yb.livewy.bean.ChatRoomCustomMsg;
import com.yb.livewy.bean.ChatRoomMsg;
import com.yb.livewy.bean.LiveRtmpUrl;
import com.yb.livewy.net.Result;
import com.yb.livewy.net.RetrofitClient;
import com.yb.livewy.net.UserApi;
import com.yb.livewy.ui.activity.LoginActivity;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.SaveUserData;
import com.yb.livewy.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
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

    private static final String TAG = "LiveStreamViewModel";

    private MutableLiveData<Boolean> isLiveing = new MutableLiveData<>();

    private MutableLiveData<Integer> exitLiveLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ChatRoomMsg>> chatMessageLiveData = new MutableLiveData<>();

    public LiveStreamViewModel(@NonNull Application application) {
        super(application);
        userApi = new RetrofitClient(getApplication()).createService(UserApi.class);
        isLiveing.setValue(false);
    }

    public void setIsLiveing(boolean isLive) {
        this.isLiveing.setValue(isLive);
    }

    public MutableLiveData<Boolean> getIsLiveing() {
        return isLiveing;
    }

    public MutableLiveData<LiveRtmpUrl> getLiveRtmpUrlLiveData() {
        return liveRtmpUrlLiveData;
    }

    public MutableLiveData<List<ChatRoomMsg>> getChatMessageLiveData() {
        return chatMessageLiveData;
    }

    public MutableLiveData<Integer> getExitLiveLiveData() {
        return exitLiveLiveData;
    }

    //开始直播，提交参数
    public void startLive(boolean isUploadCover, String filePath,String title){
        if (isUploadCover && TextUtils.isEmpty(filePath)){
            ToastUtil.showToast(NetConstant.UPLOADCOVER);
            return;
        }

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
        Log.d(TAG, "startLive: 开始发出请求");
        Call<Result<LiveRtmpUrl>> call = userApi.getRTMPUrl(parts);
        call.enqueue(new Callback<Result<LiveRtmpUrl>>() {
            @Override
            public void onResponse(Call<Result<LiveRtmpUrl>> call, Response<Result<LiveRtmpUrl>> response) {
                try {
                    Log.d(TAG, "onResponse: 得到请求结果，开始解析");
                    Result<LiveRtmpUrl> result = response.body();
                    if (result.getCode() == NetConstant.REQUEST_SUCCESS_CODE){
                        Log.d(TAG, "onResponse: code==200");
                        liveRtmpUrlLiveData.postValue(result.getData());
                    }else if (result.getCode() == NetConstant.TOKEN_CODE) {
                        ToastUtil.showToast(NetConstant.TOKEN_FILED);
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }
                    Log.d(TAG, "onResponse: 请求错误 code="+result.getCode());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result<LiveRtmpUrl>> call, Throwable t) {
                ToastUtil.showToast(NetConstant.SERVICE_ERROR);
                Log.d(TAG, "startLive: 请求失败");
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
        if (TextUtils.isEmpty(liveRtmpUrlLiveData.getValue().getId()+"")){
            return;
        }
            userApi.closeLive(liveRtmpUrlLiveData.getValue().getId()).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });
    }

    //发送消息
    public void sendMsg(String msgConnect) {

        if (TextUtils.isEmpty(msgConnect)){
            ToastUtil.showToast(NetConstant.NOT_INPUT_SOMETHING);
            return;
        }


        ChatRoomCustomMsg chatRoomCustomMsg = new ChatRoomCustomMsg(
                NetConstant.CHAT_ROOM_TEXT_MESSAGE,
                NetConstant.REQUEST_SUCCESS_CODE,
                new ChatRoomCustomMsg.MsgData(
                        msgConnect,
                        SaveUserData.getInstance(getApplication()).getUserName(),
                        SaveUserData.getInstance(getApplication()).getUserId(),
                        SaveUserData.getInstance(getApplication()).getUserAccid(),
                        1,
                        1
                ));

        String msgJson = new Gson().toJson(chatRoomCustomMsg);

        ChatRoomMessage message = ChatRoomMessageBuilder.createChatRoomTextMessage(liveRtmpUrlLiveData.getValue().getRoom_id()+"", msgJson);
        // 将文本消息发送出去
        NIMClient.getService(ChatRoomService.class).sendMessage(message, true)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        List<ChatRoomMsg> chatRoomMsgs = new ArrayList<>();
                        chatRoomMsgs.add(new ChatRoomMsg(chatRoomCustomMsg.getData().getChatAccount(),chatRoomCustomMsg.getData().getUsername(),chatRoomCustomMsg.getData().getConnect(),chatRoomCustomMsg.getData().getLevel(),chatRoomCustomMsg.getData().getUserType()));
                        chatMessageLiveData.setValue(chatRoomMsgs);
                    }

                    @Override
                    public void onFailed(int code) {
                        // 失败
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // 错误
                    }
                });
    }

    //加入聊天室

    public void loginChatRoom(){
        // roomId 表示聊天室ID
        EnterChatRoomData data = new EnterChatRoomData(liveRtmpUrlLiveData.getValue().getRoom_id()+"");
        // 重试3次
        NIMClient.getService(ChatRoomService.class).enterChatRoomEx(data, 3).setCallback(new RequestCallback<EnterChatRoomResultData>() {
            @Override
            public void onSuccess(EnterChatRoomResultData result) {
                // 登录成功
                try{
                    Log.d(TAG, "onSuccess: 登陆成功");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int code) {
                // 登录失败
            }

            @Override
            public void onException(Throwable exception) {
                // 错误
            }
        });
    }

    //发送下播信息
    public void exitLive(){
        ChatRoomCustomMsg chatRoomCustomMsg = new ChatRoomCustomMsg(NetConstant.EXIT_LIVE_MESSAGE,100,new ChatRoomCustomMsg.MsgData());
        String msgJson = new Gson().toJson(chatRoomCustomMsg);
        ChatRoomMessage exitMsg = ChatRoomMessageBuilder.createChatRoomTextMessage(liveRtmpUrlLiveData.getValue().getRoom_id()+"",msgJson);
        NIMClient.getService(ChatRoomService.class).sendMessage(exitMsg, true)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        // 成功
                    }

                    @Override
                    public void onFailed(int code) {
                        // 失败
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // 错误
                    }
                });
    }

    public void observerChatRoomMessage(){
        NIMClient.getService(ChatRoomServiceObserver.class)
                .observeReceiveMessage(new Observer<List<ChatRoomMessage>>() {
                    @Override
                    public void onEvent(List<ChatRoomMessage> chatRoomMessages) {
                        if (chatRoomMessages==null || chatRoomMessages.isEmpty()){
                            return;
                        }
                        List<ChatRoomMsg> chatRoomMsgs = new ArrayList<>();
                        chatRoomMsgs.clear();
                        for (int i = 0; i < chatRoomMessages.size(); i++) {
                            ChatRoomCustomMsg chatMsg = new Gson().fromJson(chatRoomMessages.get(i).getContent(),ChatRoomCustomMsg.class);
                            chatRoomMsgs.add(new ChatRoomMsg(chatMsg.getData().getChatAccount(),chatMsg.getData().getUsername(),chatMsg.getData().getConnect(),chatMsg.getData().getLevel(),chatMsg.getData().getUserType()));
                        }
                        chatMessageLiveData.setValue(chatRoomMsgs);
                    }
                }, true);
    }

}



