package com.example.berq.esp_led;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by berq on 15.04.15.
 */




public class Detector extends Fragment  implements SeekBar.OnSeekBarChangeListener{

    View rootView;

    private int progressValue = 0;
    private RadioGroup radioSelection;
    private RadioButton radioSelectionButton;
    private Button btnDisplay;
    private Button btnCalibration;
    private SeekBar bar;

    public Detector() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.detector, container, false);
        bar = (SeekBar) rootView.findViewById(R.id.seekBarSensitivity); // make seekbar object
        bar.setOnSeekBarChangeListener(this);

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    establish_I2C();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        thread.start();

        radioSelection = (RadioGroup) rootView.findViewById(R.id.radios);
        btnDisplay = (Button) rootView.findViewById(R.id.btnDisplay);
        btnCalibration = (Button) rootView.findViewById(R.id.buttonCalibration);

        btnCalibration.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {

                  try {
                      ConnectorESP.sendESPCommand("write_reg(0x50,0x26,0x01)");
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }

              }
          });
        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioSelection.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSelectionButton = (RadioButton) rootView.findViewById(selectedId);


                if(radioSelectionButton.getId() == R.id.radioProximity){

                    try {
                        ConnectorESP.sendESPCommand("gpio.mode(trig_proximity_id, gpio.INPUT)");
                        ConnectorESP.sendESPCommand("tmr.alarm(trig_proximity_id, 50, 1, touch_pwm)");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(radioSelectionButton.getId() == R.id.radioTouch){

                    try {

                        ConnectorESP.sendESPCommand("tmr.stop(trig_proximity_id)");
                        ConnectorESP.sendESPCommand("gpio.mode(tmr_touch_port,gpio.INT,gpio.PULLUP)");
                        ConnectorESP.sendESPCommand("gpio.trig(tmr_touch_port, \"down\",pin1cb)");
                        ConnectorESP.sendESPCommand("write_reg(0x50,0x00,0x00)");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(radioSelectionButton.getId() == R.id.radioOff){

                    try {
                        ConnectorESP.sendESPCommand("tmr.stop(trig_proximity_id)");
                        ConnectorESP.sendESPCommand("gpio.mode(trig_proximity_id, gpio.INPUT)");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getActivity(),
                        radioSelectionButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });


        return rootView;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        progressValue = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {


        try {
            ConnectorESP.sendESPCommand("write_reg(0x50,0x1F,0x"+ Integer.toString(progressValue) +"F)");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Toast.makeText(getActivity(),
//               Integer.toString(progressValue), Toast.LENGTH_SHORT).show();
    }
}
