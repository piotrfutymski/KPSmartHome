package kp.profile.domain;

import kp.config.DateTimeUtils;
import kp.device_setting.domain.DeviceSetting;
import kp.home_control.dto.DeviceSettingDTO;
import kp.profile.dto.ProfileDTO;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="Profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String creationUser;

    private String lastUpdateUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;


    @OneToMany(
            mappedBy="attachedProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DeviceSetting> deviceSettings;

    public void fillFromDTO(ProfileDTO profileDTO){
        this.name = profileDTO.getName();
        this.deviceSettings = profileDTO.getDeviceSettings()
                .stream()
                .map(e->new DeviceSetting(e, false, this))
                .collect(Collectors.toSet());
    }
    public Profile(ProfileDTO profileDTO){
        try{
            this.creationUser = SecurityContextHolder.getContext().getAuthentication().getName();
            this.lastUpdateUser = this.creationUser;
        }catch (Exception ignored){

        }
        this.creationDate = new Date();
        this.lastUpdateDate = this.creationDate;
        fillFromDTO(profileDTO);
    }

    public Profile update(ProfileDTO profileDTO){
        try{
            this.lastUpdateUser = SecurityContextHolder.getContext().getAuthentication().getName();
        }catch (Exception ignored){
        }
        this.lastUpdateDate = new Date();
        fillFromDTO(profileDTO);
        return this;
    }

    public ProfileDTO toDto(){
        return ProfileDTO
                .builder()
                .id(id)
                .name(name)
                .creationUser(creationUser)
                .lastUpdateUser(lastUpdateUser)
                .creationDate(DateTimeUtils.fromDate(creationDate))
                .lastUpdateDate(DateTimeUtils.fromDate(lastUpdateDate))
                .deviceSettings(deviceSettings.stream().map(DeviceSetting::toDto).collect(Collectors.toSet()))
                .build();
    }

}
