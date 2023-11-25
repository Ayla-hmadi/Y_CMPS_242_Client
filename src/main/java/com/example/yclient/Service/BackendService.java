package com.example.yclient.Service;

import com.example.yclient.Util.ClientSocket;
import com.example.yclient.models.LoginCommand;
import com.example.yclient.models.User;
import com.google.gson.Gson;

public class BackendService {

    public User Login(String username, String password) {
        var command = new LoginCommand(username, password);
        ClientSocket.getInstance().send("login");
        Gson gson = new Gson();
        ClientSocket.getInstance().send(gson.toJson(command));
    }
}
