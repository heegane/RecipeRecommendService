package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorUpdateServiceRequestDto {

    private Date expirationDate;
    private Integer quantity;
    private String location;
    private User user;
    private Ingredient ingredient;

    public Refrigerator toEntity() {
        return Refrigerator.builder()
                .expirationDate(expirationDate)
                .quantity(quantity)
                .location(location)
                .user(user)
                .ingredient(ingredient)
                .build();
    }
}
