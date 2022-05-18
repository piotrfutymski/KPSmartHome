package kp.home_control.device;

import kp.device_setting.DeviceSettingType;
import kp.device_setting.domain.DeviceSetting;
import kp.device_setting.repository.DeviceSettingRepository;
import kp.home_control.device.types.Speakers;
import kp.home_control.device.types.StandardBulb;
import kp.home_control.dto.DeviceSettingDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DeviceSettingsVisitor implements DeviceVisitor {

    private final List<DeviceSettingDTO> settingList;

    private final DeviceSettingRepository deviceSettingRepository;

    public void clearSettings(Device device){
        deviceSettingRepository
                .findByDeviceNameAndStartedAtIsNotNull(device.getName())
                .forEach(deviceSettingRepository::delete);
    }

    public List<DeviceSettingDTO> getFilteredSettingsForDevice(Device device){
        return settingList
                .stream()
                .filter(e->e.getDeviceName().equals(device.getName()))
                .filter(e->device.getCapabilities().contains(e.getSetting()))
                .collect(Collectors.toList());
    }


    @Override
    public void visit(StandardBulb standardBulb) {
        clearSettings(standardBulb);
        getFilteredSettingsForDevice(standardBulb)
                .stream()
                .forEach(e->{
                    if(e.getSetting().equals(DeviceSettingType.BRIGHTNESS)){
                        standardBulb.setBrightness(e.getSettingValue());
                    }
                    deviceSettingRepository.save(new DeviceSetting(e, true, null));
                });

    }

    @Override
    public void visit(Speakers speakers) {
        clearSettings(speakers);
        getFilteredSettingsForDevice(speakers)
                .stream()
                .forEach(e->{
                    if(e.getSetting().equals(DeviceSettingType.VOLUME)){
                        speakers.setVolume(e.getSettingValue());
                    }
                    deviceSettingRepository.save(new DeviceSetting(e, true, null));
                });
    }
}
