package com.yb.livewy.util;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * created  by liu
 * on 2020-01-16
 * Activity管理类
 */
public class AppCompatActivityControls {

    private static ArrayList<AppCompatActivity> activities = new ArrayList<>();



    public static void addActivity(AppCompatActivity appCompatActivity){
        activities.add(appCompatActivity);
    }
    public static void removeActivity(AppCompatActivity appCompatActivity){
        activities.remove(appCompatActivity);
    }
    public static void removeActivityByIndex(int index){
        activities.remove(index);
    }
    public static void removeAllActivity(){
        for (AppCompatActivity a : activities) {
            if (!a.isFinishing()){
                a.finish();
                activities.remove(a);
            }
        }
    }

    public ArrayList<AppCompatActivity> getActivities() {
        return activities;
    }

    public  void setActivities(ArrayList<AppCompatActivity> activities) {
        this.activities = activities;
    }
    public void nightModelChange(){
        for (AppCompatActivity a : activities) {
            if (!a.isFinishing()){
                a.recreate();
            }
        }
    }

}
