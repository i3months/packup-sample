package com.packup.config.security;

import com.packup.config.security.filter.JwtAuthenticationFilter;
import com.packup.config.security.handler.OAuth2AuthenticationSuccessHandler;
import com.packup.config.security.provider.CustomAuthenticationProvider;
import com.packup.config.security.provider.CustomOAuth2AuthenticationProvider;
import com.packup.config.security.provider.JwtTokenProvider;
import com.packup.config.security.service.CustomOAuth2UserService;
import com.packup.config.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
//    private final CustomOAuth2UserService customOAuth2UserService;
//    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.disable()) // csrf는 나중에 잡아주기
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
            )
                // 이 api 는 ssr 방식에서 사용함
//            .oauth2Login(oauth2 -> oauth2
//                    .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
//                    .successHandler(oAuth2AuthenticationSuccessHandler)
//            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//            .authenticationProvider(customAuthenticationProvider());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

//    @Bean
//    public CustomOAuth2AuthenticationProvider customOAuth2AuthenticationProvider() {
//        return new CustomOAuth2AuthenticationProvider(customOAuth2UserService, jwtTokenProvider);
//    }


//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new ProviderManager(List.of(customOAuth2AuthenticationProvider()));
//    }

}
