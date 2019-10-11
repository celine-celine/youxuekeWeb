package com.example.demo;

public class User {
    private String ID;
    private String name;
    private String open_id;
    private int identity;

    //--------------------------------------------------------------------------------------------------------
    //added for Web version.
    private String password;
    private String avatarUrl;
    //--------------------------------------------------------------------------------------------------------

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

