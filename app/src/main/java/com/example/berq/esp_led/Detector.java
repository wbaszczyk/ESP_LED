package com.example.berq.esp_led;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by berq on 15.04.15.
 */




public class Detector extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.detector, container, false);

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


        return rootView;
    }



}
