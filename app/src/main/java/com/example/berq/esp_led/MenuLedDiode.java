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


/**
 * Created by berq on 15.04.15.
 */
public class MenuLedDiode extends Fragment implements SeekBar.OnSeekBarChangeListener {


    private int progressValue = 0;

    private View rootView;
    private SeekBar bar;
    private TextView textProgress, textAction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.menu_led_diode, container, false);

        bar = (SeekBar) rootView.findViewById(R.id.seekBarGreen); // make seekbar object
        bar.setOnSeekBarChangeListener(this);
        bar = (SeekBar) rootView.findViewById(R.id.seekBarBlue); // make seekbar object
        bar.setOnSeekBarChangeListener(this);
        bar = (SeekBar) rootView.findViewById(R.id.seekBarRed); // make seekbar object
        bar.setOnSeekBarChangeListener(this);
        textProgress = (TextView) rootView.findViewById(R.id.textViewProgress);
        textAction = (TextView) rootView.findViewById(R.id.textViewAction);


        //setup esp variables
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    establish_PWM();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        thread.start();
        return rootView;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        progressValue = progress;
        textProgress.setText("The value is: " + progress);
        textAction.setText("changing");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

        textAction.setText("starting to track touch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        textAction.setText("ended tracking touch");

        int val = (progressValue * 1023) / 100;


        try {
        if (ConnectorESP.isConnectionEstablished()) {
            if (seekBar.getId() == R.id.seekBarGreen)
                ConnectorESP.sendESPCommand("pwm.setduty(G," + val + ")");
            else if (seekBar.getId() == R.id.seekBarBlue)
                ConnectorESP.sendESPCommand("pwm.setduty(B," + val + ")");
            else if (seekBar.getId() == R.id.seekBarRed)
                ConnectorESP.sendESPCommand("pwm.setduty(R," + val + ")");
        } else
            Toast.makeText(getActivity(), "Filed to get server handler", Toast.LENGTH_SHORT).show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
