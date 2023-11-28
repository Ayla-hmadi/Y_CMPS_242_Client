package com.example.yclient.Model.responses;

import com.example.yclient.Model.Post;

import java.util.List;

public class UserInfoResponse {
    private String username;
    private String name;
    private int numberOfFollowers;
    private int numberOfFollowing;
    private List<Post> posts;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFollowers(int followers) {
        this.numberOfFollowers = followers;
    }

    public void setFollowing(int following) {
        this.numberOfFollowing = following;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getFollowers() {
        return numberOfFollowers;
    }

    public int getFollowing() {
        return numberOfFollowing;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
