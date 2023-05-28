package com.refrigeratorthief.reciperecommendservice.domain.comment;

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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class CommentRepositoryTest {

    private final TestUtils testUtils = new TestUtils();
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void save() {
        //given
        Comment targetComment = testUtils.getTestComment();

        //when
        Comment result = commentRepository.save(targetComment);

        //then
        assertEquals(targetComment.getId(), result.getId());
        assertEquals(targetComment.getUpperId(), result.getUpperId());
        assertEquals(targetComment.getContent(), result.getContent());
        assertEquals(targetComment.getCreatedDateTime(), result.getCreatedDateTime());
        assertEquals(targetComment.getUpdatedDateTime(), result.getUpdatedDateTime());
        assertEquals(targetComment.getUser().getId(), result.getUser().getId());
        assertEquals(targetComment.getBoard().getId(), result.getBoard().getId());
    }

    @Test
    void delete() {
        //given
        Comment testComment = commentRepository.save(testUtils.getTestComment());

        //when
        commentRepository.delete(testComment);

        //then
        Assertions.assertEquals(Optional.empty(), commentRepository.findById(testComment.getId()));
    }

    @Test
    void findById() {
        //given
        Comment testComment = commentRepository.save(testUtils.getTestComment());

        //then
        Comment result = commentRepository.findById(testComment.getId()).orElseThrow();

        //when
        Assertions.assertEquals(testComment.getId(), result.getId());
    }

    @Test
    void findCommentsByBoard() {
        //given

        //when
        List<Comment> results = commentRepository.findCommentsByBoard(1).orElseThrow();

        //then
        assertThat(results.get(0).getId()).isEqualTo(1);
        assertThat(results.get(0).getUpperId()).isEqualTo(null);
        assertThat(results.get(0).getContent()).isEqualTo("레시피 공유좀");
        assertThat(results.get(0).getCreatedDateTime()).isEqualTo(testUtils.getTestDateTime());
        assertThat(results.get(0).getUpdatedDateTime()).isEqualTo(testUtils.getTestDateTime());
        assertThat(results.get(0).getUser().getId()).isEqualTo("hyeon");
        assertThat(results.get(0).getBoard().getId()).isEqualTo(1);
    }
}