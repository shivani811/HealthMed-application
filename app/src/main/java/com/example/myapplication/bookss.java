package com.example.myapplication;

public class bookss {
    private String age,contact,name,date,slot,diesease,uid,reason,email,docname,docspecs,hospital;
    private long priority;
    private int accept;
    public bookss(){}
    public bookss(String age,String contact,String name,String date,String slot,String diesease,long priority,String uid,int accept,String reason,String email,String docname,String docspecs,String hospital){
        this.age=age;
        this.contact=contact;
        this.name=name;
        this.date=date;
        this.slot=slot;
        this.diesease=diesease;
        this.priority=priority;
        this.uid=uid;
        this.accept=accept;
        this.reason=reason;
        this.email=email;
        this.docname=docname;
        this.docspecs=docspecs;
        this.hospital=hospital;
    }

    public String getHospital() {
        return hospital;
    }

    public String getDocname() {
        return docname;
    }

    public String getDocspecs() {
        return docspecs;
    }

    public String getEmail() {
        return email;
    }

    public String getReason() {
        return reason;
    }

    public int getAccept() {
        return accept;
    }

    public String getUid() {
        return uid;
    }

    public long getPriority() {
        return priority;
    }

    public String getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getSlot() {
        return slot;
    }

    public String getDiesease() {
        return diesease;
    }
}
