package com.packup.domain.auth.service;

import com.packup.config.security.model.GoogleOAuth2UserInfo;
import com.packup.config.security.model.KakaoOAuth2UserInfo;
import com.packup.config.security.model.OAuth2UserInfo;
import com.packup.config.security.model.OAuth2UserInfoFactory;
import com.packup.config.security.provider.JwtTokenProvider;
import com.packup.domain.auth.entity.SiteUser;
import com.packup.domain.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2Service {
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * OAuth2 인증을 수행하고, JWT를 발급하는 메서드
     */
    public String authenticate(String provider, String accessToken) {
        OAuth2UserInfo userInfo = getUserInfoFromProvider(provider, accessToken);

        // 기존 회원 조회
        SiteUser siteUser = authRepository.findByProviderId(userInfo.getId())
                .orElseGet(() -> {
                    // 신규 회원 가입
                    SiteUser newUser = new SiteUser(userInfo, provider);
                    return authRepository.save(newUser);
                });

        // 로그인할 때 프로필 정보 업데이트 (변경된 정보 반영)
        siteUser.setProfileImage(userInfo.getProfileImage());
        authRepository.save(siteUser);

        // JWT 토큰 발급
        return jwtTokenProvider.createToken(siteUser.getProviderId());
    }

    /**
     * OAuth2 Access Token을 이용하여 해당 OAuth 제공자로부터 사용자 정보를 가져옴
     */
    private OAuth2UserInfo getUserInfoFromProvider(String provider, String accessToken) {
        switch (provider.toLowerCase()) {
            case "google":
                return fetchGoogleUserInfo(accessToken);
            case "kakao":
                return fetchKakaoUserInfo(accessToken);
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    /**
     * Google OAuth2 API를 호출하여 사용자 정보 가져오기
     */
    private OAuth2UserInfo fetchGoogleUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return new GoogleOAuth2UserInfo(response.getBody());
    }

    /**
     * Kakao OAuth2 API를 호출하여 사용자 정보 가져오기
     */
    private OAuth2UserInfo fetchKakaoUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return new KakaoOAuth2UserInfo(response.getBody());
    }
}
