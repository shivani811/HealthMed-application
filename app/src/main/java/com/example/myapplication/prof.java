package com.example.myapplication;

public class prof {
    private String age,contact,name,uid;
    public prof(){}
    public prof(String name,String age,String contact,String uid){
        this.name=name;
        this.age=age;
        this.contact=contact;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
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
}
