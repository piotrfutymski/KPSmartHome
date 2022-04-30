package kp;

import kp.user.domain.User;
import kp.user.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {
    protected final Log logger = LogFactory.getLog(getClass());

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService){
        return args -> {
            try{
                userService.getUser("Piotr");
            }catch (Exception e){
                userService.createUser(User.builder().userName("Piotr").password("admin").roles("ADMIN").build());
            }
        };
    }

}
