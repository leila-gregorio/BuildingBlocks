package com.gregorio.buildingblocks;


public class Account {
    private String name;
    private String  email;
    private String uid;
    private String uri;

    public Account(String name, String email, String uid, String uri){
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.uri = uri;

    }
    public Account(String name, String email,  String uri){
        this.name = name;
        this.email = email;
        this.uri = uri;

    }
    public Account(String name, String email){
        this.name = name;
        this.email = email;
        this.uid = uid;
    }
    public String getUri(){
        return uri;
    }
    public void setUri(String uri){
        this.uri = uri;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getUID(){
        return uid;
    }
    public void setUID(){
        this.uid = uid;
    }
}
