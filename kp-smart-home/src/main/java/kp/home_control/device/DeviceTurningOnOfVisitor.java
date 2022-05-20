package kp.home_control.device;

import kp.device_setting.DeviceSettingType;
import kp.device_setting.repository.DeviceSettingRepository;
import kp.home_control.device.types.RGBBulb;
import kp.home_control.device.types.Speakers;
import kp.home_control.device.types.StandardBulb;
import kp.home_control.dto.DeviceSettingDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeviceTurningOnOfVisitor implements DeviceVisitor{

    private final Boolean turnOn;

    @Override
    public void visit(StandardBulb standardBulb) {
        DeviceSettingDTO deviceSettingDTO = DeviceSettingDTO
                .builder()
                .deviceName(standardBulb.getName())
                .setting(DeviceSettingType.BRIGHTNESS)
                .build();
        if(Boolean.TRUE.equals(turnOn)){
            deviceSettingDTO.setDeviceValue("255");
        }else{
            deviceSettingDTO.setDeviceValue("0");
        }
        new DeviceSettingsVisitor(List.of(deviceSettingDTO)).visit(standardBulb);
    }

    @Override
    public void visit(Speakers speakers) {
    }

    @Override
    public void visit(RGBBulb rgbBulb) {
        DeviceSettingDTO deviceSettingDTO = DeviceSettingDTO
                .builder()
                .deviceName(rgbBulb.getName())
                .setting(DeviceSettingType.BRIGHTNESS)
                .build();
        if(Boolean.TRUE.equals(turnOn)){
            deviceSettingDTO.setDeviceValue("255");
        }else{
            deviceSettingDTO.setDeviceValue("0");
        }
        new DeviceSettingsVisitor(List.of(deviceSettingDTO)).visit(rgbBulb);
    }
}
