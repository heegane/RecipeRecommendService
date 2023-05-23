package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardUpdateControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardUpdateServiceRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.RefrigeratorUpdateServiceRequestDto;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorUpdateControllerRequestDto {
    private Integer id;
    private Date expirationDate;
    private Integer quantity;
    private String location;
    private String userId;
    private Integer ingredientId;

    public RefrigeratorUpdateServiceRequestDto toServiceDto(RefrigeratorUpdateControllerRequestDto refrigeratorUpdateControllerRequestDto) {
        return  RefrigeratorUpdateServiceRequestDto.builder()
                .id(refrigeratorUpdateControllerRequestDto.getId())
                .expirationDate(refrigeratorUpdateControllerRequestDto.getExpirationDate())
                .quantity(refrigeratorUpdateControllerRequestDto.getQuantity())
                .location(refrigeratorUpdateControllerRequestDto.getLocation())
                .userId(refrigeratorUpdateControllerRequestDto.getUserId())
                .ingredientId(refrigeratorUpdateControllerRequestDto.getIngredientId())
                .build();
    }
}