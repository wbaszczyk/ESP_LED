id=0
sda=6
scl=5
i2c.setup(id,sda,scl,i2c.SLOW)
function read_reg(dev_addr, reg_addr)
      i2c.start(id)
      i2c.write(id,dev_addr)
      i2c.write(id,reg_addr)
      i2c.start(id)
      i2c.write(id,0x51)
      c=i2c.read(id,1)
      i2c.stop(id)
      return c
end

