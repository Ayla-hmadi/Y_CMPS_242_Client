package com.example.yclient.Service;

import com.example.yclient.Model.Post;
import com.example.yclient.Model.RegisterCommand;
import com.example.yclient.Util.ClientSocket;
import com.example.yclient.Model.LoginCommand;
import com.example.yclient.Model.responses.LoginResponse;
import com.example.yclient.Util.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class BackendService {
    public static LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public static LoginResponse loginResponse;
    public static List<Post> feedPosts;

    public LoginResponse Login(String username, String password) {
//        var hashedPasswrd = BCrypt.hashpw(password, BCrypt.gensalt());
//        var command = new LoginCommand(username, hashedPasswrd);
        var command = new LoginCommand(username, password);
        NetworkManager.getInstance().send("login");
        Gson gson = new Gson();
        NetworkManager.getInstance().send(gson.toJson(command));
        var json = NetworkManager.getInstance().tryReceive();
        System.out.println(json);
        if (json != null) {
            loginResponse = gson.fromJson(json, LoginResponse.class);
            GetFeedPosts();
            return loginResponse;
        } else {
            return null;
        }
    }

    public LoginResponse SignUp(String username, String name, String email, String password, String address) {
        var command = new RegisterCommand(username, name, email, password, address);
        NetworkManager.getInstance().send("register");
        Gson gson = new Gson();
        NetworkManager.getInstance().send(gson.toJson(command));
        var json = NetworkManager.getInstance().tryReceive();
        if (json != null) {
            loginResponse = gson.fromJson(json, LoginResponse.class);
            System.out.println("Sign up success");
            return loginResponse;
        } else {
            return null;
        }
    }

    public void GetFeedPosts() {
        NetworkManager.getInstance().send("getPostsByFollowedUsers");
        NetworkManager.getInstance().send(loginResponse.getUser().getUsername());
        Gson gson = new Gson();
        var json = NetworkManager.getInstance().tryReceive();
        System.out.println(json);
        if (json != null) {
            feedPosts = gson.fromJson(json, new TypeToken<List<Post>>() {
            }.getType());
        }
    }

    public void UpdateMyPosts() {
        NetworkManager.getInstance().send("my posts");
        Gson gson = new Gson();
        var json = NetworkManager.getInstance().tryReceive();
        if (json != null) {
            List<Post> posts = gson.fromJson(json, new TypeToken<List<Post>>() {
            }.getType());
            loginResponse.setPosts(posts);
        }
    }

    public boolean Post(String content) {
        NetworkManager.getInstance().send("add post");
        NetworkManager.getInstance().send(content);

        Gson gson = new Gson();
        var json = NetworkManager.getInstance().tryReceive();
        if (json != null) {
            UpdateMyPosts();
            return true;
        } else {
            return false;
        }
    }
}
