package kp.profile.dto;

import kp.device_setting.domain.DeviceSetting;
import kp.home_control.dto.DeviceSettingDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO {

    private Long id;
    private String name;
    private String creationUser;
    private String lastUpdateUser;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private Set<DeviceSettingDTO> deviceSettings;
}
