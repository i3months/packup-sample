package com.packup.config.security.provider;

import com.packup.config.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomOAuth2AuthenticationProvider implements AuthenticationProvider {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String providerId = authentication.getName();

        OAuth2User oAuth2User = customOAuth2UserService.loadUserByProviderId(providerId);

        if (oAuth2User == null) {
            throw new BadCredentialsException("Invalid OAuth2 authentication");
        }

        return new UsernamePasswordAuthenticationToken(oAuth2User, null, oAuth2User.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
