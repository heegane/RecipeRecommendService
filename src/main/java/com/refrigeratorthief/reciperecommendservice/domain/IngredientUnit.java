package com.refrigeratorthief.reciperecommendservice.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "INGREDIENT_UNIT")
public class IngredientUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INGREDIENT_UNIT_IDX")
    private int id;

    @Column(name = "INGREDIENT_UNIT_NM", length = 10, nullable = false, unique = true)
    private String name;
}
