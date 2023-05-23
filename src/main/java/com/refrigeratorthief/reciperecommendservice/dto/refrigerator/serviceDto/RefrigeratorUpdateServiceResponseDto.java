package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorUpdateServiceResponseDto {
    private Integer id;
    private Date expirationDate;
    private Integer quantity;
    private String location;

    private String userId;
    private String userName;

    private Integer ingredientId;
    private String ingredientName;
    private String ingredientImg;

    private Integer ingredientUnitId;
    private String ingredientUnitName;
}
