package com.yb.livewy.ui.model;

import android.util.Log;

import com.yb.livewy.R;
import com.yb.livewy.bean.BeautyBody;
import com.yb.livewy.bean.Filter;
import com.yb.livewy.bean.FilterEnum;

import java.util.ArrayList;

import a.b.a.B;

public class FuEffectNormalParam {


    public static float sColorLevel = 0.3f;// 美白
    public static float sBlurLevel = 0.7f; // 精细磨皮程度
    public static float sRedLevel = 0.3f;// 红润
    public static float sEyeBright = 0.0f;// 亮眼
    public static float sToothWhiten = 0.0f;// 美牙
    public static float sMicroPouch = 0f; // 去黑眼圈
    public static float sMicroNasolabialFolds = 0f; // 去法令纹
    public static float sMicroSmile = 0f; // 微笑嘴角
    public static float sMicroCanthus = 0f; // 眼角
    public static float sMicroPhiltrum = 0.5f; // 人中
    public static float sMicroLongNose = 0.5f; // 鼻子长度
    public static float sMicroEyeSpace = 0.5f; // 眼睛间距
    public static float sMicroEyeRotate = 0.5f; // 眼睛角度

    public static float sCheekThinning = 0f;//瘦脸
    public static float sCheekV = 0.5f;//V脸
    public static float sCheekNarrow = 0f;//窄脸
    public static float sCheekSmall = 0f;//小脸
    public static float sEyeEnlarging = 0.4f;//大眼
    public static float sIntensityChin = 0.3f;//下巴
    public static float sIntensityForehead = 0.3f;//额头
    public static float sIntensityNose = 0.5f;//瘦鼻
    public static float sIntensityMouth = 0.4f;//嘴形

    /**
     * 获取美颜的参数值
     *
     * @param checkId
     * @return
     */
    public static float getValue(FuEffectEnum checkId) {
        switch (checkId) {
            case BLURWHIT:
                return sBlurLevel;
            case BEAUTYWHITE:
                return sColorLevel;
                /*
            case RUDDY:
                return sRedLevel;
            case MICROPOUCH:
                return sMicroPouch;
            case MICRONASOLABIALFOLDS:
                return sMicroNasolabialFolds;
            case EYESBRIGHT:
                return sEyeBright;
            case TOOTHWHITEN:
                return sToothWhiten;
            case BIGEYES:
                return sEyeEnlarging;
            case THINFACE:
                return sCheekThinning;
            case CHECKNARROW:
                return sCheekNarrow;
            case CHEEKV:
                return sCheekV;
            case CHEEKSMALL:
                return sCheekSmall;
            case INTENSITYCHIN:
                return sIntensityChin;
            case INTENTSITYFOREHEAD:
                return sIntensityForehead;
            case THINNOSE:
                return sIntensityNose;
            case INTENTSITYMOUTH:
                return sIntensityMouth;
            case MICROSMILE:
                return sMicroSmile;
            case MICROCANTHUS:
                return sMicroCanthus;
            case MICROPHILTRUM:
                return sMicroPhiltrum;
            case MICROLONGNOSE:
                return sMicroLongNose;
            case MICROEYESPACE:
                return sMicroEyeSpace;
            case MICROEYEROTATE:
                return sMicroEyeRotate;
                 */
            default:
                return 0;
        }
    }


    public enum FuEffectEnum{

        RESET("reset","原图",0f, R.string.origin,R.drawable.ic_reset_white),
        BLURWHIT("blur_white","磨皮",sBlurLevel, R.string.blur_white,R.drawable.ic_blur_white),
        BEAUTYWHITE("beauty_white","美白",sColorLevel, R.string.mei_bai,R.drawable.ic_beauty_white_white_face),
        THINFACE("thin_face","瘦脸",sCheekThinning, R.string.thin_face,R.drawable.ic_thin_face_white),
        THINNOSE("thin_nose","瘦鼻",sIntensityNose, R.string.thin_nose,R.drawable.ic_thin_nose_white),
        BIGEYES("big_eye","大眼",sEyeEnlarging, R.string.big_eyes,R.drawable.ic_big_eyes_white);
/*
        RUDDY("ruddy","红润",sRedLevel, R.string.origin,R.drawable.ic_reset_white),
        EYESBRIGHT("eye_bright","亮眼",sEyeBright, R.string.origin,R.drawable.ic_reset_white),
        TOOTHWHITEN("tooth_whiten","美牙",sToothWhiten, R.string.origin,R.drawable.ic_reset_white),
        MICROPOUCH("micro_pouch","去黑眼圈",sMicroPouch, R.string.origin,R.drawable.ic_reset_white),
        MICRONASOLABIALFOLDS("micro_nasolabia","去法令纹",sMicroNasolabialFolds, R.string.origin,R.drawable.ic_reset_white),
        MICROSMILE("micro_smile","微笑嘴角",sMicroSmile, R.string.origin,R.drawable.ic_reset_white),
        MICROCANTHUS("micro_canthus","眼角",sMicroCanthus, R.string.origin,R.drawable.ic_reset_white),
        MICROPHILTRUM("micro_philtrum","人中",sMicroPhiltrum, R.string.origin,R.drawable.ic_reset_white),
        MICROLONGNOSE("micro_long_nose","鼻子长度",sMicroLongNose, R.string.origin,R.drawable.ic_reset_white),
        MICROEYESPACE("micro_eye_space","眼睛间距",sMicroEyeSpace, R.string.origin,R.drawable.ic_reset_w hite),
        MICROEYEROTATE("micro_eye_rotate","眼睛角度",sMicroEyeRotate, R.string.origin,R.drawable.ic_reset_white),
        CHEEKV("cheek_v","V脸",sCheekV, R.string.origin,R.drawable.ic_reset_white),
        CHECKNARROW("cheek_narrow","窄脸",sCheekNarrow, R.string.origin,R.drawable.ic_reset_white),
        CHEEKSMALL("cheek_small","小脸",sCheekSmall, R.string.origin,R.drawable.ic_reset_white),
        INTENSITYCHIN("intensity_chin","下巴",sIntensityChin, R.string.origin,R.drawable.ic_reset_white),
        INTENTSITYFOREHEAD("intensity_forehead","额头",sIntensityForehead, R.string.origin,R.drawable.ic_reset_white),
        INTENTSITYMOUTH("intensity_mouth","嘴形",sIntensityMouth, R.string.origin,R.drawable.ic_reset_white);

*/
        String key;
        String actionName;
        float actionValue;
        int nameId;
        int iconId;
        FuEffectEnum(String key,String actionName,float actionValue,int nameId,int iconId){
            this.key = key;
            this.actionName = actionName;
            this.actionValue = actionValue;
            this.nameId = nameId;
            this.iconId = iconId;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public float getActionValue() {
            return actionValue;
        }

        public void setActionValue(float actionValue) {
            this.actionValue = actionValue;
        }

        public int getNameId() {
            return nameId;
        }

        public void setNameId(int nameId) {
            this.nameId = nameId;
        }

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        public static ArrayList<BeautyBody> getBeautyBodyByFilterType() {
            FuEffectEnum[] values = FuEffectEnum.values();
            ArrayList<BeautyBody> effectEnums = new ArrayList<>(values.length);
            for (FuEffectEnum value : values) {
                effectEnums.add(value.create());
            }
            return effectEnums;
        }
        public static float getNormalValue(String key){
            float value = 0f;
            FuEffectEnum[] values = FuEffectEnum.values();
            for (FuEffectEnum v:values){
                if (v.getKey().equals(key)){
                    value =  v.getActionValue();
                    break;
                }
            }
            return value;
        }
        public static FuEffectEnum getFuEnum(String key){
            FuEffectEnum[] values = FuEffectEnum.values();
            for (FuEffectEnum v:values){
                if (v.getKey().equals(key)){
                    return v;
                }
            }
            return FuEffectEnum.RESET;
        }

        private BeautyBody create(){
            return new BeautyBody(key,actionName,nameId,iconId);
        }
    }

    //设置美颜参数全部是0
    public static void recoverFaceSkinToDefValue() {
        sBlurLevel = 0f;
        sColorLevel = 0f;
        sCheekThinning = 0f;
        sIntensityNose = 0f;
        sEyeEnlarging = 0f;
        Log.d("LiveStreamLeftPanel", "recoverFaceSkinToDefValue: "+sBlurLevel);
    }



}
