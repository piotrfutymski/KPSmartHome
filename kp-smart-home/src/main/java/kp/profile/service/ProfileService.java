package kp.profile.service;

import kp.home_control.dto.DeviceSettingDTO;
import kp.home_control.service.HomeService;
import kp.profile.dto.ProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kp.profile.domain.Profile;
import kp.profile.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Autowired
    private final ProfileRepository profileRepository;

    @Autowired
    private final HomeService homeService;

    public List<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll()
                .stream()
                .map(Profile::toDto)
                .collect(Collectors.toList());
    }

    public ProfileDTO createProfile(ProfileDTO profile) {
        if(getAllProfiles().stream().anyMatch(e->e.getName().equals(profile.getName()))){
            throw new IllegalArgumentException("Profile with given name already exists");
        }
        return profileRepository.save(new Profile(profile)).toDto();
    }

    public ProfileDTO updateProfile(Long id, ProfileDTO profile) {
        if(getAllProfiles().stream().anyMatch(e->e.getName().equals(profile.getName()) && !e.getId().equals(id))){
            throw new IllegalArgumentException("Profile with given name already exists");
        }
        return profileRepository.save(profileRepository.findById(id).orElseThrow().update(profile)).toDto();
    }

    public ProfileDTO getProfile(Long id) {
        return profileRepository.findById(id).map(Profile::toDto).orElse(null);
    }

    public void startFromProfile(Long id) {
        Optional<ProfileDTO> profile = Optional.of(getProfile(id));
        profile.ifPresent(e->homeService.setDeviceSettings(new ArrayList<>(e.getDeviceSettings()), null));
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
