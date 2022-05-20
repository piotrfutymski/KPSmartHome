package kp.home_control.dto;

import kp.device_setting.DeviceSettingType;
import kp.profile.domain.Profile;
import kp.profile.dto.ProfileDTO;
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
    private Boolean started;
    private String attachedProfile;

    public <T> T getSettingValue(){
        return setting.mapValue(deviceValue);
    }
}
