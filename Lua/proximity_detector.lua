function hsvToRgb (h, s, v, a)
      local r, g, b
      local i = math.floor(h * 6);
      local f = h * 6 - i;
      local p = v * (1 - s);
      local q = v * (1 - f * s);
      local t = v * (1 - (1 - f) * s);
      i = i % 6
      if i == 0 then r, g, b = v, t, p
      elseif i==1 then r, g, b = q, v, p
      elseif i==2 then r, g, b = p, v, t
      elseif i==3 then r, g, b = p, q, v
      elseif i==4 then r, g, b = t, p, v
      elseif i==5 then r, g, b = v, p, q
      end

      return r * 1023 ,g * 1023, b * 1023, a * 1023 end
tmr_touch_id=0
function touch_pwm()
      reg_val =string.byte(read_reg(0x50,0x10))
      touch_val = bit.band(reg_val,127)-bit.band(reg_val,128)
      if touch_val>4 then r,g,b=hsvToRgb(touch_val/127,1,1,1)
      else b=0
            r=0
            g=0 
      end
      pwm.setduty(G,g)
      pwm.setduty(R,r)
      pwm.setduty(B,b) 
end

-- run
gpio.mode(tmr_touch_id, gpio.INPUT)
tmr.alarm(tmr_touch_id, 50, 1, touch_pwm)