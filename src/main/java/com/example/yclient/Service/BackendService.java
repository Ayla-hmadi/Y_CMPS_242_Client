package com.example.yclient.Service;

import com.example.yclient.Util.ClientSocket;
import com.example.yclient.Model.LoginCommand;
import com.example.yclient.Model.responses.LoginResponse;
import com.google.gson.Gson;

public class BackendService {
    public static LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public static LoginResponse loginResponse;

    public LoginResponse Login(String username, String password) {
        var command = new LoginCommand(username, password);
        ClientSocket.getInstance().send("login");
        Gson gson = new Gson();
        ClientSocket.getInstance().send(gson.toJson(command));
        var json = ClientSocket.getInstance().tryReceive();
        if (json != null) {
            loginResponse = gson.fromJson(json, LoginResponse.class);
            return loginResponse;
        } else {
            return null;
        }
    }
}
