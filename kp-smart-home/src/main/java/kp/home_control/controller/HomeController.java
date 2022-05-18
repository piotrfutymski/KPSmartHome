package kp.home_control.controller;

import kp.device_setting.domain.DeviceSetting;
import kp.home_control.dto.DeviceCapabilities;
import kp.home_control.dto.DeviceInfo;
import kp.home_control.dto.DeviceSettingDTO;
import kp.home_control.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kp-home")
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    private final HomeService homeService;

    @GetMapping
    public List<DeviceInfo> getAllDeviceInformation(){
        return homeService.getAllDeviceInformation();
    }

    @GetMapping("/deviceCapabilities")
    public List<DeviceCapabilities> getDeviceCapabilities(){
        return homeService.getDeviceCapabilities();
    }

    @GetMapping("/{deviceName}")
    public DeviceInfo getDeviceInformation(@PathVariable(name = "deviceName") String deviceName){
        return homeService.getDeviceInformation(deviceName);
    }

    @PostMapping("/{deviceName}")
    public List<DeviceInfo> setDeviceSettings(@RequestBody List<DeviceSettingDTO> settings, @PathVariable(name = "deviceName") String deviceName){
        return homeService.setDeviceSettings(settings, deviceName);
    }

    @PostMapping("/{deviceName}/turn-on")
    public DeviceInfo turnOnDevice(@PathVariable(name = "deviceName") String deviceName){
        return homeService.turnOnDevice(deviceName);
    }

    @PostMapping("/{deviceName}/turn-off")
    public DeviceInfo turnOffDevice(@PathVariable(name = "deviceName") String deviceName){
        return homeService.turnOffDevice(deviceName);
    }

}
