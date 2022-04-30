package kp.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordInfo {

    private String newPassword;

    private String oldPassword;
}
