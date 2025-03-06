package com.packup.domain.sample.service;

import com.packup.common.dto.ApiResponse;
import com.packup.domain.sample.model.DevUserDto;
import com.packup.domain.sample.entity.DevUser;
import com.packup.domain.sample.repository.SampleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository sampleRepository;

    public ApiResponse<List<DevUserDto>> getAllUsers() {
        List<DevUserDto> users = sampleRepository.findAll()
                .stream()
                .map(DevUserDto::fromEntity)
                .collect(Collectors.toList());

        return ApiResponse.success(users, "전체 사용자를 조회합니다.");
    }

    public ApiResponse<DevUserDto> getUserByUserId(String userId) {
        DevUser user = sampleRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 유저를 찾을 수 없습니다."));

        return ApiResponse.success(DevUserDto.fromEntity(user), "사용자 정보를 조회합니다.");
    }

    public ApiResponse<DevUserDto> getUserByNativeQuery() {
        DevUser user = sampleRepository.findByNativeQuery("jmjeong")
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 유저를 찾을 수 없습니다."));

        return ApiResponse.success(DevUserDto.fromEntity(user), "사용자 정보를 조회합니다.");
    }
}
