package com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserRegisterRequestServiceDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserRegisterRequestControllerDto {

    @NotEmpty(message = "Id를 입력해주세요!")
    private String id;

    @NotEmpty(message = "닉네임을 입력해주세요!")
    private String name;

    @NotEmpty(message = "비밀번호를 입력해주세요!")
    private String pw;

    @NotEmpty(message = "비밀번호를 한번 더 입력해주세요!")
    private String checkPw;

    @NotEmpty(message = "주소(시) 정보를 입력해주세요!")
    private String city;

    @NotEmpty(message = "주소(동) 정보를 입력해주세요!")
    private String dong;

    public UserRegisterRequestServiceDto toServiceDto(){
        return UserRegisterRequestServiceDto.builder()
                .id(id)
                .name(name)
                .pw(pw)
                .checkPw(checkPw)
                .city(city)
                .dong(dong)
                .build();
    }
}
