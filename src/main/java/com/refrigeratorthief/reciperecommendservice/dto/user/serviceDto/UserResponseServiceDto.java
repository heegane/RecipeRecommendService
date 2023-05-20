package com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto.UserResponseControllerDto;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseServiceDto {

    private String id;
    private String name;
    private String city;
    private String dong;

    public UserResponseServiceDto(User user) {
        id = user.getId();
        name = user.getName();
        city = user.getCity();
        dong = user.getDong();
    }

    public UserResponseControllerDto toControllerDto(){
        return UserResponseControllerDto.builder()
                .id(id)
                .name(name)
                .city(city)
                .dong(dong)
                .build();
    }
}
