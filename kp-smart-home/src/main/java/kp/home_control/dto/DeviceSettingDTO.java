package kp.home_control.dto;

import kp.device_setting.DeviceSettingType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSettingDTO {

    private String deviceName;
    private DeviceSettingType setting;
    private String deviceValue;
    private LocalDateTime startedAt;
    private String setByUser;
    private String attachedProfile;

    public <T> T getSettingValue(){
        return setting.mapValue(deviceValue);
    }
}
