id=0
sda=6
scl=5
i2c.setup(id,sda,scl,i2c.SLOW)

function write_reg(dev_addr, reg_addr, reg_data)
      i2c.start(id)
      i2c.write(id,dev_addr)
      i2c.write(id,reg_addr)
      i2c.write(id,reg_data)
      i2c.stop(id)
end
