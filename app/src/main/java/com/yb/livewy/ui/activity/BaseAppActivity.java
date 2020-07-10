package com.yb.livewy.ui.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.netease.nimlib.sdk.NIMClient;
import com.yb.livewy.R;
import com.yb.livewy.bean.MessageEvent;
import com.yb.livewy.util.AppCompatActivityControls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseAppActivity<T extends ViewDataBinding, Y extends ViewModel> extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BaseAppActivity";
    public Context context;
    private Toast toast;
    //两次按您点击的时间间隔
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    protected T binding;
    protected Y viewModel;
    //是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useThemestatusBarColor = false;
    //是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
    protected boolean useStatusBarColor = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        AppCompatActivityControls.addActivity(this);
        binding = DataBindingUtil.setContentView(this, initLayout());
        //viewModel = (Y) ViewModelProviders.of(this).get(viewModel.getClass());
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = viewModel.getClass();
            }
            viewModel = (Y) ViewModelProviders.of(this).get(modelClass);
        }
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor("#0090FF").init();
        NIMClient.toggleNotification(false);
        initData();
        initViewListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 初始化显示数据
     */
    protected abstract void initData();

    /**
     * 初始化各种控件监听函数
     */
    protected abstract void initViewListener();

    /**
     * 设置布局
     */
    protected abstract int initLayout();

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示Toast
     */
    @SuppressLint("ShowToast")
    public void showToast(String msg) {
        try {
            if (null == toast) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    /**
     * findViewById
     */
    private View findView(int resId, View.OnClickListener listener) {
        View view = findViewById(resId);
        view.setOnClickListener(listener);
        return view;
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));//设置状态栏背景色
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void TestData(MessageEvent event) {

    }

    @Override
    protected void onDestroy() {
        AppCompatActivityControls.removeActivity(this);
        super.onDestroy();

    }

    @Override
    public void finish() {
        super.finish();
        /**
         * Activity关闭动画
         *overridePendingTransition(R.anim.activity_finish_anim,R.anim.activity_finish_anim);
         */

    }

    public abstract void onClick(View v);

    /**
     * Same as {@link #startActivity(Intent, Bundle)} with no options
     * specified.
     *
     * @param intent The intent to start.
     * @throws ActivityNotFoundException
     * @see #startActivity(Intent, Bundle)
     * @see #startActivityForResult
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        /**
         * 下面是Activity跳转切换动画
         * overridePendingTransition(0,0);
         */


    }


}
