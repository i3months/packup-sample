package com.packup.domain.sample.repository;

import com.packup.domain.sample.entity.DevUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<DevUser, Long> {
    Optional<DevUser> findByUserId(String userId);
}
