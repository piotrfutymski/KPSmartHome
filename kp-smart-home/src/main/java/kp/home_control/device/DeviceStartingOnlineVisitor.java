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
public class DeviceStartingOnlineVisitor implements DeviceVisitor{

    @Override
    public void visit(StandardBulb standardBulb) {
        DeviceSettingDTO deviceSettingDTO = DeviceSettingDTO
                .builder()
                .deviceName(standardBulb.getName())
                .setting(DeviceSettingType.BRIGHTNESS)
                .deviceValue("255")
                .build();
        new DeviceSettingsVisitor(List.of(deviceSettingDTO)).visit(standardBulb);
    }

    @Override
    public void visit(Speakers speakers) {
        DeviceSettingDTO deviceSettingDTO = DeviceSettingDTO
                .builder()
                .deviceName(speakers.getName())
                .setting(DeviceSettingType.VOLUME)
                .deviceValue("50")
                .build();
        new DeviceSettingsVisitor(List.of(deviceSettingDTO)).visit(speakers);
    }

    @Override
    public void visit(RGBBulb rgbBulb) {
        DeviceSettingDTO deviceSettingDTO = DeviceSettingDTO
                .builder()
                .deviceName(rgbBulb.getName())
                .setting(DeviceSettingType.BRIGHTNESS)
                .deviceValue("255")
                .build();
        new DeviceSettingsVisitor(List.of(deviceSettingDTO)).visit(rgbBulb);
    }
}
