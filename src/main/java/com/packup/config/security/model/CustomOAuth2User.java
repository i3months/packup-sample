package com.packup.config.security.model;

import com.packup.domain.auth.entity.SiteUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final SiteUser siteUser;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(SiteUser siteUser, Map<String, Object> attributes) {
        this.siteUser = siteUser;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getName() {
        return siteUser.getUsername(); // 유저의 닉네임 반환
    }

    public String getEmail() {
        return siteUser.getEmail();
    }

    public String getProfileImage() {
        return siteUser.getProfileImage();
    }

    public String getProvider() {
        return siteUser.getProvider();
    }

    public String getProviderId() {
        return siteUser.getProviderId();
    }
}