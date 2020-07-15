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

public class FuVideoFilterRecyclerAdapter extends RecyclerView.Adapter<FuVideoFilterRecyclerAdapter.FilterViewHolder> {

    private Context context;
    private ArrayList<Filter> dates;
    private OnItemClickListener onItemClickListener;

    public FuVideoFilterRecyclerAdapter(Context context, ArrayList<Filter> dates){
        this.context = context;
        this.dates = dates;
    }


    public interface OnItemClickListener{
        void itemClickListener(View view,Object filter,int position);
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
            holder.imageViewCheckable.setIconValue(dates.get(position).getIconId());
            holder.imageViewCheckable.getName().setText(dates.get(position).getNameId());
            holder.imageViewCheckable.setChecked(dates.get(position).isChecked());

        }catch (Exception e){
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(view -> {
            for (int i = 0; i < dates.size(); i++) {
                if (dates.get(i).isChecked()){
                    dates.get(i).setChecked(false);
                }
            }
            dates.get(position).setChecked(!dates.get(position).isChecked());
            notifyDataSetChanged();
            EventBus.getDefault().post(new MessageEvent(YBZBIMEnum.MessageType.CHOOSEFILTER,dates.get(position)));
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
