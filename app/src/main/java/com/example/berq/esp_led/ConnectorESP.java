package com.example.berq.esp_led;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by berq on 29.04.15.
 */
public class ConnectorESP {

    private int serverPort = 2323;
    private InetAddress host = null;
    private static PrintWriter toServer = null;
    private static BufferedReader fromServer = null;
    private static Socket socket = null;
    private String ipAddress = null;

    public ConnectorESP(String ip) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.ipAddress= ip;
    }

    public void establishConnection() throws SocketException, InterruptedException, UnknownHostException, IOException {

        host = InetAddress.getByName(this.ipAddress);
        System.out.println("Connecting to server on port " + serverPort);
        socket = new Socket(host,serverPort);

        System.out.println("Just connected to " +
                socket.getRemoteSocketAddress());

        toServer = new PrintWriter(socket.getOutputStream(), true);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //read welcome from ESP
        String line = fromServer.readLine();
        System.out.println("Client received: " + line + " from Server");
    }

    public static boolean isConnectionEstablished(){

        return  toServer != null;
    }
    public void setPort(int portNumber){

        serverPort=portNumber;
    }

    public static void sendESPCommand(String commandLua) throws InterruptedException {

//        if(isConnectionEstablished()) {
            getServerHandler().println(commandLua);
            Thread.sleep(400);
//        }
    }

    public static PrintWriter getServerHandler(){
        return toServer;
    }
    public static BufferedReader getServerReader(){
        return fromServer;
    }
}
