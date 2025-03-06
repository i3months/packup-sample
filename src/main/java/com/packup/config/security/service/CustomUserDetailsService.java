package com.packup.config.security.service;

import com.packup.config.security.model.CustomUserDetails;
import com.packup.domain.auth.entity.SiteUser;
import com.packup.domain.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SiteUser siteUser = authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        /*
         * 관련 비즈니스 로직은 이쪽에 작성
         * ex) 로그인 사용자 권한 설정, 비밀번호 오류, 계정 잠김 여부
         */

        return new CustomUserDetails(siteUser);
    }
}
