package com.yb.livewy.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.yb.livewy.R;
import com.yb.livewy.bean.Contacts;
import com.yb.livewy.bean.LiveRtmpUrl;
import com.yb.livewy.bean.MessageEvent;
import com.yb.livewy.bean.YBZBIMEnum;
import com.yb.livewy.databinding.DialogShareLiveLayoutBinding;
import com.yb.livewy.ui.adapter.LiveShareChoosePeopleRecyclerAdapter;
import com.yb.livewy.ui.adapter.ShareContactsListRecyclerAdapter;
import com.yb.livewy.util.NetConstant;
import com.yb.livewy.util.SaveUserData;
import com.yb.livewy.util.ToastUtil;
import com.yb.livewy.vm.ShareLiveViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShareLiveActivity extends AppCompatActivity {

    DialogShareLiveLayoutBinding binding;
    ShareLiveViewModel viewModel;
    private ShareContactsListRecyclerAdapter adapter;
    private List<Contacts> contacts = new ArrayList<>();
    private List<Contacts> chooseContacts = new ArrayList<>();
    private static final String TAG = "ShareSomethingActivity";
    private LiveShareChoosePeopleRecyclerAdapter liveShareChoosePeopleRecyclerAdapter;
    private LiveRtmpUrl liveRtmpUrl;
    private LoadingPopupView loadingPopupView;
    private TextView progress;
    private AlertDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.dialog_share_live_layout);
        viewModel = new ViewModelProvider(this).get(ShareLiveViewModel.class);
        binding.setLifecycleOwner(this);
        loadingPopupView =  new XPopup.Builder(this)
                .asLoading("正在加载中");
        loadingPopupView.show();
        liveRtmpUrl = (LiveRtmpUrl) getIntent().getSerializableExtra("liveData");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.contactsRecycler.setLayoutManager(linearLayoutManager);
        adapter = new ShareContactsListRecyclerAdapter(this,contacts);
        binding.contactsRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        binding.chooseRecycler.setLayoutManager(manager);
        liveShareChoosePeopleRecyclerAdapter = new LiveShareChoosePeopleRecyclerAdapter(this,chooseContacts);
        binding.chooseRecycler.setAdapter(liveShareChoosePeopleRecyclerAdapter);
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

    private void initViewListener() {
        viewModel.getContactsLiveData().observe(this,contacts -> {
            if (contacts != null && !contacts.isEmpty()){
                this.contacts.clear();
                this.contacts.addAll(contacts);
                adapter.notifyDataSetChanged();
                if (loadingPopupView.isShow()){
                    loadingPopupView.delayDismiss(300);
                }
            }
        });
        viewModel.getMyFriends();

        viewModel.getSendNumLiveData().observe(this,num ->{
            if (num == chooseContacts.size()){
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                ToastUtil.showToast(NetConstant.SHARE_DONE);
                finish();
            }
        } );
        binding.sendShare.setOnClickListener(view -> {
            if (chooseContacts!=null && !chooseContacts.isEmpty()){
                showProgressDialog();
                viewModel.sendShareMessage(chooseContacts,liveRtmpUrl.getCover_img(),liveRtmpUrl.getName(),liveRtmpUrl.getId()+"",SaveUserData.getInstance(this).getUserName(), SaveUserData.getInstance(this).getUserId());
            }
        });
        binding.cancelShare.setOnClickListener(view -> finish());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEvent(MessageEvent messageEvent){
        if (messageEvent== null){
            return;
        }
        if (messageEvent.getMessageType() == YBZBIMEnum.MessageType.LIVESHARECHOOSEPEOPLE){
            chooseContacts.clear();
            chooseContacts.addAll((Collection<? extends Contacts>) messageEvent.getObject());
            liveShareChoosePeopleRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private void showProgressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_share_message_progress_layout,null,false);
        builder.setView(view);
        progress = view.findViewById(R.id.progress);
        progress.setText(R.string.sending);
        progressDialog = builder.create();
        progressDialog.show();
    }


}
