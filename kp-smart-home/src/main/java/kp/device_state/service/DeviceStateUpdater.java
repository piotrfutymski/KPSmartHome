package kp.device_state.service;

import kp.device_setting.DeviceSettingType;
import kp.device_setting.repository.DeviceSettingRepository;
import kp.device_state.domain.DeviceState;
import kp.device_state.repository.DeviceStateRepository;
import kp.home_control.device.Device;
import kp.home_control.device.DeviceStartingOnlineVisitor;
import kp.home_control.device.DeviceTickSettingVisitor;
import kp.home_control.service.DeviceFactory;
import kp.home_control.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class DeviceStateUpdater {

    @Autowired
    private final DeviceStateRepository deviceStateRepository;

    @Autowired
    private final DeviceSettingRepository deviceSettingRepository;

    @Autowired
    private final HomeService homeService;

    @Autowired
    private final DeviceFactory deviceFactory;

    @Scheduled(fixedRate = 100)
    public void reportCurrentTime() {
        deviceFactory.getAllDevices()
                .stream()
                .collect(Collectors.toMap(
                        e->e,
                        e-> Pair.of(e.isOnline(), homeService.isOnline(e.getName()))
                )).entrySet()
                .stream()
                .peek(e-> e.getKey().accept(new DeviceTickSettingVisitor()))
                .filter(e->!Objects.equals(e.getValue().getFirst(), e.getValue().getSecond()))
                .forEach(e-> {
                    DeviceState state = deviceStateRepository.findByDeviceName(e.getKey().getName()).orElse(
                            DeviceState.builder().deviceName(e.getKey().getName()).build()
                    );
                    state.setIsOnline(e.getValue().getFirst());
                    if(Boolean.TRUE.equals(e.getValue().getFirst())){
                        log.info("Device " + e.getKey().getName() + " is going online");
                        e.getKey().accept(new DeviceStartingOnlineVisitor(deviceSettingRepository));
                    }else{
                        log.info("Device " + e.getKey().getName() + " is going offline");
                    }
                    deviceStateRepository.save(state);
                });
    }
}
