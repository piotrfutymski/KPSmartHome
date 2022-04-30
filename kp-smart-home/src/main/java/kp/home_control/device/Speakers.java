package kp.home_control.device;

import kp.device_setting.DeviceSettingType;
import kp.home_control.dto.DeviceCapabilities;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

public class Speakers implements Device{

    private final String spotify ="http://open.spotify.com";

    private final WebClient webClient;

    public Speakers() {
        this.webClient = WebClient.create(spotify);
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

    @Override
    public String getName() {
        return "Głośniki";
    }

    public void setVolume(Integer settingValue) {
        var uriSpec = webClient.get();
        var bodySpec = uriSpec.uri("/track/5Pr6R1aGN7tk18HslPUrSW?si=095777f1e8784b80");
        bodySpec.retrieve().bodyToMono(String.class);
    }
}
