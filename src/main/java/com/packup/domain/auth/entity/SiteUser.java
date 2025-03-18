package com.packup.domain.auth.entity;

import com.packup.config.security.model.OAuth2UserInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "site_user")
@Getter
@Setter
@NoArgsConstructor
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String providerId;

    @Column(nullable = false, length = 50)
    private String provider;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    private String profileImage;

    public SiteUser(OAuth2UserInfo userInfo, String provider) {
        this.providerId = userInfo.getId();
        this.provider = provider;
        this.username = userInfo.getName();
        this.email = userInfo.getEmail();
        this.profileImage = userInfo.getProfileImage();
    }
}
