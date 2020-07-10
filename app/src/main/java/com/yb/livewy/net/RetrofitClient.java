package com.yb.livewy.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.SaveUserData;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class RetrofitClient {
    private Context mContext;
    private Retrofit mRetrofit;
    private static RetrofitClient instance;

    public static synchronized RetrofitClient getInstance(Context context){
        if (instance==null){
            instance = new RetrofitClient(context);
        }
        return instance;
    }


    public RetrofitClient(Context context) {
        mContext = context;
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .addInterceptor(new AddHeaderInterceptor(context))
                .connectTimeout(NetConstant.API_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(NetConstant.API_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(NetConstant.API_WRITE_TIME_OUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .baseUrl(YBRYIMURL.BaseUrl) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();
    }

    /**
     * 接受cookie拦截器
     */
    public class ReceivedCookiesInterceptor implements Interceptor {
        private Context mContext;

        public ReceivedCookiesInterceptor(Context context) {
            mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookiesSet = new HashSet<>(originalResponse.headers("Set-Cookie"));

                SharedPreferences.Editor config = mContext.getSharedPreferences(NetConstant.API_SP_NAME_NET, MODE_PRIVATE)
                        .edit();
                config.putStringSet(NetConstant.API_SP_KEY_NET_COOKIE_SET, cookiesSet);
                config.apply();
            }

            return originalResponse;
        }
    }

    /**
     * 添加header包含cookie拦截器
     */
    public class AddHeaderInterceptor implements Interceptor {
        private Context mContext;

        public AddHeaderInterceptor(Context context) {
            mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("uuid","3131231312321");
            builder.addHeader("token", SaveUserData.getInstance(mContext).getUserToken()+"");
            return chain.proceed(builder.build());
        }
    }

    public <T> T createService(Class<T> service) {
        return mRetrofit.create(service);
    }
}
