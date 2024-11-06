package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.ChallengerEntity;
import vn.edu.likelion.front_ice.entity.RecruiterEntity;

import java.util.Optional;

@Repository
public interface RecruiterRepository extends JpaRepository<RecruiterEntity, String> {
    Optional<RecruiterEntity> findByAccountId(String id);
}
