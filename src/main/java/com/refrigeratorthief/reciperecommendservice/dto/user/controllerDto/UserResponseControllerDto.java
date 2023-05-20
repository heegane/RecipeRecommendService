package com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseControllerDto {

    private String id;
    private String name;
    private String city;
    private String dong;
}
