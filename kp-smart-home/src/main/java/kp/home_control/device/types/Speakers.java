package kp.home_control.device.types;

import kp.device_setting.DeviceSettingType;
import kp.home_control.device.AbstractDevice;
import kp.home_control.device.Device;
import kp.home_control.device.DeviceVisitor;
import kp.home_control.dto.DeviceCapabilities;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Speakers extends AbstractDevice {

    String playlistId;
    String songId;
    Integer volume = 0;

    public Speakers(String name) {
        super(name);
    }

    @Override
    public void accept(DeviceVisitor deviceVisitor) {
        deviceVisitor.visit(this);
    }

    @Override
    public Boolean isOnline() {
        return true;
    }

    @Override
    public List<DeviceSettingType> getCapabilities() {
        return List.of(DeviceSettingType.VOLUME, DeviceSettingType.SPOTIFY_SONG, DeviceSettingType.SPOTIFY_PLAYLIST, DeviceSettingType.NEXT_VOLUME);
    }

    public void setVolume(Integer volume) {
        log.info("On device {}, setting volume: {}", getName(), volume);
        this.volume = volume;
    }

    public Integer getVolume(){
        return volume;
    }

    public void setSong(String songId){
        log.info("On device {}, setting spotify song: {}", getName(), songId);
        this.songId = songId;
    }

    public String getSong(){
        return songId;
    }

    public void setPlaylist(String playlistId){
        log.info("On device {}, setting spotify playlist: {}", getName(), playlistId);
        this.playlistId = playlistId;
    }

    public String getPlaylist(){
        return playlistId;
    }

    public void setSongAndPlaylist(String song, String playlist) {
        if(song != null){
            setSong(song);
        }else if(playlist != null){
            setPlaylist(playlist);
        }
    }
}
