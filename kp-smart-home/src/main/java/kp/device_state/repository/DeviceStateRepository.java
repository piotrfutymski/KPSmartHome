package kp.device_state.repository;

import kp.device_state.domain.DeviceState;
import kp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceStateRepository extends JpaRepository<DeviceState, Long> {

    Optional<DeviceState> findByDeviceName(String deviceName);

}
