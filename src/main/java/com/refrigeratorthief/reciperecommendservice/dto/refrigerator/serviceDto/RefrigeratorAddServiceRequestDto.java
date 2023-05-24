package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorAddServiceRequestDto {
    private UserRepository userRepository;

    private Date expirationDate;
    private Integer quantity;
    private String location;
    private String userId;
    private Integer ingredientId;

    public Refrigerator toEntity() {
        return Refrigerator.builder()
                .expirationDate(expirationDate)
                .quantity(quantity)
                .location(location)
                .user(User.builder().id(userId).build())
                .ingredient(Ingredient.builder().id(ingredientId).build())
                .build();
    }
}