package com.refrigeratorthief.reciperecommendservice.domain;

import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "REFRIGERATOR")
public class Refrigerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFRIGERATOR_IDX")
    private Integer id;

    @Column(name = "REFRIGERATOR_EXPIRATION_DATE", nullable = false)
    private Date expirationDate;

    @Column(name = "REFRIGERATOR_QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "REFRIGERATOR_STORE_LOCATION", length = 2, nullable = false)
    private String location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INGREDIENT_INGREDIENT_IDX", nullable = false)
    private Ingredient ingredient;

}
