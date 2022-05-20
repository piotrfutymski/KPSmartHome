package kp.home_control.device;

import kp.device_setting.DeviceSettingType;
import kp.device_setting.domain.DeviceSetting;
import kp.device_setting.repository.DeviceSettingRepository;
import kp.home_control.device.types.RGBBulb;
import kp.home_control.device.types.Speakers;
import kp.home_control.device.types.StandardBulb;
import kp.home_control.dto.DeviceSettingDTO;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DeviceSettingsVisitor implements DeviceVisitor {

    private final List<DeviceSettingDTO> settingList;

    public Optional<DeviceSettingDTO> getFilteredSettingsForDevice(Device device, DeviceSettingType type){
        return settingList
                .stream()
                .filter(e->e.getDeviceName().equals(device.getName()))
                .filter(e->device.getCapabilities().contains(e.getSetting()))
                .filter(e->e.getSetting().equals(type))
                .findFirst();
    }


    @Override
    public void visit(StandardBulb standardBulb) {
        getFilteredSettingsForDevice(standardBulb, DeviceSettingType.BRIGHTNESS)
                .ifPresentOrElse(e->standardBulb.setBrightness(e.getSettingValue()), ()->standardBulb.setBrightness(0));

    }

    @Override
    public void visit(Speakers speakers) {
        getFilteredSettingsForDevice(speakers, DeviceSettingType.VOLUME)
                .ifPresentOrElse(e->speakers.setVolume(e.getSettingValue()), ()-> speakers.setVolume(0));

        String song = (String) getFilteredSettingsForDevice(speakers, DeviceSettingType.SPOTIFY_SONG)
                .map(DeviceSettingDTO::getSettingValue)
                .orElse(null);

        String playlist = (String) getFilteredSettingsForDevice(speakers, DeviceSettingType.SPOTIFY_PLAYLIST)
                .map(DeviceSettingDTO::getSettingValue)
                .orElse(null);

        speakers.setSongAndPlaylist(song, playlist);
    }

    @Override
    public void visit(RGBBulb rgbBulb) {
        Integer brightness = (Integer) getFilteredSettingsForDevice(rgbBulb, DeviceSettingType.BRIGHTNESS)
                .map(DeviceSettingDTO::getSettingValue)
                .orElse(null);

        Integer color = (Integer) getFilteredSettingsForDevice(rgbBulb, DeviceSettingType.COLOR)
                .map(DeviceSettingDTO::getSettingValue)
                .orElse(null);

        DeviceSettingType.ChangingIntSettingValue changingColor = (DeviceSettingType.ChangingIntSettingValue) getFilteredSettingsForDevice(rgbBulb, DeviceSettingType.NEXT_COLOR)
                .map(DeviceSettingDTO::getSettingValue)
                .orElse(null);


        if(brightness != null){
            rgbBulb.setBrightness(brightness);
        }else if( color != null){
            rgbBulb.setColor(color);
        }else if(changingColor != null){
            rgbBulb.setColor(changingColor.getNextVal(), changingColor.getTicks());
        }
    }
}
