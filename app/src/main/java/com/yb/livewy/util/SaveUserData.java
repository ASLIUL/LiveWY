package com.yb.livewy.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * create by liu
 * on 2020/4/27 10:41 AM
 **/
public class SaveUserData {

    private static SaveUserData instance;
    private static SharedPreferences.Editor editor;
    private static SharedPreferences sharedPreferences;

    private static Context context;

    public static SaveUserData getInstance(Context context){
        synchronized (SaveUserData.class){
            editor = context.getSharedPreferences("user",0).edit();
            sharedPreferences = context.getSharedPreferences("user",0);
            if (instance==null){
                instance = new SaveUserData(context);
            }
        }
        return instance;
    }

    public SaveUserData(Context context) {
        this.context = context;
    }
    public  void saveUserId(String userId){
        if (editor==null){
            editor = context.getSharedPreferences("user",0).edit();
        }
        editor.putString("userId",userId);
        editor.apply();
        editor.commit();
    }
    public String getUserId(){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("user",0);
        }
        return sharedPreferences.getString("userId","");
    }

    public  void saveUserAccid(String accid){
        if (editor==null){
            editor = context.getSharedPreferences("user",0).edit();
        }
        editor.putString("accid",accid);
        editor.apply();
        editor.commit();
    }
    public String getUserAccid(){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("user",0);
        }
        return sharedPreferences.getString("accid","");
    }
    public  void saveUserToken(String userToken){
        if (editor==null){
            editor = context.getSharedPreferences("user",0).edit();
        }
        editor.putString("userToken",userToken);
        editor.apply();
        editor.commit();
    }
    public String getUserToken(){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("user",0);
        }
        return sharedPreferences.getString("userToken","");
    }
    public  void saveUserPwd(String pwd){
        if (editor==null){
            editor = context.getSharedPreferences("user",0).edit();
        }
        editor.putString("pwd",pwd);
        editor.apply();
        editor.commit();
    }
    public String getUserPwd(){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("user",0);
        }
        return sharedPreferences.getString("pwd","");
    }

    public  void saveUserImToken(String userToken){
        if (editor==null){
            editor = context.getSharedPreferences("user",0).edit();
        }
        editor.putString("userToken",userToken);
        editor.apply();
        editor.commit();
    }
    public String getUserImToken(){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("user",0);
        }
        return sharedPreferences.getString("userToken","");
    }
    public  void saveUserPhone(String phone){
        if (editor==null){
            editor = context.getSharedPreferences("user",0).edit();
        }
        editor.putString("phone",phone);
        editor.apply();
        editor.commit();
    }
    public String getUserPhone(){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("user",0);
        }
        return sharedPreferences.getString("phone","");
    }

    public  void saveUserName(String name){
        if (editor==null){
            editor = context.getSharedPreferences("user",0).edit();
        }
        editor.putString("username",name);
        editor.apply();
        editor.commit();
    }
    public String getUserName(){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("user",0);
        }
        return sharedPreferences.getString("username","");
    }

    public  void saveLiveStatus(int status){
        if (editor==null){
            editor = context.getSharedPreferences("user",0).edit();
        }
        editor.putInt("status",status);
        editor.apply();
        editor.commit();
    }
    public  int getLiveStatus(){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("user",0);
        }
        return sharedPreferences.getInt("status",0);
    }


}
