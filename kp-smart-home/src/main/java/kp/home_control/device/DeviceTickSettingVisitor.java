package kp.home_control.device;

import kp.device_setting.repository.DeviceSettingRepository;
import kp.home_control.device.types.Speakers;
import kp.home_control.device.types.StandardBulb;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class DeviceTickSettingVisitor implements DeviceVisitor{

    @Autowired
    private DeviceSettingRepository deviceSettingRepository;

    @Override
    public void visit(StandardBulb standardBulb) {
        //TODO implement
    }

    @Override
    public void visit(Speakers speakers) {

    }
}
