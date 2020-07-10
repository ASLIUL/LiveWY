package com.yb.livewy.bean;

/**
 * create by liu
 * on 2020/4/25 11:33 AM
 **/
public class FileUpLoadBean {


    /**
     * id : 6358
     * key : hbvgnjhtp8jzlymiu3p9.jpg
     * name : 1587785377675.jpg
     * type : image/jpeg
     * size : 7867
     * url : http://www.moo9995.com:8088/wx/storage/fetch/hbvgnjhtp8jzlymiu3p9.jpg
     * addTime : 2020-04-25 11:29:41
     * updateTime : 2020-04-25 11:29:41
     */

    private int id;
    private String key;
    private String name;
    private String type;
    private int size;
    private String url;
    private String addTime;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "FileUpLoadBean{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                ", addTime='" + addTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
