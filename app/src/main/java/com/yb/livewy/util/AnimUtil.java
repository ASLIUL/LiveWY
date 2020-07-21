package com.yb.livewy.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

public class AnimUtil {

    public static void toLeftBottom(Context context,View view){
        int width = SystemUtil.getScreenWidth(context);
        int height = SystemUtil.getScreenHeight(context);
        if (width<1 || height <1){
            return;
        }
        ObjectAnimator leftTrans = ObjectAnimator.ofFloat(view,ProPertyName.TRANSLATIONX,view.getX(),10);
        ObjectAnimator bottomTrans = ObjectAnimator.ofFloat(view,ProPertyName.TRANSLATIONY,view.getY(),height-10);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,ProPertyName.SCALEX,1f,0.3f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,ProPertyName.SCALEY,1f,0.3f);
        AnimatorSet set = new AnimatorSet();
        set.play(leftTrans).with(bottomTrans).with(scaleX).with(scaleY);
        set.setDuration(1000);
        set.start();
    }
    public static void toCenter(Context context,View view){
        int width = SystemUtil.getScreenWidth(context);
        int height = SystemUtil.getScreenHeight(context);
        if (width<1 || height <1){
            return;
        }
        ObjectAnimator leftTrans = ObjectAnimator.ofFloat(view,ProPertyName.TRANSLATIONX,view.getX(),10);
        ObjectAnimator bottomTrans = ObjectAnimator.ofFloat(view,ProPertyName.TRANSLATIONY,view.getY(),height-10);
        AnimatorSet set = new AnimatorSet();
        set.play(leftTrans).with(bottomTrans);
        set.setDuration(1000);
        set.start();
    }
}
