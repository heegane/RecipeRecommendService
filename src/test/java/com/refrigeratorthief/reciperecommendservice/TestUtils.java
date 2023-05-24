package com.refrigeratorthief.reciperecommendservice;

import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Component
public class TestUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LocalDate testDate;
    private final LocalDate testDate2;
    private final User testUser;
    private final LocalDateTime testDateTime = LocalDateTime.parse("2023-05-20 14:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final LocalDateTime testDateTime2 = LocalDateTime.parse("2022-05-30 12:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final IngredientUnit testIngredientUnit;
    private final IngredientUnit testIngredientUnit2;
    private final Ingredient testIngredient;
    private final Ingredient testIngredient2;
    public TestUtils(){

        testDate = LocalDate.of(2023,2,28);
        testDate2 = LocalDate.of(2024,7,30);
        testUser = User.builder()
                .id("erica")
                .name("하냥이")
                .pw("erica")
                .city("경기도 안산시")
                .dong("상록구")
                .build();
        testIngredientUnit = IngredientUnit.builder()
                .id(1)
                .name("개")
                .build();
        testIngredientUnit2 = IngredientUnit.builder()
                .id(2)
                .name("근")
                .build();
        testIngredient = Ingredient.builder()
                .id(1)
                .name("당근")
                .img("/images/carrot.jpg")
                .unit(testIngredientUnit)
                .build();
        testIngredient2 = Ingredient.builder()
                .id(2)
                .name("소고기")
                .img("/images/beef.jpg")
                .unit(testIngredientUnit2)
                .build();
    }
}
