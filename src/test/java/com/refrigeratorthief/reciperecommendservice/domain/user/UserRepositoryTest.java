package com.refrigeratorthief.reciperecommendservice.domain.user;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
public class UserRepositoryTest {

    private final TestUtils testUtils = new TestUtils();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void findUserById(){

        //given
        User testGoal = testUtils.getTestUser();

        //when
        User result = userRepository.findUserById(testGoal.getId())
                .orElseThrow(IllegalAccessError::new);

        //then
        assertEquals(result.toString(), testGoal.toString());

    }

    @Test
    public void save(){

        //given
        User targetUser = testUtils.getTestUser();
        targetUser.setId("TEST");
        targetUser.setName("테스트2");

        //when
        User result = userRepository.save(targetUser);

        //then
        assertEquals("TEST",result.getId());
        assertEquals("테스트2",result.getName());
        assertEquals(targetUser.getPw(),result.getPw());
        assertEquals(targetUser.getCity(),result.getCity());
        assertEquals(targetUser.getDong(), result.getDong());

    }

    @Test
    public void delete(){

        //given
        User testGoal = testUtils.getTestUser();

        //when
        userRepository.delete(testGoal);

        //then
        assertThat(testEntityManager.find(User.class,testGoal.getId()),is(nullValue()));

    }

    @Test
    public void checkDuplicatedId(){

        //given
        User testUser = testUtils.getTestUser();

        //when
        boolean result = userRepository.existsUserById(testUser.getId());

        //then
        Assertions.assertTrue(result);

    }

    @Test
    public void checkDuplicatedName(){

        //given
        User testUser = testUtils.getTestUser();

        //when
        boolean result = userRepository.existsUserByName(testUser.getName());

        //then
        Assertions.assertTrue(result);

    }
}
