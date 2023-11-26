package com.example.yclient.Service;

import com.example.yclient.Model.RegisterCommand;
import com.example.yclient.Util.ClientSocket;
import com.example.yclient.Model.LoginCommand;
import com.example.yclient.Model.responses.LoginResponse;
import com.example.yclient.Util.NetworkManager;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;

public class BackendService {
    public static LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public static LoginResponse loginResponse;

    public LoginResponse Login(String username, String password) {
        var hashedPasswrd = BCrypt.hashpw(password, BCrypt.gensalt());

        var command = new LoginCommand(username, hashedPasswrd);
        NetworkManager.getInstance().send("login");
        Gson gson = new Gson();
        NetworkManager.getInstance().send(gson.toJson(command));
        var json = NetworkManager.getInstance().tryReceive();
        System.out.println(json);
        if (json != null) {
            loginResponse = gson.fromJson(json, LoginResponse.class);
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
}
