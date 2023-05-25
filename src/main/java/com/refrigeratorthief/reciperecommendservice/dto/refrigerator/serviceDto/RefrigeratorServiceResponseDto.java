package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorControllerResponseDto;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RefrigeratorServiceResponseDto {
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

    public RefrigeratorServiceResponseDto(Refrigerator refrigerator){
        id = refrigerator.getId();
        expirationDate = refrigerator.getExpirationDate();
        quantity = refrigerator.getQuantity();
        location = refrigerator.getLocation();
        userId = refrigerator.getUser().getId();
        userName = refrigerator.getUser().getName();
        ingredientId = refrigerator.getIngredient().getId();
        ingredientName = refrigerator.getIngredient().getName();
        ingredientImg = refrigerator.getIngredient().getImg();
        ingredientUnitId = refrigerator.getIngredient().getUnit().getId();
        ingredientUnitName = refrigerator.getIngredient().getUnit().getName();
    }

    public RefrigeratorControllerResponseDto toControllerDto() {
        return RefrigeratorControllerResponseDto.builder()
                .id(id)
                .expirationDate(expirationDate)
                .quantity(quantity)
                .location(location)
                .userId(userId)
                .userName(userName)
                .ingredientId(ingredientId)
                .ingredientName(ingredientName)
                .ingredientImg(ingredientImg)
                .ingredientUnitId(ingredientUnitId)
                .ingredientUnitName(ingredientUnitName)
                .build();
    }
}
