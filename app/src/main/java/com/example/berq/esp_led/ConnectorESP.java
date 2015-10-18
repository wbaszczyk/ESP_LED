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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by berq on 29.04.15.
 */
public class ConnectorESP {

    private int serverPort = 2323;
    private List<InetAddress> host = null;
    private static List<PrintWriter> toServer = null;
    private static List<BufferedReader> fromServer = null;
    private static List<Socket> socket = null;
    private List<String> ipAddresses = null;

    public ConnectorESP(List<String> ips) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.ipAddresses= ips;
    }

    public void establishConnection() throws SocketException, InterruptedException, UnknownHostException, IOException {

        host = new ArrayList<>(ipAddresses.size());
        toServer = new ArrayList<>(ipAddresses.size());
        fromServer = new ArrayList<>(ipAddresses.size());
        socket = new ArrayList<>(ipAddresses.size());
        Integer i = 0;
        for(String ip : ipAddresses) {
            host.add(InetAddress.getByName(ip));
            System.out.println("Connecting to server on port " + serverPort);
            socket.add(new Socket(host.get(i), serverPort));

            System.out.println("Just connected to " +
                    socket.get(i).getRemoteSocketAddress());

            toServer.add(new PrintWriter(socket.get(i).getOutputStream(), true));
            fromServer.add(new BufferedReader(new InputStreamReader(socket.get(i).getInputStream())));

            //read welcome from ESP
            String line = fromServer.get(i).readLine();
            System.out.println("Client received: " + line + " from Server");
            i++;
        }
    }

    public static boolean isConnectionEstablished(){
        for (PrintWriter printWriter : toServer){
            if(printWriter == null)
                return false;
        }
        return true;
    }
    public void setPort(int portNumber){

        serverPort=portNumber;
    }

    public static void sendESPCommand(String commandLua) throws InterruptedException {

//        if(isConnectionEstablished()) {
        for(PrintWriter printWriter : getServerHandler()){
            printWriter.println(commandLua);
        }
        Thread.sleep(400);
//            getServerHandler().println(commandLua);
//            Thread.sleep(400);
//        }
    }

    public static List<PrintWriter> getServerHandler(){
        return toServer;
    }
    public static List<BufferedReader> getServerReader(){
        return fromServer;
    }
}
