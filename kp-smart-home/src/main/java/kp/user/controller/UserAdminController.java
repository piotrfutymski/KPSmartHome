package kp.user.controller;

import kp.user.domain.User;
import kp.user.dto.UserDTO;
import kp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserAdminController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers().stream().map(User::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{userName}")
    public UserDTO getUser(@PathVariable("userName") String userName){
        return userService.getUser(userName).toDto();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user){
        return userService.createUser(new User(user)).toDto();
    }

    @DeleteMapping("/{userName}")
    public void deleteUser(@PathVariable("userName") String userName){
        userService.deleteUser(userName);
    }
}
