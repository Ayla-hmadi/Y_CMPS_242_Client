package com.example.yclient.Util;

import com.example.yclient.Main;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkManager {
    private static String ipAddress;
    private static int portNumber;
    private static MultiThreadClientSocket instance;

    /**
     * Kills the instance
     */
    public static void terminate() throws IOException {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }

    public static synchronized MultiThreadClientSocket getInstance() {
        if (instance == null) {
            instance = new MultiThreadClientSocket(ipAddress, portNumber);
        }
        return instance;
    }

    public static void initialize() {
        var ips = getBroadcastIPs();
        for (InetAddress ip : ips) {
            if (getTcpAddress(ip)) {
                break;
            }
        }
    }

    public static boolean getTcpAddress(InetAddress inetAddress) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(500);

            String message = "Hello Server";
            byte[] sendData = message.getBytes();

            //InetAddress broadcastAddress = InetAddress.getByName(inetAddress.getHostAddress());
            try {
                int serverPort = Main.BROADCAST_PORT;
                var sendPacket = new DatagramPacket(sendData, sendData.length, inetAddress, serverPort);
                socket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                var receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String receiveMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                var list = receiveMessage.split(",");
                ipAddress = list[0];
                portNumber = Integer.parseInt(list[1]);
                socket.close();
            } catch (java.net.SocketTimeoutException e) {
                // Handle timeout (no data received within the specified time)
                System.out.println("Socket timeout: No data received within 5 seconds.");
                return false;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static List<InetAddress> getBroadcastIPs() {
        List<InetAddress> broadcastIPs = new ArrayList<>();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                InetAddress broadcast = getBroadcast(networkInterface);
                if (broadcast != null) {
                    broadcastIPs.add(broadcast);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return broadcastIPs;
    }


    private static InetAddress getBroadcast(NetworkInterface networkInterface) {
        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
        while (addresses.hasMoreElements()) {
            InetAddress address = addresses.nextElement();

            // Check if the address is an IPv4 address
            if (address.getAddress().length == 4) {
                byte[] baseAddress = address.getAddress();
                short prefixLength = networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();

                int mask = 0xffffffff << (32 - prefixLength);
                byte[] subnetMask = new byte[]{
                        (byte) ((mask & 0xff000000) >> 24),
                        (byte) ((mask & 0x00ff0000) >> 16),
                        (byte) ((mask & 0x0000ff00) >> 8),
                        (byte) (mask & 0x000000ff)
                };

                // Calculate the broadcast address
                for (int i = 0; i < baseAddress.length; i++) {
                    baseAddress[i] |= (subnetMask[i] ^ 255);
                }

                try {
                    return InetAddress.getByAddress(baseAddress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

