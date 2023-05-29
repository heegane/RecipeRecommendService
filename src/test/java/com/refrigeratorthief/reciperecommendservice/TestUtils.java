package com.refrigeratorthief.reciperecommendservice;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.comment.Comment;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Getter
@Component
public class TestUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LocalDate testDate;
    private final LocalDate testDate2;

    private final User testUser;
    private final User testUser2;
    private final User testUser3;

    private final LocalDateTime testDateTime = LocalDateTime.parse("2023-05-05 11:11:11", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final LocalDateTime testDateTime2 = LocalDateTime.parse("2023-05-06 11:11:11", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final LocalDateTime testDateTime3 = LocalDateTime.parse("2022-05-05 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    private final Category testCategory;
    private final Board testBoard;
    private final Board testBoard2;

    private final IngredientUnit testIngredientUnit;
    private final IngredientUnit testIngredientUnit2;
    private final IngredientUnit testIngredientUnit3;

    private final Ingredient testIngredient;
    private final Ingredient testIngredient2;
    private final Ingredient testIngredient3;

    private final Refrigerator testRef;
    private final Refrigerator testRef2;
    private final Refrigerator testRef3;

    private final Comment testComment;
    private final Comment testComment2;
    private final Comment testComment3;

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
                .id("yun")
                .name("윤이")
                .pw("yun")
                .city("경기도 안산시")
                .dong("정왕동")
                .build();
        testUser3 = User.builder()
                .id("hyeon")
                .name("현희")
                .pw("hyeon")
                .city("경기도 안산시")
                .dong("고잔동")
                .build();

        testCategory = Category.builder()
                .id(1)
                .name("음식자랑")
                .build();

        testBoard = Board.builder()
                .id(1)
                .title("내가 만든 쿠키")
                .content("맛있겠쥐")
                .img("cookie.jpg")
                .type("자유")
                .createdDateTime(testDateTime)
                .updatedDateTime(testDateTime)
                .category(testCategory)
                .user(testUser2)
                .build();

        testBoard2 = Board.builder()
                .id(2)
                .title("참치 야채 비빔밥")
                .content("고추장 많이")
                .type("자유")
                .img("bibim.jpg")
                .createdDateTime(testDateTime)
                .updatedDateTime(testDateTime)
                .category(testCategory)
                .user(testUser)
                .build();

        testIngredientUnit = IngredientUnit.builder()
                .id(1)
                .name("개")
                .build();
        testIngredientUnit2 = IngredientUnit.builder()
                .id(2)
                .name("근")
                .build();
        testIngredientUnit3 = IngredientUnit.builder()
                .id(3)
                .name("알")
                .build();

        testIngredient = Ingredient.builder()
                .id(1)
                .name("당근")
                .img("https://fridgethief.s3.ap-northeast-2.amazonaws.com/ingredient/41836ff1-df5c-4d2d-ac1f-f77fed928869%E1%84%83%E1%85%A1%E1%86%BC%E1%84%80%E1%85%B3%E1%86%AB.png")
                .unit(testIngredientUnit)
                .build();
        testIngredient2 = Ingredient.builder()
                .id(2)
                .name("소고기")
                .img("https://fridgethief.s3.ap-northeast-2.amazonaws.com/ingredient/85836028-633f-49b0-be80-7cc43a8bd752%E1%84%89%E1%85%A1%E1%86%B7%E1%84%80%E1%85%A7%E1%86%B8%E1%84%89%E1%85%A1%E1%86%AF.png")
                .unit(testIngredientUnit2)
                .build();
        testIngredient3 = Ingredient.builder()
                .id(3)
                .name("감자")
                .img("https://fridgethief.s3.ap-northeast-2.amazonaws.com/ingredient/a8d0c3b3-63d6-48f4-85bc-c1317275fe64%E1%84%80%E1%85%A1%E1%86%B7%E1%84%8C%E1%85%A1.png")
                .unit(testIngredientUnit3)
                .build();

        testRef = Refrigerator.builder()
                .id(1)
                .expirationDate(testDate4)
                .quantity(5)
                .location("냉장")
                .user(testUser)
                .ingredient(testIngredient3)
                .build();
        testRef2 = Refrigerator.builder()
                .id(2)
                .expirationDate(testDate4)
                .quantity(5)
                .location("냉장")
                .user(testUser)
                .ingredient(testIngredient)
                .build();
        testRef3 = Refrigerator.builder()
                .id(3)
                .expirationDate(testDate4)
                .quantity(2)
                .location("냉동")
                .user(testUser)
                .ingredient(testIngredient2)
                .build();

        testComment = Comment.builder()
                .id(1)
                .upperId(null)
                .content("레시피 공유좀")
                .createdDateTime(testDateTime)
                .updatedDateTime(testDateTime)
                .user(testUser3)
                .board(testBoard)
                .build();
        testComment2 = Comment.builder()
                .id(2)
                .upperId(1)
                .content("기달 공유해드림")
                .createdDateTime(testDateTime2)
                .updatedDateTime(testDateTime2)
                .user(testUser2)
                .board(testBoard)
                .build();
        testComment3 = Comment.builder()
                .id(3)
                .upperId(null)
                .content("우왕 맛있겠다")
                .createdDateTime(testDateTime)
                .updatedDateTime(testDateTime)
                .user(testUser)
                .board(testBoard)
                .build();
    }

    public boolean isListSame(List<?> targetListA , List<?> targetListB){

        if(targetListA.size() != targetListB.size()) return false;
        for (int i = 0; i < targetListA.size(); i++) {
            try{
                targetListA.indexOf(targetListB.get(i));
            }catch (Exception e){
                logger.debug("{}",targetListA.get(i).toString());
                logger.debug("{}",targetListB.get(i).toString());
                return false;
            }
        }
        return true;
    }
}
