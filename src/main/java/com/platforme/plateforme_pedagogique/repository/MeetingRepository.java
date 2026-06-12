package com.platforme.plateforme_pedagogique.repository;

import com.platforme.plateforme_pedagogique.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Optional<Meeting> findByModuleIdAndActifTrue(Long moduleId);
}
