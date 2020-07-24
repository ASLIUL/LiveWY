package com.yb.livewy.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contacts")
public class Contacts implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int _id;

    private int id;
    //名字，群名或者好友名字
    private String name;
    //头像
    private String header;
    //对方的聊天账号
    private String chatAccount;
    //1 好友，2 群组
    private int ContactType;
    //用于联系人排序
    private String letter;
    //群id，用于自己的服务器查询信息
    private int groupId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getChatAccount() {
        return chatAccount;
    }

    public void setChatAccount(String chatAccount) {
        this.chatAccount = chatAccount;
    }

    public int getContactType() {
        return ContactType;
    }

    public void setContactType(int contactType) {
        ContactType = contactType;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", header='" + header + '\'' +
                ", chatAccount='" + chatAccount + '\'' +
                ", ContactType='" + ContactType + '\'' +
                ", letter='" + letter + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
