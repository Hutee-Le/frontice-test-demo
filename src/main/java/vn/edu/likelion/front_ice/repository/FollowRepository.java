package vn.edu.likelion.front_ice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.front_ice.entity.AccountEntity;
import vn.edu.likelion.front_ice.entity.FollowEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {
    Optional<List<FollowEntity>> findByChallengerId(String challengerId);
    Optional<List<FollowEntity>> findByRecruiterId(String recruiterId);

    Optional<FollowEntity> findByChallengerIdAndRecruiterId(String challengerId, String recruiterId);
}
