package com.yb.livewy.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yb.livewy.R;
import com.yb.livewy.bean.Contacts;

import java.util.List;

public class LiveShareChoosePeopleRecyclerAdapter extends RecyclerView.Adapter<LiveShareChoosePeopleRecyclerAdapter.PeopleViewHolder> {

    private Context context;
    private List<Contacts> contacts;
    public LiveShareChoosePeopleRecyclerAdapter(Context context, List<Contacts> contacts){
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_live_share_people_layout,parent,false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        try {
            if (TextUtils.isEmpty(contacts.get(position).getHeader())){
                Glide.with(context).load(R.drawable.ic_normal_header).into(holder.header);
            }else {
                Glide.with(context).load(contacts.get(position).getHeader()).into(holder.header);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder{

        public ImageView header;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
        }
    }
}
