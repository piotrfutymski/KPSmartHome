package kp.home_control.device;

import kp.device_setting.DeviceSettingType;
import kp.device_setting.repository.DeviceSettingRepository;
import kp.home_control.device.types.Speakers;
import kp.home_control.device.types.StandardBulb;
import kp.home_control.dto.DeviceSettingDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeviceStartingOnlineVisitor implements DeviceVisitor{

    private final DeviceSettingRepository deviceSettingRepository;

    @Override
    public void visit(StandardBulb standardBulb) {
        DeviceSettingDTO deviceSettingDTO = DeviceSettingDTO
                .builder()
                .deviceName(standardBulb.getName())
                .setting(DeviceSettingType.BRIGHTNESS)
                .deviceValue("255")
                .build();
        new DeviceSettingsVisitor(List.of(deviceSettingDTO), deviceSettingRepository).visit(standardBulb);
    }

    @Override
    public void visit(Speakers speakers) {
        DeviceSettingDTO deviceSettingDTO = DeviceSettingDTO
                .builder()
                .deviceName(speakers.getName())
                .setting(DeviceSettingType.VOLUME)
                .deviceValue("120")
                .build();
        new DeviceSettingsVisitor(List.of(deviceSettingDTO), deviceSettingRepository).visit(speakers);
    }
}
