package com.rustam.CVFinder.dao.repository;

import com.rustam.CVFinder.dao.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<BaseUser, UUID> {
}
