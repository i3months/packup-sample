package com.packup.domain.sample.controller;

import com.packup.common.dto.ApiResponse;
import com.packup.domain.sample.model.DevUserDto;
import com.packup.domain.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sample")
@RequiredArgsConstructor
public class SampleApiController {

    private final SampleService sampleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DevUserDto>>> getAllUsers() {
        return ResponseEntity.ok(sampleService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<DevUserDto>> getUserByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(sampleService.getUserByUserId(userId));
    }

    @GetMapping("/native")
    public ResponseEntity<ApiResponse<DevUserDto>> getUserByNativeQuery() {
        return ResponseEntity.ok(sampleService.getUserByNativeQuery());
    }
}
