package kp.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String userName;
    private String password;
    private String roles;
    private LocalDateTime registrationDate;
}
