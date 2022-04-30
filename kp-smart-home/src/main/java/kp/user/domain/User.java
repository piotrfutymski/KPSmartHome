package kp.user.domain;

import kp.config.DateTimeUtils;
import kp.user.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name= "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    private String roles;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date registrationDate;

    public User(UserDTO userDTO){
        userName = userDTO.getUserName();
        password = userDTO.getPassword();
        roles = userDTO.getRoles();
        registrationDate = DateTimeUtils.fromLocalDateTime(userDTO.getRegistrationDate());
    }

    public UserDTO toDto(){
        return UserDTO
                .builder()
                .userName(userName)
                .password(password)
                .roles(roles)
                .registrationDate(DateTimeUtils.fromDate(registrationDate))
                .build();
    }
}
