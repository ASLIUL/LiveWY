package com.yb.livewy.ui.activity;

import android.Manifest;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.yb.livewy.R;
import com.yb.livewy.databinding.ActivityLoginBinding;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.ShapeUtils;
import com.yb.livewy.vm.LoginViewModel;

import java.util.List;

/**
 * create by liu
 * on 2020/3/30 10:52 AM
 **/
public class LoginActivity extends BaseAppActivity<ActivityLoginBinding, LoginViewModel> {


    private static final String TAG = "LoginActivity";
    @Override
    protected void initData() {
        binding.setData(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setAppCompatActivity(LoginActivity.this);
        PermissionX.init(this)
                .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.ACCESS_WIFI_STATE

                ).request(new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                if (!allGranted){
                    PermissionX.init(LoginActivity.this).permissions(deniedList).request((allGranted1, grantedList1, deniedList1) -> {});
                }
            }
        });
    }

    @Override
    protected void initViewListener() {
        binding.pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == NetConstant.NUMBER_SIX){
                    binding.login.setBackground(ShapeUtils.setFullShape("#FF4A9AFD","#FF4A9AFD"));
                    binding.login.setEnabled(true);
                }else {
                    binding.login.setBackground(ShapeUtils.setFullShape("#FFD8D8DA","#FFD8D8DA"));
                    binding.login.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {

    }
}
