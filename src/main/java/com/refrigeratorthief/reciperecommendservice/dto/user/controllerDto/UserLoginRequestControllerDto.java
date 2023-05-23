package com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserLoginRequestServiceDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserLoginRequestControllerDto {

    @NotEmpty(message = "id를 입력해주세요!")
    private String id;

    @NotEmpty(message = "비밀번호를 입력해주세요!")
    private String pw;

    public UserLoginRequestServiceDto toServiceDto(){
        return UserLoginRequestServiceDto.builder()
                .id(id)
                .pw(pw)
                .build();
    }
}
