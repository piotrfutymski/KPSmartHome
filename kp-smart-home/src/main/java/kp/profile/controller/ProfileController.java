package kp.profile.controller;

import kp.profile.dto.ProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import kp.profile.domain.Profile;
import kp.profile.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private final ProfileService profileService;

    @GetMapping
    public List<ProfileDTO> getAllProfiles(){
        return profileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ProfileDTO getProfile(@PathVariable Long id){
        return profileService.getProfile(id);
    }

    @PostMapping("/{id}")
    public void startFromProfile(@PathVariable Long id) {profileService.startFromProfile(id);}

    @PostMapping
    public ProfileDTO createProfile(@RequestBody ProfileDTO profile){
        return profileService.createProfile(profile);
    }

    @PutMapping("/{id}")
    public ProfileDTO updateProfile(@PathVariable Long id, @RequestBody ProfileDTO profile){
        return profileService.updateProfile(id, profile);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id){
        profileService.deleteProfile(id);
    }

}
