package com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class IngredientAddRequestServiceDto {

    private String name;
    private String img;
    private IngredientUnit ingredientUnit;

    public Ingredient toEntity(IngredientUnit ingredientUnit){
        return Ingredient.builder()
                .name(name)
                .img(img)
                .unit(ingredientUnit)
                .build();
    }
}
