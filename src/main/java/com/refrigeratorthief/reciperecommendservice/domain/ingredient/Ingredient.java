package com.refrigeratorthief.reciperecommendservice.domain.ingredient;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ingredient")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INGREDIENT_IDX")
    private Integer id;

    @Column(name = "INGREDIENT_NM", length = 45, nullable = false, unique = true)
    private String name;

    @Column(name = "INGREDIENT_IMG", length = 500)
    private String img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INGREDIENT_UNIT_INGREDIENT_UNIT_IDX", nullable = false)
    private IngredientUnit unit;
}
