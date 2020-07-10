package com.yb.livewy.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created  by liu
 * on 2020-01-19 10:39
 * 常用到工具方法
 */
public class Utils {

    private static final String TAG = "Utils";

    private static DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();

    public static boolean isPhone(String phone){
        Pattern p = Pattern.compile(NetConstant.REGULAR_PHONE);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static void stopTime(int time) throws InterruptedException {
        if (time<1){
            time=1000;
        }
        Thread.sleep(time);
    }

    public static int getScreenHeight(){
        return displayMetrics.heightPixels;
    }

    public static boolean uploadCover(){

        return true;
    }
}
