package com.yb.livewy.bean;

import java.util.List;

/**
 * create by liu
 * on 2020/4/11 10:21 AM
 **/
public class VideoListBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"videos":[{"videoCover":"/api/cover/1313.png","videoAuthorId":"uid-12314124231","videoId":"av-23432412","videoTitle":"目标：一天上王者","videoWatchCount":"131212","AuthorName":"小白","whyShow":"国服第一林丹"},{"videoCover":"/api/cover/1313.png","videoAuthorId":"uid-12314124231","videoId":"av-23432412","videoTitle":"目标：一天上王者","videoWatchCount":"131212","AuthorName":"小白","whyShow":"国服第一林丹"},{"videoCover":"/api/cover/1313.png","videoAuthorId":"uid-12314124231","videoId":"av-23432412","videoTitle":"目标：一天上王者","videoWatchCount":"131212","AuthorName":"小白","whyShow":"国服第一林丹"},{"videoCover":"/api/cover/1313.png","videoAuthorId":"uid-12314124231","videoId":"av-23432412","videoTitle":"目标：一天上王者","videoWatchCount":"131212","AuthorName":"小白","whyShow":"国服第一林丹"},{"videoCover":"/api/cover/1313.png","videoAuthorId":"uid-12314124231","videoId":"av-23432412","videoTitle":"目标：一天上王者","videoWatchCount":"131212","AuthorName":"小白","whyShow":"国服第一林丹"},{"videoCover":"/api/cover/1313.png","videoAuthorId":"uid-12314124231","videoId":"av-23432412","videoTitle":"目标：一天上王者","videoWatchCount":"131212","AuthorName":"小白","whyShow":"国服第一林丹"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<VideosBean> videos;

        public List<VideosBean> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosBean> videos) {
            this.videos = videos;
        }

        public static class VideosBean {
            /**
             * videoCover : /api/cover/1313.png
             * videoAuthorId : uid-12314124231
             * videoId : av-23432412
             * videoTitle : 目标：一天上王者
             * videoWatchCount : 131212
             * AuthorName : 小白
             * whyShow : 国服第一林丹
             */

            private String videoCover;
            private String videoAuthorId;
            private String videoId;
            private String videoTitle;
            private String videoWatchCount;
            private String AuthorName;
            private String whyShow;

            public String getVideoCover() {
                return videoCover;
            }

            public void setVideoCover(String videoCover) {
                this.videoCover = videoCover;
            }

            public String getVideoAuthorId() {
                return videoAuthorId;
            }

            public void setVideoAuthorId(String videoAuthorId) {
                this.videoAuthorId = videoAuthorId;
            }

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getVideoTitle() {
                return videoTitle;
            }

            public void setVideoTitle(String videoTitle) {
                this.videoTitle = videoTitle;
            }

            public String getVideoWatchCount() {
                return videoWatchCount;
            }

            public void setVideoWatchCount(String videoWatchCount) {
                this.videoWatchCount = videoWatchCount;
            }

            public String getAuthorName() {
                return AuthorName;
            }

            public void setAuthorName(String AuthorName) {
                this.AuthorName = AuthorName;
            }

            public String getWhyShow() {
                return whyShow;
            }

            public void setWhyShow(String whyShow) {
                this.whyShow = whyShow;
            }
        }
    }
}
