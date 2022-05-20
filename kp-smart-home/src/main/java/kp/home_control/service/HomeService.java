package kp.home_control.service;

import kp.device_setting.domain.DeviceSetting;
import kp.device_setting.repository.DeviceSettingRepository;
import kp.device_state.domain.DeviceState;
import kp.device_state.repository.DeviceStateRepository;
import kp.home_control.device.*;
import kp.home_control.dto.DeviceCapabilities;
import kp.home_control.dto.DeviceInfo;
import kp.home_control.dto.DeviceSettingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class HomeService {

    @Autowired
    private final DeviceFactory deviceFactory;

    @Autowired
    private final DeviceStateRepository deviceStateRepository;

    @Autowired
    private final DeviceSettingRepository deviceSettingRepository;

    public void getTickSettingsAndClear(Device device){
        deviceSettingRepository
                .findByDeviceNameAndStarted(device.getName(), true)
                .forEach(deviceSettingRepository::delete);
    }

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
        deviceFactory.createDevice(deviceName).accept(new DeviceTurningOnOfVisitor(true));
        return getDeviceInformation(deviceName);
    }

    public DeviceInfo turnOffDevice(String deviceName) {
        deviceFactory.createDevice(deviceName).accept(new DeviceTurningOnOfVisitor(false));
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
                )).forEach((k,v)->{
                    Device device = deviceFactory.createDevice(k);
                    getTickSettingsAndClear(device);
                    deviceSettingRepository.saveAll(v.stream().map(e->new DeviceSetting(e, true, null)).collect(Collectors.toList()));
                    device.accept(new DeviceSettingsVisitor(v));
                });
        checkDeviceStates();
        return getAllDeviceInformation();
    }

    public boolean isOnline(String deviceName){
        return deviceStateRepository.findByDeviceName(deviceName).map(DeviceState::getIsOnline).orElse(false);
    }

    @Scheduled(fixedRate = 500)
    public void checkDeviceStates() {
        deviceFactory.getAllDevices()
                .stream()
                .collect(Collectors.toMap(
                        e -> e,
                        e -> Pair.of(e.isOnline(), isOnline(e.getName()))
                )).entrySet()
                .stream()
                .filter(e -> !Objects.equals(e.getValue().getFirst(), e.getValue().getSecond()))
                .forEach(e -> {
                    DeviceState state = deviceStateRepository.findByDeviceName(e.getKey().getName()).orElse(
                            DeviceState.builder().deviceName(e.getKey().getName()).build()
                    );
                    state.setIsOnline(e.getValue().getFirst());
                    if (Boolean.TRUE.equals(e.getValue().getFirst())) {
                        log.info("Device " + e.getKey().getName() + " is going online");
                        e.getKey().accept(new DeviceStartingOnlineVisitor());
                    } else {
                        log.info("Device " + e.getKey().getName() + " is going offline");
                    }
                    deviceStateRepository.save(state);
                });

        Collection<DeviceSetting> deviceSettings = deviceSettingRepository.findByStarted(true);
        deviceFactory.getAllDevices()
                .forEach(device -> {
                    DeviceCheckStateVisitor visitor = new DeviceCheckStateVisitor(
                            deviceSettings.stream().filter(e -> e.getDeviceName().equals(device.getName())).collect(Collectors.toList())
                    );
                    device.accept(visitor);
                    deviceSettingRepository.saveAll(visitor.getActualSettings());
                });
    }

}
