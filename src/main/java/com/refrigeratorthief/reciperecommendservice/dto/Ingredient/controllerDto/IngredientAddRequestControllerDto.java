package com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientAddRequestServiceDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class IngredientAddRequestControllerDto {

    @NotEmpty(message = "추가할 재료 이름을 입력해주세요!")
    private String name;

    private String img;

    @NotNull(message = "추가할 재료의 단위를 입력해주세요!")
    private Integer ingredientUnitId;

    public IngredientAddRequestServiceDto toServiceDto() {
        return IngredientAddRequestServiceDto.builder()
                .name(name)
                .img(img)
                .ingredientUnit(IngredientUnit.builder().id(ingredientUnitId).build())
                .build();
    }
    public Ingredient toEntity() {
        return Ingredient.builder()
                .name(name)
                .img(img)
                .unit(IngredientUnit.builder().id(ingredientUnitId).build())
                .build();
    }
}
