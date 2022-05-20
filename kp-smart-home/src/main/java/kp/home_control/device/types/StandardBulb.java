package kp.home_control.device.types;

import kp.device_setting.DeviceSettingType;
import kp.home_control.device.AbstractDevice;
import kp.home_control.device.Device;
import kp.home_control.device.DeviceVisitor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StandardBulb extends AbstractDevice {

    private Integer brightness = 0;

    public StandardBulb(String name){
        super(name);
    }

    @Override
    public void accept(DeviceVisitor deviceVisitor) {
        deviceVisitor.visit(this);
    }


    @Override
    public Boolean isOnline() {
        return true;
    }

    @Override
    public List<DeviceSettingType> getCapabilities() {
        return List.of(DeviceSettingType.BRIGHTNESS, DeviceSettingType.NEXT_BRIGHTNESS);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setBrightness(Integer brightness){
        log.info("On device {}, setting brightness: {}", getName(), brightness);
        this.brightness = brightness;
    }

    public Integer getBrightness(){
        return brightness;
    }
}
