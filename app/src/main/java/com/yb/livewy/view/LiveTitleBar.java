package com.yb.livewy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yb.livewy.R;

public class LiveTitleBar extends RelativeLayout {

    /**
     * title左边的类型：0：无图无文字，1：图片无文本，2：文本无图，3：文本和图都有
     */
    private int leftType;
    private int leftImg;
    private String leftText;
    /**
     * title右边的类型：0：无图无文字，1：图片无文本，2：文本无图，3：文本和图都有
     */
    private int rightType;
    private int rightImg;
    private String rightText;
    /**
     * 中间类型: 0：无搜索框无文字，1：文本无搜索框，2：搜索框无文本
     */
    private int centreType;
    private String centreText;
    private String centreHintText;

    private TextView left_tv;
    private ImageView left_Img;
    private TextView centre_tv;
    private EditText centre_et;
    private TextView right_tv;
    private ImageView right_img;
    private View view;
    private RelativeLayout rela_bg;
    private int bgColor ;

    private Context context;

    private static final String TAG = "LiveTitleBar";


    public LiveTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context, attrs);
    }

    public LiveTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LiveTitleBar);
        leftType = typedArray.getInt(R.styleable.LiveTitleBar_leftType, 0);
        leftImg = typedArray.getResourceId(R.styleable.LiveTitleBar_leftImg, R.drawable.ic_left_point_line_black);
        leftText = typedArray.getString(R.styleable.LiveTitleBar_leftBtnText);
        rightType = typedArray.getInt(R.styleable.LiveTitleBar_rightType, 0);
        rightImg = typedArray.getResourceId(R.styleable.LiveTitleBar_rightImg, R.drawable.ic_right_menu_black);
        rightText = typedArray.getString(R.styleable.LiveTitleBar_rightBtnText);
        centreType = typedArray.getInt(R.styleable.LiveTitleBar_centreType, 0);
        centreText = typedArray.getString(R.styleable.LiveTitleBar_centreText);
        centreHintText = typedArray.getString(R.styleable.LiveTitleBar_centreHintText);
        //bgColor = typedArray.getColor(R.styleable.LiveTitleBar_bgColor,getResources().getColor(R.color.title_blue_1));

        typedArray.recycle();
        /**
         * 初始化view布局文件
         */
        view = inflate(context, R.layout.yb_view_live_title_bar, null);
        left_tv = view.findViewById(R.id.left_tv);
        left_Img = view.findViewById(R.id.left_img);
        centre_et = view.findViewById(R.id.centre_et);
        centre_tv = view.findViewById(R.id.centre_tv);
        right_img = view.findViewById(R.id.right_img);
        right_tv = view.findViewById(R.id.right_tv);
        rela_bg = view.findViewById(R.id.rela_bg);
        addView(view);
        initViewData();
    }

    private void initViewData() {
        switch (leftType) {
            case 0:
                left_tv.setVisibility(GONE);
                left_Img.setVisibility(GONE);
                break;
            case 1:
                left_tv.setVisibility(GONE);
                left_Img.setImageDrawable(getResources().getDrawable(leftImg, null));
                left_Img.setVisibility(VISIBLE);
                break;
            case 2:
                left_Img.setVisibility(GONE);
                left_tv.setText(TextUtils.isEmpty(leftText) ? "返回" : leftText);
                left_tv.setVisibility(VISIBLE);
                break;
            case 3:
                left_tv.setText(TextUtils.isEmpty(leftText) ? "返回" : leftText);
                left_Img.setImageDrawable(getResources().getDrawable(leftImg, null));
                left_tv.setVisibility(VISIBLE);
                left_Img.setVisibility(VISIBLE);
                break;
        }
        switch (rightType) {
            case 0:
                right_tv.setVisibility(GONE);
                right_img.setVisibility(GONE);
                break;
            case 1:
                right_tv.setVisibility(GONE);
                right_img.setImageDrawable(getResources().getDrawable(rightImg, null));
                right_img.setVisibility(VISIBLE);
                break;
            case 2:
                right_img.setVisibility(GONE);
                right_tv.setText(TextUtils.isEmpty(rightText) ? "选项" : rightText);
                right_tv.setVisibility(VISIBLE);
                break;
            case 3:
                right_img.setImageDrawable(getResources().getDrawable(rightImg, null));
                right_tv.setText(TextUtils.isEmpty(rightText) ? "选项" : rightText);
                right_tv.setVisibility(VISIBLE);
                right_img.setVisibility(VISIBLE);
                break;
        }
        switch (centreType){
            case 0:
                centre_tv.setVisibility(GONE);
                centre_et.setVisibility(GONE);
                break;
            case 1:
                centre_tv.setText(TextUtils.isEmpty(centreText)?getResources().getString(R.string.app_name):centreText);
                centre_et.setVisibility(GONE);
                centre_tv.setVisibility(VISIBLE);
                break;
            case 2:
                centre_tv.setVisibility(GONE);
                centre_et.setHint(TextUtils.isEmpty(centreHintText)?getResources().getString(R.string.input_search_hint_everything):centreHintText);
                centre_et.setVisibility(VISIBLE);
                break;
        }
        //rela_bg.setBackgroundColor(context.getResources().getColor(bgColor));
    }

    public void setRela_bgColor(int color){
        rela_bg.setBackgroundColor(color);
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
        initViewData();
    }

    public int getLeftImg() {
        return leftImg;
    }

    public void setLeftImg(int leftImg) {
        this.leftImg = leftImg;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
        initViewData();
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        rela_bg.setBackgroundColor(bgColor);
    }

    public int getRightImg() {
        return rightImg;
    }

    public void setRightImg(int rightImg) {
        this.rightImg = rightImg;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public int getCentreType() {
        return centreType;
    }

    public void setCentreType(int centreType) {
        this.centreType = centreType;
        initViewData();
    }

    public String getCentreText() {
        return centreText;
    }

    public void setCentreText(String centreText) {
        this.centreText = centreText;
    }

    public String getCentreHintText() {
        return centreHintText;
    }

    public void setCentreHintText(String centreHintText) {
        this.centreHintText = centreHintText;
    }

    public TextView getLeft_tv() {
        return left_tv;
    }


    public ImageView getLeft_Img() {
        return left_Img;
    }

    public TextView getCentre_tv() {
        return centre_tv;
    }

    public EditText getCentre_et() {
        return centre_et;
    }

    public TextView getRight_tv() {
        return right_tv;
    }

    public ImageView getRight_img() {
        return right_img;
    }

    public RelativeLayout getRela_bg() {
        Log.d(TAG, "getRela_bg: ");
        return rela_bg;
    }
}
