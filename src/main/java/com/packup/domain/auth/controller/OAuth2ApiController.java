package com.packup.domain.auth.controller;

import com.packup.config.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/oauth2")
@RequiredArgsConstructor
public class OAuth2ApiController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;



}
