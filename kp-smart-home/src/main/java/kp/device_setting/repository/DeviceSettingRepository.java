package kp.device_setting.repository;

import kp.device_setting.domain.DeviceSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
public interface DeviceSettingRepository extends JpaRepository<DeviceSetting, Long> {


    Collection<DeviceSetting> findByDeviceNameAndStartedAtIsNotNull(@Param("deviceName") String deviceName);

    Collection<DeviceSetting> findByDeviceName(String deviceName);
}
