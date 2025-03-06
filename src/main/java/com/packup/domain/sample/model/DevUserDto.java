package com.packup.domain.sample.model;

import com.packup.domain.sample.entity.DevUser;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevUserDto {
    private Long id;
    private String userName;
    private String userId;

    public static DevUserDto fromEntity(DevUser user) {
        return DevUserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userId(user.getUserId())
                .build();
    }
}
