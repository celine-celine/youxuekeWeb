package com.example.demo;

public class Reply {
    private int id;
    private int topic_id;
    private String des;
    private String author;
    // private int likes;
    
    // the field likes changed to type
    private int type;
    private String date;
    private String oldCommenter;


    public String getOldCommenter() {
        return oldCommenter;
    }

    public void setOldCommenter(String oldCommenter) {
        this.oldCommenter = oldCommenter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    // public int getLikes() {
    //     return likes;
    // }

    // public void setLikes(int likes) {
    //     this.likes = likes;
    // }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
