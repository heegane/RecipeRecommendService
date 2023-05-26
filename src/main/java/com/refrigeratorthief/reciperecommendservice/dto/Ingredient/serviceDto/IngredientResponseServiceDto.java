package com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientResponseControllerDto;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientResponseServiceDto {
    private Integer id;
    private String name;
    private String img;
    private IngredientUnit ingredientUnit;

    public IngredientResponseServiceDto(Ingredient ingredient) {
        id = ingredient.getId();
        name = ingredient.getName();
        img = ingredient.getImg();
        ingredientUnit = ingredient.getUnit();
    }

    public IngredientResponseControllerDto toControllerDto() {
        return IngredientResponseControllerDto.builder()
                .id(id)
                .name(name)
                .img(img)
                .ingredientUnitId(ingredientUnit.getId())
                .ingredientUnitName(ingredientUnit.getName())
                .build();
    }
}