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
public class UserRegisterRequestServiceDto {

    private String id;
    private String name;
    private String pw;
    private String checkPw;
    private String city;
    private String dong;

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .pw(pw)
                .city(city)
                .dong(dong)
                .build();
    }
}
