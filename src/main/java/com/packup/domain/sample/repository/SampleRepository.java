package com.packup.domain.sample.repository;

import com.packup.domain.sample.entity.DevUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<DevUser, Long> {
    Optional<DevUser> findByUserId(String userId);

    @Query(value = "SELECT * FROM DEV_USER WHERE USER_ID = :userId", nativeQuery = true)
    Optional<DevUser> findByNativeQuery(@Param("userId") String userId);
}
