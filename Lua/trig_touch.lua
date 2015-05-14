a = {}
is4=0
time = 0
div_time = 0
trig_proximity_port=7
gpio.mode(7,gpio.INT,gpio.PULLUP)
write_reg(0x50,0x22,0xa7)
write_reg(0x50,0x27,0x01)
write_reg(0x50,0x21,0x01)
function pin1cb(level)
      div_time=tmr.now()-time
      time = tmr.now()
      if div_time <290000 then
            table.insert(a, div_time)
            is4 = is4+1
      else is4=0 a={} end
      if is4==4 then
            if diode == 0 then pwm.setduty(G," + 1023 + ") diode =1
            else pwm.setduty(G," + 0 + ") diode =0 end
            is4=0
            a={}
      end
      tmr.delay(80000)
      write_reg(0x50,0x00,0x00)
end

-- run
gpio.mode(trig_proximity_port,gpio.INT,gpio.PULLUP)
gpio.trig(trig_proximity_port, "down",pin1cb)
write_reg(0x50,0x00,0x00)