package com.yb.livewy.bean;

import com.netease.LSMediaCapture.lsMediaCapture;
import com.netease.vcloud.video.effect.VideoEffect;

import java.io.Serializable;

/**
 * create by liu
 * on 2020/4/13 10:44 AM
 **/
public class PublishParam implements Serializable {

    //推流地址
    String pushUrl;
    //直播方式
    lsMediaCapture.StreamType streamType;
    //推流格式
    lsMediaCapture.FormatType formatType;
    //文件录制地址，当formatType 为 MP4 或 RTMP_AND_MP4 时有效
    String recordPath;
    //清晰度
    lsMediaCapture.VideoQuality videoQuality;
    //是否强制16:9
    boolean isScale_16x9;
    //是否使用滤镜
    boolean useFilter;
    //滤镜类型
    VideoEffect.FilterType filterType;
    //是否默认前置摄像头
    boolean frontCamera;
    //是否添加水印
    boolean watermark;
    //是否开启QOS
    boolean qosEnable;
    // 1:流畅优先, 2:清晰优先
    int qosEncodeMode;
    //是否添加涂鸦
    boolean graffitiOn;
    //是否上传SDK运行日志
    boolean uploadLog;

    public PublishParam(String pushUrl, lsMediaCapture.StreamType streamType, lsMediaCapture.FormatType formatType, String recordPath, lsMediaCapture.VideoQuality videoQuality, boolean isScale_16x9, boolean useFilter, VideoEffect.FilterType filterType, boolean frontCamera, boolean watermark, boolean qosEnable, int qosEncodeMode, boolean graffitiOn, boolean uploadLog) {
        this.pushUrl = pushUrl;
        this.streamType = streamType;
        this.formatType = formatType;
        this.recordPath = recordPath;
        this.videoQuality = videoQuality;
        this.isScale_16x9 = isScale_16x9;
        this.useFilter = useFilter;
        this.filterType = filterType;
        this.frontCamera = frontCamera;
        this.watermark = watermark;
        this.qosEnable = qosEnable;
        this.qosEncodeMode = qosEncodeMode;
        this.graffitiOn = graffitiOn;
        this.uploadLog = uploadLog;
    }

    public PublishParam() {
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public lsMediaCapture.StreamType getStreamType() {
        return streamType;
    }

    public void setStreamType(lsMediaCapture.StreamType streamType) {
        this.streamType = streamType;
    }

    public lsMediaCapture.FormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(lsMediaCapture.FormatType formatType) {
        this.formatType = formatType;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public lsMediaCapture.VideoQuality getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(lsMediaCapture.VideoQuality videoQuality) {
        this.videoQuality = videoQuality;
    }

    public boolean isScale_16x9() {
        return isScale_16x9;
    }

    public void setScale_16x9(boolean scale_16x9) {
        isScale_16x9 = scale_16x9;
    }

    public boolean isUseFilter() {
        return useFilter;
    }

    public void setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
    }

    public VideoEffect.FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(VideoEffect.FilterType filterType) {
        this.filterType = filterType;
    }

    public boolean isFrontCamera() {
        return frontCamera;
    }

    public void setFrontCamera(boolean frontCamera) {
        this.frontCamera = frontCamera;
    }

    public boolean isWatermark() {
        return watermark;
    }

    public void setWatermark(boolean watermark) {
        this.watermark = watermark;
    }

    public boolean isQosEnable() {
        return qosEnable;
    }

    public void setQosEnable(boolean qosEnable) {
        this.qosEnable = qosEnable;
    }

    public int getQosEncodeMode() {
        return qosEncodeMode;
    }

    public void setQosEncodeMode(int qosEncodeMode) {
        this.qosEncodeMode = qosEncodeMode;
    }

    public boolean isGraffitiOn() {
        return graffitiOn;
    }

    public void setGraffitiOn(boolean graffitiOn) {
        this.graffitiOn = graffitiOn;
    }

    public boolean isUploadLog() {
        return uploadLog;
    }

    public void setUploadLog(boolean uploadLog) {
        this.uploadLog = uploadLog;
    }

    @Override
    public String toString() {
        return "{" +
                "pushUrl='" + pushUrl + '\'' +
                ", streamType=" + streamType +
                ", formatType=" + formatType +
                ", recordPath='" + recordPath + '\'' +
                ", videoQuality=" + videoQuality +
                ", isScale_16x9=" + isScale_16x9 +
                ", useFilter=" + useFilter +
                ", filterType=" + filterType +
                ", frontCamera=" + frontCamera +
                ", watermark=" + watermark +
                ", qosEnable=" + qosEnable +
                ", qosEncodeMode=" + qosEncodeMode +
                ", graffitiOn=" + graffitiOn +
                ", uploadLog=" + uploadLog +
                '}';
    }
}
