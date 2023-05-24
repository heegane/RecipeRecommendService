package com.refrigeratorthief.reciperecommendservice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Component
public class TestUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LocalDate testDate;
    private final LocalDate testDate2;
    private final User testUser;
    private final User testUser2;
    private final LocalDateTime testDateTime = LocalDateTime.parse("2023-05-05 11:11:11", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    private final LocalDateTime testDateTime2 = LocalDateTime.parse("2023-05-20 23:13:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final LocalDateTime testDateTime3 = LocalDateTime.parse("2022-05-05 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    private final Category testCategory;
    private final Board testBoard;

    private final IngredientUnit testIngredientUnit;
    private final Ingredient testIngredient;
    private final Refrigerator testRef;

    public TestUtils() {

        testDate = LocalDate.of(2023,2,28);
        testDate2 = LocalDate.of(2024,7,30);
        Date testDate4 = java.sql.Timestamp.valueOf(testDateTime3);

        testUser = User.builder()
                .id("erica")
                .name("하냥이")
                .pw("erica")
                .city("경기도 안산시")
                .dong("상록구")
                .build();
        testUser2 = User.builder()
                .id("choiyun")
                .name("윤이")
                .pw("123")
                .city("시흥시")
                .dong("정왕동")
                .build();
        testCategory = Category.builder()
                .id(1)
                .name("팔게요")
                .build();
        testBoard = Board.builder()
                .id(2)
                .title("수정")
                .content("내용")
                .img("112233")
                .type("거래")
                .createdDateTime(testDateTime)
                .updatedDateTime(testDateTime2)
                .category(testCategory)
                .user(testUser2)
                .build();

        testIngredientUnit = IngredientUnit.builder()
                .id(5)
                .name("개")
                .build();

        testIngredient = Ingredient.builder()
                .id(1)
                .name("당근")
                .unit(testIngredientUnit)
                .build();

        testRef = Refrigerator.builder()
                .id(1)
                .expirationDate(testDate4)
                .quantity(3)
                .location("정왕")
                .user(testUser2)
                .ingredient(testIngredient)
                .build();
    }
}
