package com.example.berq.esp_led;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by berq on 15.04.15.
 */
public class MenuLedDiode extends Fragment implements SeekBar.OnSeekBarChangeListener {

    ConnectorESP espConnection;

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


        espConnection=new ConnectorESP("192.168.150.10");

        //setup ESP connection
        try{
            espConnection.establishConnection();

        } catch(UnknownHostException ex) {
            Toast.makeText(getActivity(), "Filed - UnknownHostException", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
        catch (InterruptedException e) {
            Toast.makeText(getActivity(), "Filed - InterruptedException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        catch(IOException e) {
            Toast.makeText(getActivity(), "Filed - IOException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        //setup esp variables
        if(espConnection.isConnectionEstablished()) {
            espConnection.getServerHandler().println("R=3");
            espConnection.getServerHandler().println("G=2");
            espConnection.getServerHandler().println("B=1");
            espConnection.getServerHandler().println("pwm.setup(R, 100, 1)");
            espConnection.getServerHandler().println("pwm.setup(G, 100, 1)");
            espConnection.getServerHandler().println("pwm.setup(B, 100, 1)");
        }
        else
            Toast.makeText(getActivity(), "Filed to get server handler", Toast.LENGTH_SHORT).show();
        return rootView;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        progressValue=progress;
        textProgress.setText("The value is: "+progress);
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
        if(espConnection.isConnectionEstablished()) {
            if (seekBar.getId() == R.id.seekBarGreen)
                espConnection.getServerHandler().println("pwm.setduty(G," + val + ")");
            else if (seekBar.getId() == R.id.seekBarBlue)
                espConnection.getServerHandler().println("pwm.setduty(B," + val + ")");
            else if (seekBar.getId() == R.id.seekBarRed)
                espConnection.getServerHandler().println("pwm.setduty(R," + val + ")");
        }
        else
            Toast.makeText(getActivity(), "Filed to get server handler", Toast.LENGTH_SHORT).show();
    }
}
