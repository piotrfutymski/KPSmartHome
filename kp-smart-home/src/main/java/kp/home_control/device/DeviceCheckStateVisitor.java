package kp.home_control.device;

import kp.device_setting.DeviceSettingType;
import kp.device_setting.domain.DeviceSetting;
import kp.home_control.device.types.RGBBulb;
import kp.home_control.device.types.Speakers;
import kp.home_control.device.types.StandardBulb;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceCheckStateVisitor implements DeviceVisitor{

    List<DeviceSetting> deviceSettings;

    List<DeviceSetting> actualSettings = new ArrayList<>();

    public DeviceCheckStateVisitor(List<DeviceSetting> deviceSettings) {
        this.deviceSettings = deviceSettings;
    }

    private void modify(Device device, Object val, DeviceSettingType type){
        deviceSettings.stream()
                .filter(e->e.getDeviceName().equals(device.getName()) && e.getSetting().equals(type))
                .findFirst()
                .ifPresentOrElse(e->{
                    e.setDeviceValue(e.getSetting().saveValue(val));
                    actualSettings.add(e);
                }, ()->{
                    actualSettings.add(DeviceSetting.builder()
                            .deviceName(device.getName())
                            .deviceValue(type.saveValue(val))
                            .attachedProfile(null)
                            .started(true)
                            .setting(type)
                            .build());
                });
    }
    @Override
    public void visit(StandardBulb standardBulb) {
        Integer val = standardBulb.getBrightness();
        modify(standardBulb, val, DeviceSettingType.BRIGHTNESS);
    }

    @Override
    public void visit(Speakers speakers) {
        Integer volume = speakers.getVolume();
        String spotifySong = speakers.getSong();
        String spotifyPlaylist = speakers.getPlaylist();
        modify(speakers, volume, DeviceSettingType.VOLUME);
        modify(speakers, spotifySong != null ? spotifySong : "", DeviceSettingType.SPOTIFY_SONG);
        modify(speakers, spotifyPlaylist != null ? spotifyPlaylist : "", DeviceSettingType.SPOTIFY_PLAYLIST);
    }

    @Override
    public void visit(RGBBulb rgbBulb) {
        Color color = rgbBulb.getColor();
        modify(rgbBulb, color, DeviceSettingType.COLOR);
    }

    public List<DeviceSetting> getActualSettings() {
        return actualSettings;
    }
}
