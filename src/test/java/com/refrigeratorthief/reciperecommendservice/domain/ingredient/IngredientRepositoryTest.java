package com.refrigeratorthief.reciperecommendservice.domain.ingredient;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class IngredientRepositoryTest {

    private final TestUtils testUtils = new TestUtils();
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void findById() {
        //given
        Ingredient testIng = testUtils.getTestIngredient();

        //when
        Ingredient result = ingredientRepository.findById(testIng.getId())
                .orElseThrow(IllegalAccessError::new);

        //then
        assertEquals(result.toString(), testIng.toString());
    }

    @Test
    void existsIngredientByName() {
        //given
        Ingredient testIngredient = testUtils.getTestIngredient();

        //when
        boolean result = ingredientRepository.existsIngredientByName(testIngredient.getName());

        //then
        Assertions.assertTrue(result);
    }

    @Test
    void save() {
        //given
        Ingredient targetIngredient = testUtils.getTestIngredient();
        targetIngredient.setName("마라탕 소스");

        //when
        Ingredient result = ingredientRepository.save(targetIngredient);

        //then
        assertEquals("마라탕 소스",result.getName());
        assertEquals(targetIngredient.getImg(),result.getImg());
        assertEquals(targetIngredient.getUnit().getId(), result.getUnit().getId());
        assertEquals(targetIngredient.getUnit().getName(), result.getUnit().getName());
    }

    @Test
    void findAllBy() {
        //given
        List<Ingredient> testGoal = new ArrayList<>();
        testGoal.add(testUtils.getTestIngredient());
        testGoal.add(testUtils.getTestIngredient2());

        //when
        List<Ingredient> results = ingredientRepository.findAllBy()
                .orElseThrow(IllegalArgumentException::new);

        //then
        assertThat(testUtils.isListSame(testGoal,results),is(true));
    }
}