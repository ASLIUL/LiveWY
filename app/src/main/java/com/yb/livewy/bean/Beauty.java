package com.yb.livewy.bean;

/**
 * create by liu
 * on 2020/4/15 3:45 PM
 **/
public class Beauty {

    //曝光度
    private int exposure;

    //磨皮
    private int grinding;

    //滤镜强度
    private float filter;

    public Beauty() {
    }

    public Beauty(int exposure, int grinding, float filter) {
        this.exposure = exposure;
        this.grinding = grinding;
        this.filter = filter;
    }

    public int getExposure() {
        return exposure;
    }

    public void setExposure(int exposure) {
        this.exposure = exposure;
    }

    public int getGrinding() {
        return grinding;
    }

    public void setGrinding(int grinding) {
        this.grinding = grinding;
    }

    public float getFilter() {
        return filter;
    }

    public void setFilter(float filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "{" +
                "exposure=" + exposure +
                ", grinding=" + grinding +
                ", filter=" + filter +
                '}';
    }
}
