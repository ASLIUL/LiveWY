package com.yb.livewy.bean;

public class LoginUser {

    /**
     * token : 447a1d72f7e6eb23619fb285355ed5eb-742333
     * accid : 0617114054-9563
     * id : 10
     * imToken : b2ca2c63e51dda2403820425d8f5f1be
     */

    private String token;
    private String accid;
    private int id;
    private String imToken;
    private String qrCodeUrl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "token='" + token + '\'' +
                ", accid='" + accid + '\'' +
                ", id=" + id +
                ", imToken='" + imToken + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                '}';
    }
}
