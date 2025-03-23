package com.packup.config.security.service;

import com.packup.config.security.model.CustomOAuth2User;
import com.packup.config.security.model.OAuth2UserInfo;
import com.packup.config.security.model.OAuth2UserInfoFactory;
import com.packup.domain.auth.entity.SiteUser;
import com.packup.domain.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AuthRepository authRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        SiteUser siteUser = authRepository.findByProviderId(userInfo.getId())
                .orElseGet(() -> {
                    SiteUser newUser = new SiteUser(userInfo, registrationId);
                    return authRepository.save(newUser);
                });

        // 기존 유저가 로그인할 때 프로필 업데이트
        siteUser.setProfileImage(userInfo.getProfileImage());
        authRepository.save(siteUser);

        return new CustomOAuth2User(siteUser, oAuth2User.getAttributes());
    }

    public OAuth2User loadUserByProviderId(String providerId) {
        return authRepository.findByProviderId(providerId)
                .map(user -> new CustomOAuth2User(user, Map.of()))
                .orElse(null);
    }
}
