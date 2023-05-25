package com.refrigeratorthief.reciperecommendservice.domain.refrigerator;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class RefrigeratorRepositoryTest {

    private final TestUtils testUtils = new TestUtils();
    @Autowired
    private RefrigeratorRepository refrigeratorRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void save() {
        //given
        Refrigerator targetRef = testUtils.getTestRef();

        //when
        Refrigerator result = refrigeratorRepository.save(targetRef);

        //then
        assertEquals(targetRef.getId(),result.getId());
        assertEquals(targetRef.getExpirationDate(),result.getExpirationDate());
        assertEquals(targetRef.getUser().getId(),result.getUser().getId());
        assertEquals(targetRef.getLocation(),result.getLocation());
        assertEquals(targetRef.getQuantity(), result.getQuantity());
        assertEquals(targetRef.getIngredient().getId(), result.getIngredient().getId());
    }

    @Test
    void delete() {
        //given
        Refrigerator targetRef = testUtils.getTestRef();

        //when
        refrigeratorRepository.delete(targetRef);

        //then
        assertThat(testEntityManager.find(Refrigerator.class,targetRef.getId()),is(nullValue()));
    }

    @Test
    void findById() {
        //given
        Refrigerator testRef = testUtils.getTestRef();

        //when
        Refrigerator result = refrigeratorRepository.findById(testRef.getId())
                .orElseThrow(IllegalAccessError::new);

        //then
        assertEquals(result.toString(), testRef.toString());
    }

    @Test
    void findAllByUser() {
        //given
        User testUser = testUtils.getTestUser();

        List<Refrigerator> testGoal = new ArrayList<Refrigerator>();
        testGoal.add(testUtils.getTestRef2());
        testGoal.add(testUtils.getTestRef3());

        //when
        List<Refrigerator> results = refrigeratorRepository.findAllByUser(testUser)
                .orElseThrow(IllegalArgumentException::new);

        //then
        assertThat(testUtils.isListSame(testGoal,results),is(true));
    }
}