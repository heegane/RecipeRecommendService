package com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class IngredientResponseControllerDto {
    private Integer id;
    private String name;
    private String img;
    private Integer ingredientUnitId;
    private String ingredientUnitName;
}
