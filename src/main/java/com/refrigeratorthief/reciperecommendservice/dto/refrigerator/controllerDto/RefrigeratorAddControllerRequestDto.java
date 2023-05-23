package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardAddServiceRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.RefrigeratorAddServiceRequestDto;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorAddControllerRequestDto {
    private Date expirationDate;
    private Integer quantity;
    private String location;
    private String userId;
    private Integer ingredientId;

    public RefrigeratorAddServiceRequestDto toServiceDto(RefrigeratorAddControllerRequestDto refrigeratorAddControllerRequestDto) {
        return RefrigeratorAddServiceRequestDto.builder()
                .expirationDate(refrigeratorAddControllerRequestDto.getExpirationDate())
                .quantity(refrigeratorAddControllerRequestDto.getQuantity())
                .location(refrigeratorAddControllerRequestDto.getLocation())
                .userId(refrigeratorAddControllerRequestDto.getUserId())
                .ingredientId(refrigeratorAddControllerRequestDto.getIngredientId())
                .build();
    }
}
