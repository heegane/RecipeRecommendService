package com.refrigeratorthief.reciperecommendservice.domain.board;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.category.CategoryRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class BoardRepositoryTest {
    private final TestUtils testUtils = new TestUtils();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void save() {
        //given
        Board targetBoard = testUtils.getTestBoard();

        //when
        Board result = boardRepository.save(targetBoard);

        //then
        assertEquals(targetBoard.getTitle(), result.getTitle());
        assertEquals(targetBoard.getContent(),result.getContent());
        assertEquals(targetBoard.getImg(),result.getImg());
        assertEquals(targetBoard.getType(),result.getType());
        assertEquals(targetBoard.getCategory().getName(),result.getCategory().getName());
        assertEquals(targetBoard.getUser().getId(),result.getUser().getId());
    }

    @Test
    void delete() {
        //given
        Category category = new Category(10, "사요");
        Category testCategory = categoryRepository.save(category);
        User user = new User("testId", "yun", "123", "사랑시 고백구", "행복동");
        User testUser = userRepository.save(user);
        Board board = new Board(1, "제목", "내용", "img", "거래", LocalDateTime.now(), LocalDateTime.now(), testCategory, testUser);
        Board testBoard = boardRepository.save(board);

        //when
        boardRepository.delete(testBoard);

        //then
        Assertions.assertEquals(Optional.empty(), boardRepository.findById(testBoard.getId()));
    }

    @Test
    void findById() {
        //given
        Category category = new Category(10, "사요");
        Category testCategory = categoryRepository.save(category);
        User user = new User("testId", "yun", "123", "사랑시 고백구", "행복동");
        User testUser = userRepository.save(user);
        Board board = new Board(1, "제목", "내용", "img", "거래", LocalDateTime.now(), LocalDateTime.now(), testCategory, testUser);
        Board testBoard = boardRepository.save(board);

        //then
        Board result = boardRepository.findById(testBoard.getId()).orElseThrow();

        //when
        Assertions.assertEquals(testBoard.getId(), result.getId());
    }

    @Test
    void findBoardsByCategory() {
        //given
        Category category = new Category(10, "사요");
        Category testCategory = categoryRepository.save(category);
        User user = new User("testId", "yun", "123", "사랑시 고백구", "행복동");
        User testUser = userRepository.save(user);
        Board board = new Board(1, "제목", "내용", "img", "거래", LocalDateTime.now(), LocalDateTime.now(), testCategory, testUser);
        Board testBoard = boardRepository.save(board);

        //then
        List<Board> results = boardRepository.findBoardsByCategory(testBoard.getCategory().getId()).orElseThrow();

        //when
        assertThat(results.get(0).getTitle()).isEqualTo("제목");
        assertThat(results.get(0).getContent()).isEqualTo("내용");
        assertThat(results.get(0).getImg()).isEqualTo("img");
        assertThat(results.get(0).getType()).isEqualTo("거래");
        assertThat(results.get(0).getCategory()).isEqualTo(testCategory);
        assertThat(results.get(0).getUser()).isEqualTo(testUser);
    }
}