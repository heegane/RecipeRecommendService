package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorControllerResponseDto {
    private Integer id;
    private Date expirationDate;
    private Integer quantity;
    private String location;
    private String user;
    private Integer ingredient;
}
