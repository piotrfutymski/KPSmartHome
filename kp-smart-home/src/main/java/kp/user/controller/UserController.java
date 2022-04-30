package kp.user.controller;

import kp.user.dto.ChangePasswordInfo;
import kp.user.service.InvalidOldPasswordException;
import kp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/changePassword")
    public void changePassword(@RequestBody ChangePasswordInfo changePasswordInfo) throws InvalidOldPasswordException {
        userService.changePassword(changePasswordInfo.getNewPassword(), changePasswordInfo.getOldPassword());
    }
}
