package kp.home_control.device;

import kp.device_setting.DeviceSettingType;
import kp.device_setting.repository.DeviceSettingRepository;
import kp.home_control.dto.DeviceSettingDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeviceTurningOnOfVisitor implements DeviceVisitor{

    private final Boolean turnOn;

    private final  DeviceSettingRepository deviceSettingRepository;

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
        new DeviceSettingsVisitor(List.of(deviceSettingDTO), deviceSettingRepository).visit(standardBulb);
    }

    @Override
    public void visit(Speakers speakers) {
        DeviceSettingDTO deviceSettingDTO = DeviceSettingDTO
                .builder()
                .deviceName(speakers.getName())
                .setting(DeviceSettingType.VOLUME)
                .build();
        if(Boolean.TRUE.equals(turnOn)){
            deviceSettingDTO.setDeviceValue("120");
        }else{
            deviceSettingDTO.setDeviceValue("0");
        }
        new DeviceSettingsVisitor(List.of(deviceSettingDTO), deviceSettingRepository).visit(speakers);
    }
}
