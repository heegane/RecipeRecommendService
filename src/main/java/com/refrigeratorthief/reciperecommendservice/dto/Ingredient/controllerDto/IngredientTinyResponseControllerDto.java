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
public class IngredientTinyResponseControllerDto {
    private Integer id;
    private String name;
    private String img;
}
