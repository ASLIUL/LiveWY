package com.yb.livewy.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.yb.livewy.R;
import com.yb.livewy.LiveApplication;
import com.yb.livewy.bean.YBZBIMEnum;
import com.yb.livewy.ui.activity.LoginActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * created  by liu
 * on 2020-01-17 15:17
 * 全局Toast
 */
public class ToastUtil {

    private static Toast toast;

    public static void showToast(String message){
        if (TextUtils.isEmpty(message))
            return;
        // 9.0 以上直接用调用即可防止重复的显示的问题，且如果复用 Toast 会出现无法再出弹出对话框问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            Toast.makeText(LiveApplication.getContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            if (toast != null) {
                toast.setText(message);
            } else {
                toast = Toast.makeText(LiveApplication.getContext(), message, Toast.LENGTH_SHORT);
            }
            toast.show();
        }

    }
    public static void showToast(Context context, String message){
        if (TextUtils.isEmpty(message))
            return;
        // 9.0 以上直接用调用即可防止重复的显示的问题，且如果复用 Toast 会出现无法再出弹出对话框问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            if (toast != null) {
                toast.setText(message);
            } else {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            }
            toast.show();
        }

    }

    /**
     * 发送通知
     * @param context 上下文
     * @param title 通知标题
     * @param connect 通知内容
     * @return
     */
    public static void createNotification(Context context, String title, String connect){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Intent[] intents = new Intent[1];
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intents[0] = intent;
            PendingIntent pendingIntent = PendingIntent.getActivities(context,0,intents, PendingIntent.FLAG_CANCEL_CURRENT);
            Notification notification = new Notification.Builder(context)
                    .setChannelId(YBZBIMEnum.NotificationCode.FRIENDS_MESSAGE.getChannelId())
                    .setSmallIcon(R.drawable.ic_normal_header)
                    .setContentTitle(title)
                    .setContentText(connect)
                    .setAutoCancel(true)
                    .setTicker("提示")
                    .setContentIntent(pendingIntent)
                    .setFullScreenIntent(pendingIntent,true)
                    .build();
            // 2. 获取系统的通知管理器(必须设置channelId)
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    YBZBIMEnum.NotificationCode.FRIENDS_MESSAGE.getChannelId(),
                    YBZBIMEnum.NotificationCode.FRIENDS_MESSAGE.getNotifName(),
                    YBZBIMEnum.NotificationCode.FRIENDS_MESSAGE.getImportantlevel());
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(YBZBIMEnum.NotificationCode.FRIENDS_MESSAGE.getNotifId(), notification);
        }else {
            // 创建通知(标题、内容、图标)
            Notification notification = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(connect)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("其他好友给您发送信息啦")
                    .build();
            // 创建通知管理器
            NotificationManager manager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            // 发送通知
            manager.notify(YBZBIMEnum.NotificationCode.FRIENDS_MESSAGE.getNotifId(), notification);
        }
    }

}
