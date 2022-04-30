package kp.user;

import kp.Application;
import kp.DataSourceTestConfig;
import kp.user.domain.User;
import kp.user.service.InvalidOldPasswordException;
import kp.user.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        Application.class,
        DataSourceTestConfig.class})
@ActiveProfiles("test")
@RequiredArgsConstructor
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private static final User piotr = User.builder()
            .userName("Piotr")
            .password("test123")
            .roles("ADMIN")
            .build();

    @BeforeAll
    public void create(){
        userService.createUser(piotr);
    }

    @Test
    public void createUserTest(){
        assertNotNull(userService.getUser("Piotr"));
    }

    @Test
    public void passwordEncodingTest(){
        User user = userService.getUser("Piotr");
        assertNotEquals(user.getPassword(), "test123");
    }

}
