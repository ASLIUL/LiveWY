package com.yb.livewy.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

/**
 * create by liu
 * on 2020/2/25 9:32 PM
 * 动态修改SVG图片的主要颜色
 **/
public class ResourceColor {

    public static void changeSVGColo(Context context, ImageView imageView, int color, int imageResId){
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(context.getResources(), imageResId, context.getTheme());
        //你需要改变的颜色
        try {
            vectorDrawableCompat.setTint(color);
            imageView.setImageDrawable(vectorDrawableCompat);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
