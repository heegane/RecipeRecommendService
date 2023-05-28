package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.category.CategoryRepository;
import com.refrigeratorthief.reciperecommendservice.domain.comment.Comment;
import com.refrigeratorthief.reciperecommendservice.domain.comment.CommentRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentAddServiceRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentUpdateServiceRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ComponentScan
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private CommentRepository commentRepository;

    private final TestUtils testUtils = new TestUtils();

    @Test
    void addComment() {
        //given
        CommentAddServiceRequestDto commentAddServiceRequestDto = CommentAddServiceRequestDto
                .builder()
                .upperId(testUtils.getTestComment().getUpperId())
                .content(testUtils.getTestComment().getContent())
                .user(testUtils.getTestComment().getUser())
                .board(testUtils.getTestComment().getBoard())
                .build();

        User testUserEntity = testUtils.getTestUser2();
        Board testBoardEntity = testUtils.getTestBoard();
        Comment testCommentEntity = commentAddServiceRequestDto.toEntity(testUserEntity, testBoardEntity);

        doReturn(Optional.of(testUserEntity)).when(userRepository).findUserById(anyString());
        doReturn(Optional.of(testBoardEntity)).when(boardRepository).findById(anyInt());
        doReturn(testCommentEntity).when(commentRepository).save(any(Comment.class));

        //when
        CommentServiceResponseDto result = commentService.addComment(commentAddServiceRequestDto);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, Objects.requireNonNull(result));
        verify(userRepository).findUserById(anyString());
        verify(boardRepository).findById(anyInt());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void updateComment() {
        //given
        Comment targetComment = testUtils.getTestComment();

        doReturn(Optional.of(targetComment)).when(commentRepository).findById(anyInt());

        CommentUpdateServiceRequestDto commentUpdateServiceRequestDto = CommentUpdateServiceRequestDto
                .builder()
                .id(targetComment.getId())
                .content("수정된 콘텐츠")
                .user(targetComment.getUser())
                .build();

        //when
        commentService.updateComment(commentUpdateServiceRequestDto);

        //then
        verify(commentRepository).findById(anyInt());
    }

    @Test
    void deleteComment() {
        //given
        Comment targetComment = testUtils.getTestComment();
        doReturn(Optional.of(targetComment)).when(commentRepository).findById(targetComment.getId());

        //when
        commentService.deleteComment(targetComment.getId());

        //then
        verify(commentRepository).findById(targetComment.getId());
        verify(commentRepository).delete(targetComment);
    }

    @Test
    void findCommentsByBoard() {
        //given
        Comment targetComment = testUtils.getTestComment();
        List<Comment> targetComments = new ArrayList<>();
        doReturn(Optional.of(targetComments)).when(commentRepository).findCommentsByBoard(targetComment.getBoard().getId());

        //when
        List<CommentServiceResponseDto> targetLists = commentService.findCommentsByBoard(targetComment.getBoard().getId());

        //then
        Assertions.assertNotNull(targetLists);
        Assertions.assertEquals(targetLists.toString(), Objects.requireNonNull(targetLists).toString());
        verify(commentRepository).findCommentsByBoard(targetComment.getBoard().getId());
    }
}