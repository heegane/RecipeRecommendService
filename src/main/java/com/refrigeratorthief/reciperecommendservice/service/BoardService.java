package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.User.User;
import com.refrigeratorthief.reciperecommendservice.domain.User.UserRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.CategoryRepository;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final UserRepository userRepository;

    // 게시글 저장
    @Transactional
    public BoardAddServiceResponseDto addBoard(BoardAddServiceRequestDto boardAddServiceRequestDto) {
        Category targetCategory = categoryRepository.findByName(boardAddServiceRequestDto.getCategory()).orElseThrow(NullPointerException::new);
        User targetUser = userRepository.findById(boardAddServiceRequestDto.getUser()).orElseThrow(NullPointerException::new);

        Board targetBoard = Board.builder()
                .content(boardAddServiceRequestDto.getContent())
                .img(boardAddServiceRequestDto.getImg())
                .type(boardAddServiceRequestDto.getType())
                .createdDateTime(boardAddServiceRequestDto.getCreatedDateTime())
                .updatedDateTime(boardAddServiceRequestDto.getUpdatedDateTime())
                .category(targetCategory)
                .user(targetUser)
                .build();

        boardRepository.save(targetBoard);

        return BoardAddServiceResponseDto.builder()
                .id(targetBoard.getId())
                .build();

    }

    // 게시글 상세 정보 조회
    @Transactional(readOnly = true)
    public BoardServiceResponseDto getBoard(Integer id) {

        Board targetBoard = boardRepository.findById(id).orElseThrow(NullPointerException::new);

        return BoardServiceResponseDto.builder()
                .id(targetBoard.getId())
                .content(targetBoard.getContent())
                .img(targetBoard.getImg())
                .type(targetBoard.getType())
                .createdDateTime(targetBoard.getCreatedDateTime())
                .updatedDateTime(targetBoard.getUpdatedDateTime())
                .category(targetBoard.getCategory().getName())
                .user(targetBoard.getUser().getName())
                .build();
    }

    // 게시글 수정
    @Transactional
    public BoardUpdateServiceResponseDto updateBoard(BoardUpdateServiceRequestDto boardUpdateServiceRequestDto) {
        Board targetBoard = boardRepository.findById(boardUpdateServiceRequestDto.getId()).orElseThrow(NullPointerException::new);
        Category targetCategory = categoryRepository.findByName(boardUpdateServiceRequestDto.getCategory()).orElseThrow(NullPointerException::new);
        User targetUser = userRepository.findById(boardUpdateServiceRequestDto.getUser()).orElseThrow(NullPointerException::new);
        Board resultBoard = Board.builder()
                .id(targetBoard.getId())
                .content(boardUpdateServiceRequestDto.getContent())
                .img(boardUpdateServiceRequestDto.getImg())
                .type(boardUpdateServiceRequestDto.getType())
                .category(targetCategory)
                .user(targetUser)
                .build();
        targetBoard.updateBoard(resultBoard);

        return BoardUpdateServiceResponseDto.builder()
                .id(resultBoard.getId())
                .content(resultBoard.getContent())
                .img(resultBoard.getImg())
                .type(resultBoard.getType())
                .category(resultBoard.getCategory().getName())
                .user(resultBoard.getUser().getId())
                .build();
    }

    // 게시글 삭제
    @Transactional
    public BoardDeleteServiceResponseDto deleteBoard(Integer id) {
        if (boardRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("해당하는 idx값을 가진 게시글이 존재하지 않습니다.");
        }

        Board targetBoard = boardRepository.findById(id).orElseThrow(NullPointerException::new);
        boardRepository.delete(targetBoard);

        return BoardDeleteServiceResponseDto.builder()
                .id(targetBoard.getId())
                .build();
    }

    // 카테고리별 조회
    @Transactional(readOnly = true)
    public List<BoardServiceResponseDto> findBoardsByCategory(Integer id) {
        List<Board> targetBoards = boardRepository.findBoardsByCategory(id).orElseThrow(NullPointerException::new);
        List<BoardServiceResponseDto> dtoList = new ArrayList<>();

        for (Board boards : targetBoards) {
            BoardServiceResponseDto dto = BoardServiceResponseDto.builder()
                    .id(boards.getId())
                    .content(boards.getContent())
                    .img(boards.getImg())
                    .type(boards.getType())
                    .category(boards.getCategory().getName())
                    .user(boards.getUser().getId())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }

}
