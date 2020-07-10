package com.yb.livewy.util;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

/**
 * create by liu
 * on 2020/5/14 5:25 PM
 **/
public class ShapeUtils {

    public static GradientDrawable setFullShape(String strokeColor, String bgColor){
        GradientDrawable drawable = new GradientDrawable();
        //设置圆角大小
        drawable.setCornerRadius(15);
        //设置边缘线的宽以及颜色
        drawable.setStroke(3, Color.parseColor(strokeColor));
        //设置shape背景色
        drawable.setColor(Color.parseColor(bgColor));
        return drawable;
    }
    public static GradientDrawable setStrokeShape(String strokeColor){
        GradientDrawable drawable = new GradientDrawable();
        //设置圆角大小
        drawable.setCornerRadius(15);
        //设置边缘线的宽以及颜色
        drawable.setStroke(3, Color.parseColor(strokeColor));
        //设置shape背景色
        drawable.setColor(Color.parseColor("#FFFFFF"));
        return drawable;
    }

    public static GradientDrawable setStrokeShapeByGift(String strokeColor){
        GradientDrawable drawable = new GradientDrawable();
        //设置圆角大小
        drawable.setCornerRadius(15);
        //设置边缘线的宽以及颜色
        drawable.setStroke(3, Color.parseColor(strokeColor));
        //设置shape背景色
        drawable.setColor(Color.parseColor("#FFFFFF"));
        return drawable;
    }
    public static GradientDrawable setHeaderRound(int roundWidth){
        GradientDrawable drawable = new GradientDrawable();
        //设置圆角大小
        drawable.setCornerRadius(roundWidth);
        //设置边缘线的宽以及颜色
        drawable.setStroke(3, Color.GRAY);
        //设置shape背景色
        drawable.setColor(Color.parseColor("#FFFFFF"));
        return drawable;
    }
}
