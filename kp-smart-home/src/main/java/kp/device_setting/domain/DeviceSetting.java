package kp.device_setting.domain;

import kp.config.DateTimeUtils;
import kp.device_setting.DeviceSettingType;
import kp.home_control.dto.DeviceSettingDTO;
import kp.profile.domain.Profile;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name="DeviceSetting")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceSetting {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String deviceName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DeviceSettingType setting;

    private String deviceValue;
    private Boolean started;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profile attachedProfile;

    public DeviceSetting(DeviceSettingDTO deviceSettingDTO, boolean isCurrent, Profile profile){
        this.deviceName = deviceSettingDTO.getDeviceName();
        this.setting = deviceSettingDTO.getSetting();
        this.deviceValue = deviceSettingDTO.getDeviceValue();
        this.started = isCurrent;
        this.attachedProfile = profile;
    }

    public DeviceSettingDTO toDto(){
        return DeviceSettingDTO
                .builder()
                .deviceName(deviceName)
                .setting(setting)
                .deviceValue(deviceValue)
                .started(started)
                .attachedProfile(Optional.ofNullable(attachedProfile).map(Profile::getName).orElse(null))
                .build();
    }

    public <T> T getSettingValue(){
        return setting.mapValue(deviceValue);
    }

}
