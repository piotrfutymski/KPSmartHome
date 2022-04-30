package kp.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kp.profile.domain.Profile;
import kp.profile.repository.ProfileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Autowired
    private final ProfileRepository profileRepository;

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
