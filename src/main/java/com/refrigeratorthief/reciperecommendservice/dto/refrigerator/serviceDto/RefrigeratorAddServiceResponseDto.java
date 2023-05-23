package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardAddControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardAddServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorAddControllerResponseDto;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorAddServiceResponseDto {
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

    public RefrigeratorAddControllerResponseDto toControllerDto(RefrigeratorAddServiceResponseDto refrigeratorAddServiceResponseDto) {
        return RefrigeratorAddControllerResponseDto.builder()
                .id(refrigeratorAddServiceResponseDto.getId())
                .expirationDate(refrigeratorAddServiceResponseDto.getExpirationDate())
                .quantity(refrigeratorAddServiceResponseDto.getQuantity())
                .location(refrigeratorAddServiceResponseDto.getLocation())
                .userId(refrigeratorAddServiceResponseDto.getUserId())
                .userName(refrigeratorAddServiceResponseDto.getUserName())
                .ingredientId(refrigeratorAddServiceResponseDto.getIngredientId())
                .ingredientName(refrigeratorAddServiceResponseDto.getIngredientName())
                .ingredientImg(refrigeratorAddServiceResponseDto.getIngredientImg())
                .ingredientUnitId(refrigeratorAddServiceResponseDto.getIngredientUnitId())
                .ingredientUnitName(refrigeratorAddServiceResponseDto.getIngredientUnitName())
                .build();
    }
}