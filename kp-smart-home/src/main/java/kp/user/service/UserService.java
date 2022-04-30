package kp.user.service;

import com.google.common.base.Preconditions;
import kp.user.domain.User;
import kp.user.dto.UserDTO;
import kp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final Integer MIN_PASS_AND_USERNAME_LENGTH = 3;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("Load by username: {}", userName);
        return new UserDetailsImpl(getUser(userName));
    }

    public User getUser(String userName){
        log.info("getUser: {}", userName);
        return userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException(userName + " not found."));
    }

    public List<User> getAllUsers() {
        log.info("getAllUsers");
        return userRepository.findAll();
    }

    public User createUser(User user){
        Preconditions.checkArgument(user.getUserName().length() > MIN_PASS_AND_USERNAME_LENGTH);
        Preconditions.checkArgument(user.getPassword().length() > MIN_PASS_AND_USERNAME_LENGTH);
        log.info("createUser: {}", user.getUserName());
        user.setRegistrationDate(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(String userName){
        log.info("deleteUser: {}", userName);
        userRepository.deleteByUserName(userName);
    }

    public void changePassword(String password, String oldPassword) throws InvalidOldPasswordException {
        User user = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        Preconditions.checkArgument(password.length() > MIN_PASS_AND_USERNAME_LENGTH, "To short password should be min. 3 symbols");
        log.info("changePassword for: {}", user.getUserName());
        if (!checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException();
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    private boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

}
