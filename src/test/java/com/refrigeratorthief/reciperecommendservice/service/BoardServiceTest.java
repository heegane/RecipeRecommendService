package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.category.CategoryRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import org.junit.jupiter.api.*;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ComponentScan
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @InjectMocks
    private BoardService boardService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BoardRepository boardRepository;

    private final TestUtils testUtils = new TestUtils();

    @Test
    void addBoard() {
        //given
        BoardAddServiceRequestDto boardAddServiceRequestDto = BoardAddServiceRequestDto
                .builder()
                .title(testUtils.getTestBoard().getTitle())
                .content(testUtils.getTestBoard().getContent())
                .img(testUtils.getTestBoard().getImg())
                .type(testUtils.getTestBoard().getType())
                .createdDateTime(testUtils.getTestBoard().getCreatedDateTime())
                .updatedDateTime(testUtils.getTestBoard().getUpdatedDateTime())
                .category(testUtils.getTestBoard().getCategory())
                .user(testUtils.getTestBoard().getUser())
                .build();

        Board testBoardEntity = boardAddServiceRequestDto.toEntity();
        Category testCategoryEntity = testUtils.getTestCategory();
        User testUserEntity = testUtils.getTestUser2();

        doReturn(Optional.of(testCategoryEntity)).when(categoryRepository).findByName(any());
        doReturn(Optional.of(testUserEntity)).when(userRepository).findById(any());
        doReturn(testBoardEntity).when(boardRepository).save(any());

        //when
        BoardAddServiceResponseDto result = boardService.addBoard(boardAddServiceRequestDto);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, Objects.requireNonNull(result));
        verify(boardRepository).save(any());
    }

    @Test
    void getBoard() {
        //given
        Board targetBoard = testUtils.getTestBoard();
        doReturn(Optional.of(targetBoard)).when(boardRepository).findById(targetBoard.getId());

        //when
        BoardServiceResponseDto result = boardService.getBoard(targetBoard.getId());

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.toString(), Objects.requireNonNull(result).toString());
        verify(boardRepository).findById(targetBoard.getId());
    }

    @Test
    void updateBoard() {
        //given
        Board targetBoard = testUtils.getTestBoard();

        doReturn(Optional.of(testUtils.getTestCategory())).when(categoryRepository).findByName(any());
        doReturn(Optional.of(testUtils.getTestUser2())).when(userRepository).findById(any());
        doReturn(Optional.of(targetBoard)).when(boardRepository).findById(targetBoard.getId());

        BoardUpdateServiceRequestDto boardUpdateServiceRequestDto = BoardUpdateServiceRequestDto
                .builder()
                .id(targetBoard.getId())
                .title("수정된 타이틀")
                .content(targetBoard.getContent())
                .img(targetBoard.getImg())
                .type(targetBoard.getType())
                .updatedDateTime(targetBoard.getUpdatedDateTime())
                .category(targetBoard.getCategory())
                .user(targetBoard.getUser())
                .build();

        //when
        BoardUpdateServiceResponseDto result = boardService.updateBoard(boardUpdateServiceRequestDto);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("수정된 타이틀", result.getTitle());
        verify(categoryRepository).findByName(any());
        verify(userRepository).findById(any());
        verify(boardRepository).findById(targetBoard.getId());
    }

    @Test
    void deleteBoard() {
        //given
        Board targetBoard = testUtils.getTestBoard();
        doReturn(Optional.of(targetBoard)).when(boardRepository).findById(targetBoard.getId());

        //when
        BoardDeleteServiceResponseDto result = boardService.deleteBoard(targetBoard.getId());

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(targetBoard.getId(), Objects.requireNonNull(result.getId()));
        verify(boardRepository).delete(targetBoard);
    }

    @Test
    void findBoardsByCategory() {
        //given
        Board targetBoard = testUtils.getTestBoard();
        List<Board> targetBoards = new ArrayList<>();

        doReturn(Optional.of(targetBoards)).when(boardRepository).findBoardsByCategory(targetBoard.getCategory().getId());

        //when
        List<BoardServiceResponseDto> targetLists = boardService.findBoardsByCategory(targetBoard.getCategory().getId());

        //then
        Assertions.assertNotNull(targetLists);
        Assertions.assertEquals(targetLists.toString(), Objects.requireNonNull(targetLists).toString());
        verify(boardRepository).findBoardsByCategory(targetBoard.getCategory().getId());
    }
}