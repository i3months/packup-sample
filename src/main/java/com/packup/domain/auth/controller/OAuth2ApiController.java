package com.packup.domain.auth.controller;

import com.packup.config.security.provider.JwtTokenProvider;
import com.packup.domain.auth.model.JwtResponse;
import com.packup.domain.auth.model.OAuth2LoginRequest;
import com.packup.domain.auth.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/oauth2")
@RequiredArgsConstructor
public class OAuth2ApiController {

    private final OAuth2Service oAuth2Service;

    @PostMapping("/{provider}")
    public ResponseEntity<JwtResponse> authenticateUser(
            @PathVariable String provider,
            @RequestBody OAuth2LoginRequest loginRequest) {

        String jwtToken = oAuth2Service.authenticate(provider, loginRequest.getAccessToken());

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

}
