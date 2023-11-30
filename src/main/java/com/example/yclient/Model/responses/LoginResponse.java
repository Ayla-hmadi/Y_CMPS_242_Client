package com.example.yclient.Model.responses;

import com.example.yclient.Model.Post;
import com.example.yclient.Model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse extends BaseResponse {
    User user;
    List<Post> posts = new ArrayList<>();
    List<Post> interests = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getInterests() {
        return interests;
    }

    public void setInterests(List<Post> interests) {
        this.interests = interests;
    }
}

