package com.yb.livewy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.yb.livewy.R;
import com.yb.livewy.util.ShapeUtils;

public class ImageViewCheckable extends LinearLayout implements Checkable {

    private Context context;

    private ImageView icon;

    private TextView name;

    private int iconValue;

    private String iconName;
    private boolean isCheckable;



    public ImageViewCheckable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(attrs);
    }

    public ImageViewCheckable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }

    public ImageViewCheckable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView(attrs);
    }


    private  void initView(AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ImageViewCheckable);
        iconValue = typedArray.getResourceId(R.styleable.ImageViewCheckable_icon,R.drawable.demo_icon_cancel);
        isCheckable = typedArray.getBoolean(R.styleable.ImageViewCheckable_checkable,false);
        iconName = typedArray.getString(R.styleable.ImageViewCheckable_name);
        typedArray.recycle();
        View view = LayoutInflater.from(context).inflate(R.layout.live_image_checkable_layout,null,false);
        icon = view.findViewById(R.id.icon);
        name = view.findViewById(R.id.name);
        addView(view);
        if (iconValue>0){
            Glide.with(context).load(iconValue).into(icon);
        }
        if (!TextUtils.isEmpty(iconName)){
            name.setText(iconName);
        }
        setChecked(isCheckable);
    }


    @Override
    public void setChecked(boolean b) {
        this.isCheckable = b;
        if (isCheckable){
            icon.setImageDrawable(ShapeUtils.setStrokeShapeByRoundImg("#FF0090FF"));
            Glide.with(context).load(iconValue).into(icon);
            name.setTextColor(context.getResources().getColor(R.color.hy_color));
        }else {
            icon.setImageDrawable(ShapeUtils.setStrokeShapeByRoundImg("#000090FF"));
            Glide.with(context).load(iconValue).into(icon);
            name.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public int getIconValue() {
        return iconValue;
    }

    public void setIconValue(int iconValue) {
        this.iconValue = iconValue;
        Glide.with(context).load(iconValue).into(icon);
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    @Override
    public boolean isChecked() {
        return isCheckable;
    }

    @Override
    public void toggle() {
        isCheckable = !isCheckable;
        setChecked(isCheckable);
    }
}
