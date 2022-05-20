package kp.home_control.service;

import kp.home_control.device.Device;
import kp.home_control.device.types.RGBBulb;
import kp.home_control.device.types.Speakers;
import kp.home_control.device.types.StandardBulb;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceFactory {

    TelnetClient lampaCzytelniczaClient;

    public DeviceFactory(){
        lampaCzytelniczaClient = new TelnetClient();
        try {
            lampaCzytelniczaClient.connect("192.168.1.50", 55443);
        } catch (IOException ignored) {
        }
    }

    public final Map<String, Function<String, Device>> DEVICES = Map.of(
        "Światło salon 1", StandardBulb::new,
        "Światło salon 2", StandardBulb::new,
        "Światło salon 3", StandardBulb::new,
        "Światło salon 4", StandardBulb::new,
        "Głośniki", Speakers::new,
        "Lampa czytelnicza", (String name)->{
            RGBBulb device = new RGBBulb(name);
            device.setTelnetClient(lampaCzytelniczaClient);
            return device;
        }
    );

    public Device createDevice(String name){
        Function<String, Device> function = DEVICES.get(name);
        if(function == null){
            String msg = "Devices with name '" + name + "' is not supported";
            log.warn(msg);
            throw new IllegalArgumentException(msg);
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
