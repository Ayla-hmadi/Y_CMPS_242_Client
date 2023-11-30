package com.example.yclient.Model.responses;

import com.example.yclient.Model.Post;

import java.util.List;

public class MyPostsResponse extends BaseResponse {
    List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
