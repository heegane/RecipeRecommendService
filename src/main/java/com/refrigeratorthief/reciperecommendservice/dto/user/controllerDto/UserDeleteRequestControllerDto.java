package com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserDeleteRequestServiceDto;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDeleteRequestControllerDto {

    private String id;
    private String pw;
    private String checkPw;

    public UserDeleteRequestServiceDto toServiceDto(){
        return UserDeleteRequestServiceDto.builder()
                .id(id)
                .pw(pw)
                .checkPw(checkPw)
                .build();
    }
}
