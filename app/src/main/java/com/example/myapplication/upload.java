package com.example.myapplication;

public class upload {
    private String rname,rurl,message,uid,fname,contact,hospital;
   public upload(){
       //empty constructor needed

    }
    public upload(String name,String imageurl,String message,String uid,String fname,String contact,String hospital)
    {
        rname=name;
        rurl=imageurl;
        this.message=message;
        this.uid=uid;
        this.fname=fname;
        this.contact=contact;
        this.hospital=hospital;
    }

    public String getHospital() {
        return hospital;
    }

    public String getContact() {
        return contact;
    }

    public String getFname() {
        return fname;
    }

    public String getMessage() {
        return message;
    }

    public String getUid() {
        return uid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRurl() {
        return rurl;
    }

    public void setRurl(String rurl) {
        this.rurl = rurl;
    }
}
