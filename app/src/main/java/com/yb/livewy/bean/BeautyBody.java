package com.yb.livewy.bean;

import com.yb.livewy.ui.model.FuEffectNormalParam;

public class BeautyBody {

    private String key;
    private String name;
    private int iconId;
    private int nameId;
    private boolean isChecked;

    public BeautyBody(FuEffectNormalParam.FuEffectEnum fuEffectEnum){
        this(fuEffectEnum.getKey(),fuEffectEnum.getActionName(),fuEffectEnum.getNameId(),fuEffectEnum.getIconId());
    }

    public BeautyBody(String key,String name,int nameId,int iconId){
        this(key,name,iconId,nameId,false);
    }

    public BeautyBody(String key, String name, int iconId, int nameId, boolean isChecked) {
        this.key = key;
        this.name = name;
        this.iconId = iconId;
        this.nameId = nameId;
        this.isChecked = isChecked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", iconId=" + iconId +
                ", nameId=" + nameId +
                ", isChecked=" + isChecked +
                '}';
    }
}
