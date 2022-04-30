package kp.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kp.profile.domain.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
