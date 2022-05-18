package kp.home_control.device.types;

import kp.device_setting.DeviceSettingType;
import kp.home_control.device.Device;
import kp.home_control.device.DeviceVisitor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StandardBulb implements Device {

    String name;

    public StandardBulb(String name){
        this.name = name;
    }

    @Override
    public void accept(DeviceVisitor deviceVisitor) {
        deviceVisitor.visit(this);
    }


    @Override
    public Boolean isOnline() {
        return true;
    }

    public void setBrightness(Integer brightness){
        log.info("Setting brightness: " + brightness);
    }


    @Override
    public List<DeviceSettingType> getCapabilities() {
        return List.of(DeviceSettingType.BRIGHTNESS, DeviceSettingType.NEXT_BRIGHTNESS);
    }

    @Override
    public String getName() {
        return name;
    }
}
