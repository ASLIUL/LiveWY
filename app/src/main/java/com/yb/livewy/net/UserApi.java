package com.yb.livewy.net;

import com.google.gson.JsonObject;
import com.yb.livewy.bean.ChatAccount;
import com.yb.livewy.bean.LiveRtmpUrl;
import com.yb.livewy.bean.LoginUser;
import com.yb.livewy.bean.RTMP;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * create by liu
 * on 2020/4/10 11:28 AM
 **/
public interface UserApi {



    @POST(YBRYIMURL.login)
    Call<Result<LoginUser>> loginUser(@Body RequestBody body);


    @GET(YBRYIMURL.GETPULLRTMP)
    Call<Result<RTMP>> getRTMPUrl(@Query("name") String name);

    @POST(YBRYIMURL.REGISTER)
    Call<Result> registerUser(@Body RequestBody body);

    @Multipart
    @POST(YBRYIMURL.GETPULLRTMP)
    Call<Result<LiveRtmpUrl>> getRTMPUrl(@Part List<MultipartBody.Part> parts);

    @POST(YBRYIMURL.GETPULLRTMP)
    Call<Result<LiveRtmpUrl>> getRTMPUrlData();

    //创建聊天
    @GET(YBRYIMURL.CreateLiveRoom)
    Call<JsonObject> createLiveRoom(@Query("name") String name);
    //获取聊天账号
    @GET(YBRYIMURL.getChatAccount)
    Call<Result<ChatAccount>> getChatAccount();


    @Headers({"Content-Type: application/json"})
    @PUT(YBRYIMURL.closeLive)
    Call<Result> closeLive(@Query("id") int id);

}
