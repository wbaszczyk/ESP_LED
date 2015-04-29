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

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    establish_I2C();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


        return rootView;
    }


    private void establish_I2C() throws InterruptedException {

        if(ConnectorESP.isConnectionEstablished()) {

            //establish I2C vars
            ConnectorESP.sendESPCommand("id=0");
            ConnectorESP.sendESPCommand("sda=6");
            ConnectorESP.sendESPCommand("scl=5");
            //setup I2C
            ConnectorESP.sendESPCommand("i2c.setup(id,sda,scl,i2c.SLOW)");
            //setup I2C read function
            ConnectorESP.sendESPCommand("function read_reg(dev_addr, reg_addr)");
            ConnectorESP.sendESPCommand("i2c.start(id)");
            ConnectorESP.sendESPCommand("i2c.write(id,dev_addr)");
            ConnectorESP.sendESPCommand("i2c.write(id,reg_addr)");
            ConnectorESP.sendESPCommand("i2c.start(id)");
            ConnectorESP.sendESPCommand("i2c.write(id,0x51)");
            ConnectorESP.sendESPCommand("c=i2c.read(id,1)");
            ConnectorESP.sendESPCommand("i2c.stop(id)");
            ConnectorESP.sendESPCommand("return c");
            ConnectorESP.sendESPCommand("end");
            //example using - print(string.byte(read_reg(0x50, 0x00)))

            //setup I2C write function
            ConnectorESP.sendESPCommand("function write_reg(dev_addr, reg_addr, reg_data)");
            ConnectorESP.sendESPCommand("i2c.start(id)");
            ConnectorESP.sendESPCommand("i2c.write(id,dev_addr)");
            ConnectorESP.sendESPCommand("i2c.write(id,reg_addr)");
            ConnectorESP.sendESPCommand("i2c.write(id,reg_data)");
            ConnectorESP.sendESPCommand("i2c.stop(id)");
            ConnectorESP.sendESPCommand("end");
            //example using - write_reg(0x50,0x00,0x00)
        }
    }
}
