package com.yb.livewy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yb.livewy.R;
import com.yb.livewy.bean.ChatRoomMsg;

import java.util.List;

public class LivePlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ChatRoomMsg> chatRoomMsgs;

    public LivePlayerRecyclerAdapter(Context context, List<ChatRoomMsg> chatRoomMsgs){
        this.context = context;
        this.chatRoomMsgs = chatRoomMsgs;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_live_player_message,parent,false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder viewHolder = (MessageViewHolder) holder;
        try {
            viewHolder.msgConnect.setText(chatRoomMsgs.get(position).getChatConnect());
            viewHolder.name.setText(chatRoomMsgs.get(position).getUserName()+" : ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return chatRoomMsgs.size();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder{

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView msgConnect;
        public ImageView level;
        public TextView name;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            msgConnect = itemView.findViewById(R.id.msg_connect);
            level = itemView.findViewById(R.id.level_img);
            name = itemView.findViewById(R.id.name);
        }
    }
}
