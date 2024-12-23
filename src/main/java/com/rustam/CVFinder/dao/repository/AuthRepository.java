package com.rustam.CVFinder.dao.repository;

import com.rustam.CVFinder.dao.entity.BaseUser;
import com.rustam.CVFinder.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<BaseUser, UUID> {

    @Query(value = "SELECT * FROM base_users WHERE (user_type = 'USER' OR user_type = 'HUMAN_RESOURCE') AND username = :username", nativeQuery = true)
    Optional<BaseUser> findByUsername(String username);
}
