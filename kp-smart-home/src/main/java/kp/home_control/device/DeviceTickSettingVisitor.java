package kp.home_control.device;

import kp.device_setting.DeviceSettingType;
import kp.device_setting.domain.DeviceSetting;
import kp.device_setting.repository.DeviceSettingRepository;
import kp.home_control.dto.DeviceSettingDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;

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
