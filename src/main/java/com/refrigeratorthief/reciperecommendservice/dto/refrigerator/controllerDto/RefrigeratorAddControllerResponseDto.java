package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorAddControllerResponseDto {
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
