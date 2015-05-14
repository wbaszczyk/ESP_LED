write_reg(0x50,0x27,0x01) --enable interrupts on channel 1
write_reg(0x50,0x21,0x01) --enable channel 1
write_reg(0x50,0x26,0x01) --force calibration
write_reg(0x50,0x22,0xa9) --repeat rate 350ms
write_reg(0x50,0x22,0xa4) --repeat rate 175ms
write_reg(0x50,0x22,0xa7) --repeat rate 280ms
write_reg(0x50,0x1F,0x0F) --Sensitivity Control Register, values from 0x0F to 0x7F (least sensitive)
write_reg(0x50,0x1F,0x0F)-- Sensor Input 1 Threshold Registers
print(string.byte(read_reg(0x50,0x02))) --read status reg
print(string.byte(read_reg(0x50,0x10))) --read delta counts
print(bit.band(string.byte(read_reg(0x50,0x10)),127)-bit.band(string.byte(read_reg(0x50,0x10)),128)) --read delta counts in U2
