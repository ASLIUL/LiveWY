package com.yb.livewy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.yb.livewy.R;
import com.yb.livewy.util.ToastUtil;

import java.util.ArrayList;

/**
 * create by liu
 * on 2020/5/9 11:33 AM
 **/
public class ChatMessageRecyclerAdapter extends RecyclerView.Adapter<ChatMessageRecyclerAdapter.ChatMessage> {

    private Context context;
    private ArrayList<IMMessage> chatMessageBeans;

    public ChatMessageRecyclerAdapter(Context context, ArrayList<IMMessage> chatMessageBeans){
        this.context = context;
        this.chatMessageBeans = chatMessageBeans;
    }

    @NonNull
    @Override
    public ChatMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_live_video_chat_layout,parent,false);
        view.setOnClickListener((v)->{
            ToastUtil.showToast("点击了");
        });
        return new ChatMessage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessage holder, int position) {
        holder.chatTv.setText(chatMessageBeans.get(position).getSessionId()+":"+chatMessageBeans.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return chatMessageBeans.size();
    }

    class ChatMessage extends RecyclerView.ViewHolder{

        public TextView chatTv;

        public ChatMessage(@NonNull View itemView) {
            super(itemView);
            chatTv = itemView.findViewById(R.id.chat_tv);
        }
    }
}
