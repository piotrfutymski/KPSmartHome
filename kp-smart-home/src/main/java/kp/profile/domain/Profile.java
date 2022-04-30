package kp.profile.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String creationUser;

    private String lastUpdateUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

}
