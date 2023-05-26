package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto;

import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
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

    private Date expirationDate;
    private Integer quantity;
    private String location;
    private String userId;
    private Integer ingredientId;

    public RefrigeratorUpdateServiceRequestDto toServiceDto() {
        return  RefrigeratorUpdateServiceRequestDto.builder()
                .expirationDate(expirationDate)
                .quantity(quantity)
                .location(location)
                .user(User.builder().id(userId).build())
                .ingredient(Ingredient.builder().id(ingredientId).build())
                .build();
    }
}