package com.example.myapplication;

public class doc {
    private String dname,dage,dcontact,dspec,dexp,hospital,time;
    public doc(){}
    public doc(String dname,String dage,String dcontact,String dspec,String dexp,String hospital,String time){
        this.dname=dname;
        this.dage=dage;
        this.dcontact=dcontact;
        this.dspec=dspec;
        this.dexp=dexp;
        this.hospital=hospital;
        this.time=time;
    }

    public String getTime() {
        return time;
    }

    public String getHospital() {
        return hospital;
    }

    public String getDname() {
        return dname;
    }

    public String getDage() {
        return dage;
    }

    public String getDcontact() {
        return dcontact;
    }

    public String getDspec() {
        return dspec;
    }

    public String getDexp() {
        return dexp;
    }
}
