package com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ingredient_unit")
public class IngredientUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INGREDIENT_UNIT_IDX")
    private Integer id;

    @Column(name = "INGREDIENT_UNIT_NM", length = 10, nullable = false, unique = true)
    private String name;
}
