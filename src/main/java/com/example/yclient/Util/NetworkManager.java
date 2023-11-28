package com.example.yclient.Util;

import java.io.IOException;

public class NetworkManager {
    private static MultiThreadClientSocket instance;

    /**
     * Kills the instance
     */
    public static void terminate() throws IOException {
        if (instance != null) {
            instance.close();
        }
    }

    public static synchronized MultiThreadClientSocket getInstance() {
        if (instance == null) {
            instance = new MultiThreadClientSocket();
        }
        return instance;
    }
}

