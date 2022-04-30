package kp.home_control.dto;

import kp.device_setting.domain.DeviceSetting;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfo {

    String deviceName;

    List<DeviceSettingDTO> deviceSettings;

    Boolean isOnline;
}
