package com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserLoginRequestServiceDto {

    private String id;
    private String pw;

    public User toEntity() {
        return User.builder()
                .id(id)
                .pw(pw)
                .build();
    }
}
