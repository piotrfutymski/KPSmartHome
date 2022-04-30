package kp.home_control.dto;

import kp.device_setting.DeviceSettingType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCapabilities {

    String deviceName;
    List<DeviceSettingType> capabilities;
}
