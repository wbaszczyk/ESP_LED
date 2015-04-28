package com.example.berq.esp_led;

import android.app.Activity;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by berq on 15.04.15.
 */
public class MenuLedDiode extends Fragment implements SeekBar.OnSeekBarChangeListener {

    int serverPort = 2323;
    InetAddress host;
    PrintWriter toServer;
    BufferedReader fromServer;
    Socket socket;

    private int progressValue=0;

    private View rootView;
    private SeekBar bar;
    private TextView textProgress,textAction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.menu_led_diode, container, false);

        bar = (SeekBar)rootView.findViewById(R.id.seekBarGreen); // make seekbar object
        bar.setOnSeekBarChangeListener(this);
        bar = (SeekBar)rootView.findViewById(R.id.seekBarBlue); // make seekbar object
        bar.setOnSeekBarChangeListener(this);
        bar = (SeekBar)rootView.findViewById(R.id.seekBarRed); // make seekbar object
        bar.setOnSeekBarChangeListener(this);
        textProgress = (TextView)rootView.findViewById(R.id.textViewProgress);
        textAction = (TextView)rootView.findViewById(R.id.textViewAction);






        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            host = InetAddress.getByName("192.168.150.10");
            System.out.println("Connecting to server on port " + serverPort);
            socket = new Socket(host,serverPort);

            System.out.println("Just connected to " +
                    socket.getRemoteSocketAddress());

            toServer = new PrintWriter(socket.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = fromServer.readLine();
            System.out.println("Client received: " + line + " from Server");

            toServer.println("R=3");
            toServer.println("G=2");
            toServer.println("B=1");
            toServer.println("pwm.setup(R, 100, 1)");
            toServer.println("pwm.setup(G, 100, 1)");
            toServer.println("pwm.setup(B, 100, 1)");


// myAwesomeTextView.setText("Client received: " + line + " from Server");




        } catch(UnknownHostException ex) { ex.printStackTrace(); }
        catch(IOException e){ e.printStackTrace(); }




    return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        progressValue=progress;
// change progress text label with current seekbar value
        textProgress.setText("The value is: "+progress);
// change action text label to changing
        textAction.setText("changing");

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

        textAction.setText("starting to track touch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        textAction.setText("ended tracking touch");

        int val =(progressValue*1023)/100;
        if(seekBar.getId()==R.id.seekBarGreen)
            toServer.println("pwm.setduty(G,"+val+")");
        else if(seekBar.getId()==R.id.seekBarBlue)
            toServer.println("pwm.setduty(B,"+val+")");
        else if(seekBar.getId()==R.id.seekBarRed)
            toServer.println("pwm.setduty(R,"+val+")");

    }
}
