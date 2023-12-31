package com.example.yclient.Service;

import com.example.yclient.Model.*;
import com.example.yclient.Model.enums.ReactionType;
import com.example.yclient.Model.responses.UserInfoResponse;
import com.example.yclient.Util.ClientSocket;
import com.example.yclient.Model.responses.LoginResponse;
import com.example.yclient.Util.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.mindrot.jbcrypt.BCrypt;

import java.lang.reflect.Type;
import java.util.List;

public class BackendService {
    public static LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public static LoginResponse loginResponse;
    public static List<Post> feedPosts;
    public static UserInfoResponse currentUserProfile;

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
            if (loginResponse.isSuccess()) {
                GetFeedPosts();
            }
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

    public void followUser(String usernameToFollow) {
        NetworkManager.getInstance().send("follow");
        NetworkManager.getInstance().send(usernameToFollow);

        var response = NetworkManager.getInstance().tryReceive();
        System.out.println("Server response to follow request: " + response);
    }

    public void unfollowUser(String usernameToUnfollow) {
        NetworkManager.getInstance().send("unfollow");
        NetworkManager.getInstance().send(usernameToUnfollow);

        String response = NetworkManager.getInstance().tryReceive();
        System.out.println("Server response to unfollow request: " + response);
    }

    public List<User> getRandomUsersToFollow() {
        try {
            NetworkManager.getInstance().send("GetRandomUsersToFollow");

            String jsonResponse = NetworkManager.getInstance().tryReceive();
            System.out.println(jsonResponse);
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                Gson gson = new Gson();
                Type userListType = new TypeToken<List<User>>() {
                }.getType();
                return gson.fromJson(jsonResponse, userListType);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void reactToPost(int postId, ReactionType reactionType) {
        var command = new AddReactionCommand(postId, reactionType);
        NetworkManager.getInstance().send("react");
        Gson gson = new Gson();
        NetworkManager.getInstance().send(gson.toJson(command));
    }

    public void getUserInfo(String username) {
        NetworkManager.getInstance().send("GetUserInfoForUserProfile");
        NetworkManager.getInstance().send(username);

        Gson gson = new Gson();
        var json = NetworkManager.getInstance().tryReceive();
        System.out.println(json);
        if (json != null) {
            currentUserProfile = gson.fromJson(json, UserInfoResponse.class);
        }
    }

    public List<Following> getFollowing() {
        NetworkManager.getInstance().send("getFollowingByUsername");
        NetworkManager.getInstance().send(loginResponse.getUser().getUsername());

        Gson gson = new Gson();
        var json = NetworkManager.getInstance().tryReceive();
        System.out.println(json);
        if (json != null) {
            return gson.fromJson(json, new TypeToken<List<Following>>() {
            }.getType());
        }

        return null;
    }

    public List<Following> getFollowers() {
        NetworkManager.getInstance().send("getFollowersByUsername");
        NetworkManager.getInstance().send(loginResponse.getUser().getUsername());

        Gson gson = new Gson();
        var json = NetworkManager.getInstance().tryReceive();
        System.out.println(json);
        if (json != null) {
            return gson.fromJson(json, new TypeToken<List<Following>>() {
            }.getType());
        }

        return null;
    }
}
