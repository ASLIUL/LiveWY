package com.yb.livewy.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.viewpager.widget.ViewPager;

import com.netease.vcloud.video.effect.VideoEffect;
import com.yb.livewy.R;
import com.yb.livewy.bean.Beauty;
import com.yb.livewy.bean.MessageEvent;
import com.yb.livewy.bean.YBZBIMEnum;

import org.greenrobot.eventbus.EventBus;

/**
 * create by liu
 * on 2020-02-18 20:56
 * 自定义的弹窗
 **/
public class AlertDialogUtil {

    private static AlertDialog.Builder builder;

    /**
     * 转圈圈的加载框
     */
    public static AlertDialog showRunRoundLoadDialog(Context context){
        builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    public static AlertDialog showErrorDialog(Context context, String title, String connect){
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(connect);
        AlertDialog alertDialog = builder.create();
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }

    //美颜界面的dialog
    public static AlertDialog showBeauty(Context context, Beauty currentBeauty){
        builder = new AlertDialog.Builder(context,R.style.dialogAnim);
        View view = LayoutInflater.from(context).inflate(R.layout.live_beauty_mode_layout,null,false);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        AppCompatSeekBar filter = view.findViewById(R.id.filter_seekBar);
        AppCompatSeekBar beauty = view.findViewById(R.id.beauty_seekBar);
        AppCompatSeekBar exposure = view.findViewById(R.id.exposure_seekBar);
        filter.setProgress((int) (currentBeauty.getFilter()));
        beauty.setProgress(currentBeauty.getGrinding());
        exposure.setProgress(currentBeauty.getExposure());
        Button button = view.findViewById(R.id.close_view);
        button.setOnClickListener((v -> {
            EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.BEAUTYMODECHANGE,new Beauty(exposure.getProgress(),beauty.getProgress(),filter.getProgress())));
        }));
        builder.setCancelable(false);
        return dialog;
    }
    //滤镜界面的Dialog
    public static AlertDialog showFilter(Context context){
        //614104
        builder = new AlertDialog.Builder(context,R.style.dialogAnim);
        View view = LayoutInflater.from(context).inflate(R.layout.live_filter_mode_layout,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        RadioGroup filter = view.findViewById(R.id.filter_group);
        filter.setOnCheckedChangeListener(((group, checkedId) -> {
            switch (checkedId){
                case R.id.filter_refreshing:
                case R.id.filter_clean:
                    EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.UPDATEFILTERTYPE, VideoEffect.FilterType.clean));
                    break;
                case R.id.filter_natural:
                    EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.UPDATEFILTERTYPE, VideoEffect.FilterType.nature));
                    break;
                case R.id.filter_restoring:
                    EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.UPDATEFILTERTYPE, VideoEffect.FilterType.pixar));
                    break;
                case R.id.filter_tender:
                    EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.UPDATEFILTERTYPE, VideoEffect.FilterType.tender));
                    break;
                case R.id.filter_nostalgia:
                    EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.UPDATEFILTERTYPE, VideoEffect.FilterType.brooklyn));
                    break;
                case R.id.filter_healthy:
                    EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.UPDATEFILTERTYPE, VideoEffect.FilterType.healthy));
                    break;
                case R.id.filter_whitening:
                    EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.UPDATEFILTERTYPE, VideoEffect.FilterType.whiten));
                    break;
            }
            alertDialog.dismiss();
        }));
        return alertDialog;
    }
}
