package com.example.teamproject1;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String sid;
    public String id;
    public String pw;
    public String name;
    public List<Favorites> favoritesList;
    public List<Recent> recentList;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Favorites> getFavoritesList() {
        return favoritesList;
    }

    public void setFavoritesList(List<Favorites> favoritesList) {
        this.favoritesList = favoritesList;
    }

    public List<Recent> getRecentList() {
        return recentList;
    }

    public void setRecentList(List<Recent> recentList) {
        this.recentList = recentList;
    }

    @Override
    public String toString() {
        return "User{" +
                "sid='" + sid + '\'' +
                ", id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", favoritesList=" + favoritesList +
                ", recentList=" + recentList +
                '}';
    }
}
