package kp.home_control.device;

import kp.device_setting.DeviceSettingType;
import kp.home_control.dto.DeviceCapabilities;

import java.util.List;

public interface Device {

    void accept(DeviceVisitor deviceVisitor);

    Boolean isOnline();

    List<DeviceSettingType> getCapabilities();

    String getName();
}
