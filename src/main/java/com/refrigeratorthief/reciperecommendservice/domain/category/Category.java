package com.refrigeratorthief.reciperecommendservice.domain.category;

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
@Entity(name = "category")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_IDX")
    private Integer id;

    @Column(name = "CATEGORY_NM", length = 10, unique = true, nullable = false)
    private String name;
}
