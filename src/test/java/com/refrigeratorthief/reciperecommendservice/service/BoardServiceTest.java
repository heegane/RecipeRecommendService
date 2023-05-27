package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.category.CategoryRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.utils.CustomException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
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
                .category(testUtils.getTestBoard().getCategory())
                .user(testUtils.getTestBoard().getUser())
                .build();

        Category testCategoryEntity = testUtils.getTestCategory();
        User testUserEntity = testUtils.getTestUser2();
        Board testBoardEntity = boardAddServiceRequestDto.toEntity(testCategoryEntity,testUserEntity);

        doReturn(Optional.of(testCategoryEntity)).when(categoryRepository).findById(anyInt());
        doReturn(Optional.of(testUserEntity)).when(userRepository).findById(anyString());
        doReturn(testBoardEntity).when(boardRepository).save(any(Board.class));

        //when
        BoardServiceResponseDto result = boardService.addBoard(boardAddServiceRequestDto);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, Objects.requireNonNull(result));
        verify(categoryRepository).findById(anyInt());
        verify(userRepository).findById(anyString());
        verify(boardRepository).save(any(Board.class));
    }

    @Test
    void notMatchingCategoryErrorWhenAddBoard() {
        //given
        Category testCategory = testUtils.getTestCategory();
        testCategory.setId(5);

        BoardAddServiceRequestDto boardAddServiceRequestDto = BoardAddServiceRequestDto
                .builder()
                .title(testUtils.getTestBoard().getTitle())
                .content(testUtils.getTestBoard().getContent())
                .img(testUtils.getTestBoard().getImg())
                .type("자유")
                .category(testCategory)
                .user(testUtils.getTestUser())
                .build();

        doReturn(Optional.of(testUtils.getTestCategory())).when(categoryRepository).findById(anyInt());
        doReturn(Optional.of(testUtils.getTestUser())).when(userRepository).findById(anyString());

        //when
        CustomException result = assertThrows(CustomException.class, ()-> boardService.addBoard(boardAddServiceRequestDto));

        // then
        assertEquals("해당 카테고리를 고를 수 없습니다. (자유 게시판은 1. 음식자랑, 2. 일상, 3. 질문글, 4. 기타만 가능)",result.getMessage());
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

        doReturn(Optional.of(targetBoard)).when(boardRepository).findById(anyInt());
        doReturn(Optional.of(testUtils.getTestCategory())).when(categoryRepository).findById(anyInt());
        doReturn(Optional.of(testUtils.getTestUser2())).when(userRepository).findById(anyString());

        BoardUpdateServiceRequestDto boardUpdateServiceRequestDto = BoardUpdateServiceRequestDto
                .builder()
                .id(targetBoard.getId())
                .title("수정된 타이틀")
                .content(targetBoard.getContent())
                .img(targetBoard.getImg())
                .type(targetBoard.getType())
                .category(targetBoard.getCategory())
                .user(targetBoard.getUser())
                .build();

        //when
        BoardUpdateServiceResponseDto result = boardService.updateBoard(boardUpdateServiceRequestDto);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("수정된 타이틀", result.getTitle());
        verify(boardRepository).findById(anyInt());
        verify(categoryRepository).findById(anyInt());
        verify(userRepository).findById(anyString());
    }

    @Test
    void notMatchingCategoryErrorWhenUpdateBoard() {
        //given
        Category testCategory = testUtils.getTestCategory();
        testCategory.setId(5);

        BoardUpdateServiceRequestDto boardUpdateServiceRequestDto = BoardUpdateServiceRequestDto
                .builder()
                .id(testUtils.getTestBoard().getId())
                .title(testUtils.getTestBoard().getTitle())
                .content(testUtils.getTestBoard().getContent())
                .img(testUtils.getTestBoard().getImg())
                .type("자유")
                .user(testUtils.getTestBoard().getUser())
                .category(testCategory)
                .build();

        doReturn(Optional.of(testUtils.getTestBoard())).when(boardRepository).findById(anyInt());
        doReturn(Optional.of(testUtils.getTestCategory())).when(categoryRepository).findById(anyInt());
        doReturn(Optional.of(testUtils.getTestUser())).when(userRepository).findById(anyString());

        //when
        CustomException result = assertThrows(CustomException.class, ()-> boardService.updateBoard(boardUpdateServiceRequestDto));

        // then
        assertEquals("해당 카테고리를 고를 수 없습니다. (자유 게시판은 1. 음식자랑, 2. 일상, 3. 질문글, 4. 기타만 가능)",result.getMessage());
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