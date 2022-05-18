package kp.home_control.service;

import kp.device_setting.domain.DeviceSetting;
import kp.device_setting.repository.DeviceSettingRepository;
import kp.device_state.domain.DeviceState;
import kp.device_state.repository.DeviceStateRepository;
import kp.home_control.device.DeviceSettingsVisitor;
import kp.home_control.device.DeviceTurningOnOfVisitor;
import kp.home_control.dto.DeviceCapabilities;
import kp.home_control.dto.DeviceInfo;
import kp.home_control.dto.DeviceSettingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    @Autowired
    private final DeviceFactory deviceFactory;

    @Autowired
    private final DeviceStateRepository deviceStateRepository;

    @Autowired
    private final DeviceSettingRepository deviceSettingRepository;

    public List<DeviceInfo> getAllDeviceInformation() {
        return deviceFactory.allDeviceNames()
                .stream()
                .map(this::getDeviceInformation)
                .collect(Collectors.toList());
    }

    public DeviceInfo getDeviceInformation(String deviceName) {
        Boolean isOnline = isOnline(deviceName);
        List<DeviceSettingDTO> settings = deviceSettingRepository.findByDeviceName(deviceName).stream().map(DeviceSetting::toDto).collect(Collectors.toList());
        return DeviceInfo
                .builder()
                .deviceName(deviceName)
                .deviceSettings(settings)
                .isOnline(isOnline)
                .build();
    }

    public DeviceInfo turnOnDevice(String deviceName) {
        deviceFactory.createDevice(deviceName).accept(new DeviceTurningOnOfVisitor(true, deviceSettingRepository));
        return getDeviceInformation(deviceName);
    }

    public DeviceInfo turnOffDevice(String deviceName) {
        deviceFactory.createDevice(deviceName).accept(new DeviceTurningOnOfVisitor(false, deviceSettingRepository));
        return getDeviceInformation(deviceName);
    }

    public List<DeviceCapabilities> getDeviceCapabilities() {
        return deviceFactory.getAllDevices()
                .stream()
                .map(e->DeviceCapabilities
                        .builder()
                        .capabilities(e.getCapabilities())
                        .deviceName(e.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DeviceInfo> setDeviceSettings(List<DeviceSettingDTO> settings, String deviceName) {
        settings
                .stream()
                .peek(e->{
                    if(e.getDeviceName() == null){
                        e.setDeviceName(deviceName);
                    }
                }).collect(Collectors.groupingBy(
                        DeviceSettingDTO::getDeviceName
                )).forEach((k,v)->deviceFactory.createDevice(k).accept(new DeviceSettingsVisitor(v, deviceSettingRepository)));
        return getAllDeviceInformation();
    }

    public boolean isOnline(String deviceName){
        return deviceStateRepository.findByDeviceName(deviceName).map(DeviceState::getIsOnline).orElse(false);
    }
}
