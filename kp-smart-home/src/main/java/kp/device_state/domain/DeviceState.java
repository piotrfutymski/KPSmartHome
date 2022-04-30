package kp.device_state.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="DeviceState")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceState {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String deviceName;

    private Boolean isOnline;
}
