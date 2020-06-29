package com.example.myapplication;

public class hosp {
    private String name,uid;
    private int icon;
    public hosp(){}
    public hosp(String name,String uid,int icon){
        this.name=name;
        this.uid=uid;
        this.icon=icon;
    }


    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }
}
