package com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
public class IngredientUnitRepositoryTest {

    private final TestUtils testUtils = new TestUtils();

    @Autowired
    private IngredientUnitRepository ingredientUnitRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findIngredientUnitById() {
        //given
        IngredientUnit testGoal = testUtils.getTestIngredientUnit();


        //when
        IngredientUnit result = ingredientUnitRepository.findIngredientUnitById(testGoal.getId())
                .orElseThrow(IllegalAccessError::new);

        //then
        assertEquals(result.toString(), testGoal.toString());
    }
}
