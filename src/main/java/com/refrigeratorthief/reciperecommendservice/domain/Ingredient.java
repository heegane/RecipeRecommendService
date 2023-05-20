package com.refrigeratorthief.reciperecommendservice.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "INGREDIENT")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INGREDIENT_IDX")
    private int id;

    @Column(name = "INGREDIENT_NM", length = 45, nullable = false, unique = true)
    private String name;

    @Column(name = "INGREDIENT_IMG", length = 100)
    private String img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INGREDIENT_UNIT_INGREDIENT_UNIT_IDX", nullable = false)
    private IngredientUnit unit;
}
