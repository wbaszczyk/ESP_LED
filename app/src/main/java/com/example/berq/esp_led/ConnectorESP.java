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
    private InetAddress host;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private Socket socket;

    public ConnectorESP(String ip) throws SocketException, InterruptedException, UnknownHostException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        host = InetAddress.getByName("192.168.150.10");
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

    public void setPort(int portNumber){

        serverPort=portNumber;
    }
    public PrintWriter getServerHandler(){
        return toServer;
    }
    public BufferedReader getServerReader(){
        return fromServer;
    }
}
