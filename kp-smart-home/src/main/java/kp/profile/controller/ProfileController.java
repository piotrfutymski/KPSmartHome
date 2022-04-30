package kp.profile.controller;

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
    private List<Profile> getAllProfiles(){
        return profileService.getAllProfiles();
    }

    @PostMapping
    private Profile createProfile(@RequestBody Profile profile){
        return profileService.createProfile(profile);
    }

}
