package kp.home_control.service;

import kp.home_control.device.Device;
import kp.home_control.device.types.Speakers;
import kp.home_control.device.types.StandardBulb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DeviceFactory {

    public static final Map<String, Function<String, Device>> DEVICES = Map.of(
        "Główna lampa 1", StandardBulb::new,
        "Główna lampa 2", StandardBulb::new,
        "Główna lampa 3", StandardBulb::new,
        "Główna lampa 4", StandardBulb::new,
        "Głośniki", e->new Speakers()
    );

    public Device createDevice(String name){
        Function<String, Device> function = DEVICES.get(name);
        if(function == null){
            throw new IllegalArgumentException("Devices with name '" + name + "' is not supported");
        }
        return function.apply(name);
    }

    public List<Device> getAllDevices(){
        return DEVICES.keySet()
                .stream()
                .map(this::createDevice)
                .collect(Collectors.toList());
    }

    public Set<String> allDeviceNames(){
        return DEVICES.keySet();
    }

}
