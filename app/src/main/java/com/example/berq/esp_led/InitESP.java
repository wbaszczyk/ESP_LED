package com.example.berq.esp_led;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by berq on 15.04.15.
 */
public class InitESP extends Fragment implements Button.OnClickListener {


    private int progressValue = 0;
    ConnectorESP espConnection;
    private View rootView;
    private TextView ipAdd;
    private Button connetcbtn;
    private List<CheckBox> chkPlate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.init, container, false);

        ipAdd = (TextView) rootView.findViewById(R.id.ipAdd); // make seekbar object

        connetcbtn = (Button) rootView.findViewById(R.id.connectbtn);

        chkPlate = new ArrayList<>();
        chkPlate.add((CheckBox) rootView.findViewById(R.id.chkPlate1));
        chkPlate.add((CheckBox) rootView.findViewById(R.id.chkPlate2));
        chkPlate.add((CheckBox) rootView.findViewById(R.id.chkPlate3));
        connetcbtn.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {

        List<String> ips = new ArrayList<>();
        for(CheckBox checkBox : chkPlate){
            if(checkBox.isChecked()){
                ips.add(checkBox.getText().toString());
            }
        }
        espConnection = new ConnectorESP(ips);
//        espConnection=new ConnectorESP(ipAdd.getText().toString());
        //setup ESP connection
        try{
//            for (ConnectorESP connectorESP : espConnections){
//                connectorESP.establishConnection();
//            }
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
        Toast.makeText(getActivity(), "done.", Toast.LENGTH_SHORT).show();
//        long startTime = System.currentTimeMillis();
//        while(!espConnection.isConnectionEstablished())
//        {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if(System.currentTimeMillis() - startTime>3000) {
//                Toast.makeText(getActivity(), "Cant connect", Toast.LENGTH_SHORT).show();
//                break;
//            }
//        }
//
//        if (ConnectorESP.isConnectionEstablished()) {
//            try {
//                establish_PWM();
//                establish_I2C();
//                establish_hsv_function();
//                Toast.makeText(getActivity(), "done.", Toast.LENGTH_SHORT).show();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


    }
    private void establish_PWM() throws InterruptedException {

//        if (ConnectorESP.isConnectionEstablished()) {
            ConnectorESP.sendESPCommand("R=1");
            ConnectorESP.sendESPCommand("G=8");
            ConnectorESP.sendESPCommand("B=2");
            ConnectorESP.sendESPCommand("pwm.setup(R, 100, 1)");
            ConnectorESP.sendESPCommand("pwm.setup(G, 100, 1)");
            ConnectorESP.sendESPCommand("pwm.setup(B, 100, 1)");
//        }
        //else Toast.makeText(getActivity(), "Filed to get server handler", Toast.LENGTH_SHORT).show();
    }

    private void establish_I2C() throws InterruptedException {

//        if(ConnectorESP.isConnectionEstablished()) {

            //establish I2C vars
            ConnectorESP.sendESPCommand("id=0");
            ConnectorESP.sendESPCommand("sda=6");
            ConnectorESP.sendESPCommand("scl=7");
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



            //setup I2C iterupt function


            ConnectorESP.sendESPCommand("a = {}");
            ConnectorESP.sendESPCommand("is4=0");
            ConnectorESP.sendESPCommand("time = 0");
            ConnectorESP.sendESPCommand("div_time = 0");
            ConnectorESP.sendESPCommand("gpio.mode(5,gpio.INT,gpio.PULLUP)");
        ConnectorESP.sendESPCommand("write_reg(0x50,0x22,0xa7)");
        ConnectorESP.sendESPCommand("write_reg(0x50,0x27,0x01)");
        ConnectorESP.sendESPCommand("write_reg(0x50,0x21,0x01)");

            ConnectorESP.sendESPCommand("function pin1cb(level)");
            ConnectorESP.sendESPCommand("div_time=tmr.now()-time");
//            ConnectorESP.sendESPCommand("print(div_time)");
            ConnectorESP.sendESPCommand("time = tmr.now()");


            ConnectorESP.sendESPCommand("if div_time <290000 then");
            ConnectorESP.sendESPCommand("table.insert(a, div_time)");
            ConnectorESP.sendESPCommand("is4 = is4+1");
            ConnectorESP.sendESPCommand("else is4=0 a={} end");
            ConnectorESP.sendESPCommand("if is4==4 then");

            ConnectorESP.sendESPCommand("if diode == 0 then pwm.setduty(G," + 1023 + ") diode =1");
            ConnectorESP.sendESPCommand("else pwm.setduty(G," + 0 + ") diode =0 end");

//            ConnectorESP.sendESPCommand("for k,v in pairs(a) do print(k,v) end ");
            ConnectorESP.sendESPCommand("is4=0");
            ConnectorESP.sendESPCommand("a={}");
            ConnectorESP.sendESPCommand("end");
//
//
            ConnectorESP.sendESPCommand("tmr.delay(80000)");
            ConnectorESP.sendESPCommand("write_reg(0x50,0x00,0x00)");
            ConnectorESP.sendESPCommand("end");






//            ConnectorESP.sendESPCommand("tmr.alarm(0, 5, 1, function()");
//            ConnectorESP.sendESPCommand("if gpio.read(7)==0 then");
//            ConnectorESP.sendESPCommand("write_reg(0x50,0x00,0x00)");
//            ConnectorESP.sendESPCommand("end");
//            ConnectorESP.sendESPCommand("end)");


//            ConnectorESP.sendESPCommand("tmr.alarm(1, 5, 1, function()");
//            ConnectorESP.sendESPCommand("val=string.byte(read_reg(0x50, 0x00))");
//            ConnectorESP.sendESPCommand("if val > 0 then");
//            ConnectorESP.sendESPCommand("print(val) end");
//            ConnectorESP.sendESPCommand("end)");

//            Toast.makeText(getActivity(), "done.", Toast.LENGTH_SHORT).show();
//        }
        //else Toast.makeText(getActivity(), "Filed to get server handler", Toast.LENGTH_SHORT).show();
    }

    private void establish_hsv_function() throws InterruptedException {


        ConnectorESP.sendESPCommand("function hsvToRgb (h, s, v, a)");
        ConnectorESP.sendESPCommand("local r, g, b");
        ConnectorESP.sendESPCommand("local i = math.floor(h * 6);");
        ConnectorESP.sendESPCommand("local f = h * 6 - i;");
        ConnectorESP.sendESPCommand("local p = v * (1 - s);");
        ConnectorESP.sendESPCommand("local q = v * (1 - f * s);");
        ConnectorESP.sendESPCommand("local t = v * (1 - (1 - f) * s);");
        ConnectorESP.sendESPCommand("i = i % 6");
        ConnectorESP.sendESPCommand("if i == 0 then r, g, b = v, t, p");
        ConnectorESP.sendESPCommand("elseif i==1 then r, g, b = q, v, p");
        ConnectorESP.sendESPCommand("elseif i==2 then r, g, b = p, v, t");
        ConnectorESP.sendESPCommand("elseif i==3 then r, g, b = p, q, v");
        ConnectorESP.sendESPCommand("elseif i==4 then r, g, b = t, p, v");
        ConnectorESP.sendESPCommand("elseif i==5 then r, g, b = v, p, q");
        ConnectorESP.sendESPCommand("end");
        ConnectorESP.sendESPCommand("return r * 1023 ,g * 1023, b * 1023, a * 1023 end");

        ConnectorESP.sendESPCommand("tmr_touch_id=0");
        ConnectorESP.sendESPCommand("trig_proximity_port=5");

        ConnectorESP.sendESPCommand("function touch_pwm()");
        ConnectorESP.sendESPCommand("reg_val =string.byte(read_reg(0x50,0x10))");
        ConnectorESP.sendESPCommand("touch_val = bit.band(reg_val,127)-bit.band(reg_val,128)");
        ConnectorESP.sendESPCommand("if touch_val>4 then r,g,b=hsvToRgb(touch_val/127,1,1,1)");
        ConnectorESP.sendESPCommand("else b=0");
        ConnectorESP.sendESPCommand("r=0");
        ConnectorESP.sendESPCommand("g=0 end");
        ConnectorESP.sendESPCommand("pwm.setduty(G,g)");
        ConnectorESP.sendESPCommand("pwm.setduty(R,r)");
        ConnectorESP.sendESPCommand("pwm.setduty(B,b) end");






    }

}
