package com.refrigeratorthief.reciperecommendservice.domain.category;

import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void save() {
        //given
        Category category = new Category(10, "사요");

        //when
        Category testCategory = categoryRepository.save(category);

        //then
        assertEquals("사요",testCategory.getName());
    }

    @Test
    void findByName() {
        //given
        Category category = new Category(10, "사요");
        Category testCategory = categoryRepository.save(category);

        //then
        Category result = categoryRepository.findByName(testCategory.getName()).orElseThrow();

        //when
        Assertions.assertEquals(testCategory.getName(), result.getName());
    }
}