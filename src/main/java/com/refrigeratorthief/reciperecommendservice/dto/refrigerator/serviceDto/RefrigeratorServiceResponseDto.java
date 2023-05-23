package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorControllerResponseDto;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public RefrigeratorControllerResponseDto toControllerDto(RefrigeratorServiceResponseDto refrigeratorServiceResponseDto) {
        return RefrigeratorControllerResponseDto.builder()
                .id(refrigeratorServiceResponseDto.getId())
                .expirationDate(refrigeratorServiceResponseDto.getExpirationDate())
                .quantity(refrigeratorServiceResponseDto.getQuantity())
                .location(refrigeratorServiceResponseDto.getLocation())
                .userId(refrigeratorServiceResponseDto.getUserId())
                .userName(refrigeratorServiceResponseDto.getUserName())
                .ingredientId(refrigeratorServiceResponseDto.getIngredientId())
                .ingredientName(refrigeratorServiceResponseDto.getIngredientName())
                .ingredientImg(refrigeratorServiceResponseDto.getIngredientImg())
                .ingredientUnitId(refrigeratorServiceResponseDto.getIngredientUnitId())
                .ingredientUnitName(refrigeratorServiceResponseDto.getIngredientUnitName())
                .build();
    }
}
