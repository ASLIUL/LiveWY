package com.yb.livewy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yb.livewy.R;
import com.yb.livewy.bean.Contacts;
import com.yb.livewy.bean.MessageEvent;
import com.yb.livewy.bean.YBZBIMEnum;
import com.yb.livewy.view.CustomRoundView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShareContactsListRecyclerAdapter extends RecyclerView.Adapter<ShareContactsListRecyclerAdapter.FriendsListViewHolder> {


    private Context context;
    private List<Contacts> contacts;
    private List<Contacts> chooseContacts = new ArrayList<>();

    public ShareContactsListRecyclerAdapter(Context context, List<Contacts> contacts){
        this.context = context;
        this.contacts = contacts;
        chooseContacts.clear();
    }

    public List<Contacts> getChooseContacts() {
        return chooseContacts;
    }

    @NonNull
    @Override
    public ShareContactsListRecyclerAdapter.FriendsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_share_contacts_list_layout,parent,false);
        return new FriendsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShareContactsListRecyclerAdapter.FriendsListViewHolder holder, int position) {

        try{
            holder.friendName.setText(contacts.get(position).getName()+"");
            holder.friendName.setText(contacts.get(position).getName());
            Glide.with(context).load(R.drawable.ic_not_checked_blue).into(holder.isChecked);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener((v)->{
            if (chooseContacts.size()<1){
                Glide.with(context).load(R.drawable.ic_checked_blue).into(holder.isChecked);
                chooseContacts.add(contacts.get(position));
                EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.LIVESHARECHOOSEPEOPLE,chooseContacts));
            }else {
                boolean isHas = false;
                Iterator<Contacts> iterable = chooseContacts.iterator();
                while (iterable.hasNext()){
                    Contacts choose = iterable.next();
                    if (choose.getChatAccount().equalsIgnoreCase(contacts.get(position).getChatAccount())){
                        isHas = true;
                        iterable.remove();
                        Glide.with(context).load(R.drawable.ic_not_checked_blue).into(holder.isChecked);
                        break;
                    }
                }
                if (!isHas){
                    chooseContacts.add(contacts.get(position));
                    Glide.with(context).load(R.drawable.ic_checked_blue).into(holder.isChecked);
                }
                EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.LIVESHARECHOOSEPEOPLE,chooseContacts));
            }
        });
    }
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class FriendsListViewHolder extends RecyclerView.ViewHolder{

        public CustomRoundView header;
        public TextView friendName;
        public ImageView isChecked;


        public FriendsListViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.friend_header);
            friendName = itemView.findViewById(R.id.friend_name);
            isChecked = itemView.findViewById(R.id.is_check);
        }
    }
}
