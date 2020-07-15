package com.yb.livewy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yb.livewy.R;
import com.yb.livewy.bean.BeautyBody;
import com.yb.livewy.bean.Filter;
import com.yb.livewy.bean.MessageEvent;
import com.yb.livewy.bean.YBZBIMEnum;
import com.yb.livewy.view.ImageViewCheckable;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class FuVideoBeautyBodyRecyclerAdapter extends RecyclerView.Adapter<FuVideoBeautyBodyRecyclerAdapter.FilterViewHolder> {

    private Context context;
    private ArrayList<BeautyBody> dates;
    private OnItemClickListener onItemClickListener;

    public FuVideoBeautyBodyRecyclerAdapter(Context context, ArrayList<BeautyBody> dates){
        this.context = context;
        this.dates = dates;
    }


    public interface OnItemClickListener{
        void itemClickListener(View view, BeautyBody beautyBody, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fu_filter_recycler_layout,parent,false);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.setMargins(20,10,20,10);
        view.setLayoutParams(params);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        try{
                BeautyBody beautyBody = (BeautyBody) dates.get(position);
                holder.imageViewCheckable.setIconValue(beautyBody.getIconId());
                holder.imageViewCheckable.getName().setText(beautyBody.getNameId());
                holder.imageViewCheckable.setChecked(beautyBody.isChecked());
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(view -> {
            for (int i = 0; i < dates.size(); i++) {
                if (dates.get(i).isChecked()){
                    dates.get(i).setChecked(false);
                }
            }
            if (onItemClickListener!=null){
                onItemClickListener.itemClickListener(view,dates.get(position),position);
            }
            dates.get(position).setChecked(!dates.get(position).isChecked());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder{

        public ImageViewCheckable imageViewCheckable;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCheckable = itemView.findViewById(R.id.filter_item);
        }
    }
}
