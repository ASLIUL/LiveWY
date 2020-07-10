package com.yb.livewy.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.yb.livewy.R;
import com.yb.livewy.databinding.ActivityRegisterBinding;
import com.yb.livewy.util.AppCompatActivityControls;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.ProPertyName;
import com.yb.livewy.util.Utils;
import com.yb.livewy.vm.RegisterViewModel;

/**
 * created  by liu
 * on 2020-01-16 09:53
 */
public class RegisterActivity extends BaseAppActivity<ActivityRegisterBinding, RegisterViewModel> {

    private static final String TAG = "RegisterActivity";
    /**
     * 1 点击下一步是隐藏手机号，显示验证码
     * 2 隐藏验证码输入框，显示密码
     */
    private int NEXTSTEPTYPWE = 1;

    /**
     * 手机号
     */
    private String phone;

    /**
     *加载框
     */
    private AlertDialog loadDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 初始化显示数据
     */
    @Override
    protected void initData() {
        binding.setData(viewModel);
        binding.setLifecycleOwner(this);
    }

    /**
     * 初始化各种控件以及其他类型的监听函数
     */
    @Override
    protected void initViewListener() {
        binding.liveTitleBar.getLeft_Img().setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.sendCode.setOnClickListener(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_img:
                finish();
                break;
            case R.id.button2:
                if (TextUtils.isEmpty(binding.phone.getText().toString())) {
                    showToast(NetConstant.ERROR_NOPHONECONENT);
                    return;
                }
                if (!Utils.isPhone(binding.phone.getText().toString())) {
                    showToast(NetConstant.ERROR_NOTPHONE);
                    return;
                }
//                if (TextUtils.isEmpty(binding.codeEt.getText().toString())) {
//                    ToastUtil.showToast(String.format(getResources().getString(R.string.please_input_content), getResources().getString(R.string.veri_code)));
//                    return;
//                }
//                if (binding.codeEt.getText().toString().length() != NetConstant.NUMBER_SIX) {
//                    showToast(String.format(NetConstant.ERROR_NOTDIGIT, getResources().getString(R.string.veri_code), NetConstant.NUMBER_SIX));
//                    return;
//                }
                if (TextUtils.isEmpty(binding.onePwd.getText().toString())) {
                    showToast(String.format(NetConstant.ERROR_INPUTCONTENT, getResources().getString(R.string.password)));
                    return;
                }
                if (TextUtils.isEmpty(binding.twoPwd.getText().toString())) {
                    showToast(String.format(NetConstant.ERROR_AGAINCONTENT, getResources().getString(R.string.password)));
                }
                if (!(binding.onePwd.getText().toString().equals(binding.twoPwd.getText().toString()))){
                    showToast(NetConstant.ERROR_PASSWORDNOTSAME);
                    return;
                }
//                if (TextUtils.isEmpty(binding.nickname.getText().toString())){
//                    showToast(getResources().getString(R.string.input_nickname_hint));
//                }
                //loadDialog = AlertDialogUtil.showRunRoundLoadDialog(RegisterActivity.this);
                //loadDialog.show();
                viewModel.registerAccount(binding.phone.getText().toString(),binding.onePwd.getText().toString());
                break;
            case R.id.send_code:
                if (TextUtils.isEmpty(binding.phone.getText().toString())) {
                    showToast(NetConstant.ERROR_NOPHONECONENT);
                    return;
                }
                if (!Utils.isPhone(binding.phone.getText().toString())) {
                    showToast(NetConstant.ERROR_NOTPHONE);
                    return;
                }
                viewModel.sendVeriCode(binding.phone.getText().toString());
                break;
        }
    }

    /**
     * 向左移除动画
     */
    private void removeLeftAnim(View view1, View view2, int t) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view1, ProPertyName.TRANSLATIONX, 0f, -1500f);
        animator.setDuration(NetConstant.ANIMDURATION);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view2.setVisibility(View.VISIBLE);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(view2, ProPertyName.TRANSLATIONX, 1500f, 0f);
                animator1.setDuration(NetConstant.ANIMDURATION);
                animator1.start();
                NEXTSTEPTYPWE = t;
            }
        });
       // animator.start();
    }
    /**
     * 向右移除动画
     */
    private void removeRightAnim(View view1, View view2, int t) {
        view1.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view1, ProPertyName.TRANSLATIONX, -1500f, 0f);
        animator.setDuration(NetConstant.ANIMDURATION);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view2.setVisibility(View.VISIBLE);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(view2, ProPertyName.TRANSLATIONX, 0f, 1500f);
                animator1.setDuration(NetConstant.ANIMDURATION);
                animator1.start();
                NEXTSTEPTYPWE = t;
            }
        });
        animator.start();
    }

    @Override
    public void onBackPressed() {
        switch (NEXTSTEPTYPWE){
            case 1:
                AppCompatActivityControls.removeActivity(this);
                finish();
                break;
        }
    }
}
