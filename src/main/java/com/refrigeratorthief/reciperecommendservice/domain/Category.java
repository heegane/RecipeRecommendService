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
@Entity(name = "CATEGORY")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_IDX")
    private int id;

    @Column(name = "CATEGORY_NM", length = 10, unique = true, nullable = false)
    private String name;
}
