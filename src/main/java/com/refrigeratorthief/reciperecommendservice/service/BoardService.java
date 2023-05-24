package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.CategoryRepository;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        Category targetCategory = categoryRepository.findByName(boardAddServiceRequestDto.getCategory().getName())
                .orElseThrow(()->new CustomException("해당하는 카테고리가 존재하지 않습니다."));
        User targetUser = userRepository.findById(boardAddServiceRequestDto.getUser().getId())
                .orElseThrow(()->new CustomException("해당하는 id가 존재하지 않습니다."));

        Board targetBoard = Board.builder()
                .title(boardAddServiceRequestDto.getTitle())
                .content(boardAddServiceRequestDto.getContent())
                .img(boardAddServiceRequestDto.getImg())
                .type(boardAddServiceRequestDto.getType())
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .category(targetCategory)
                .user(targetUser)
                .build();

        boardRepository.save(targetBoard);

        return BoardAddServiceResponseDto.builder()
                .id(targetBoard.getId())
                .title(targetBoard.getTitle())
                .content(targetBoard.getContent())
                .img(targetBoard.getImg())
                .type(targetBoard.getType())
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .category(targetBoard.getCategory().getName())
                .user(targetBoard.getUser().getName())
                .build();
    }

    // 게시글 상세 정보 조회
    @Transactional(readOnly = true)
    public BoardServiceResponseDto getBoard(Integer id) {

        Board targetBoard = boardRepository.findById(id)
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));

        return BoardServiceResponseDto.builder()
                .id(targetBoard.getId())
                .title(targetBoard.getTitle())
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
        Board targetBoard = boardRepository.findById(boardUpdateServiceRequestDto.getId())
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));
        Category targetCategory = categoryRepository.findByName(boardUpdateServiceRequestDto.getCategory().getName())
                .orElseThrow(()->new CustomException("해당하는 카테고리가 존재하지 않습니다."));
        User targetUser = userRepository.findById(boardUpdateServiceRequestDto.getUser().getId())
                .orElseThrow(()->new CustomException("해당하는 id가 존재하지 않습니다."));

        Board resultBoard = Board.builder()
                .id(targetBoard.getId())
                .title(boardUpdateServiceRequestDto.getTitle())
                .content(boardUpdateServiceRequestDto.getContent())
                .img(boardUpdateServiceRequestDto.getImg())
                .type(boardUpdateServiceRequestDto.getType())
                .createdDateTime(boardUpdateServiceRequestDto.getCreatedDateTime())
                .updatedDateTime(LocalDateTime.now())
                .category(targetCategory)
                .user(targetUser)
                .build();

        targetBoard.updateBoard(resultBoard);

        return BoardUpdateServiceResponseDto.builder()
                .id(resultBoard.getId())
                .title(resultBoard.getTitle())
                .content(resultBoard.getContent())
                .img(resultBoard.getImg())
                .type(resultBoard.getType())
                .createdDateTime(resultBoard.getCreatedDateTime())
                .updatedDateTime(LocalDateTime.now())
                .category(resultBoard.getCategory().getName())
                .user(resultBoard.getUser().getName())
                .build();
    }

    // 게시글 삭제
    @Transactional
    public BoardDeleteServiceResponseDto deleteBoard(Integer id) {
        if (boardRepository.findById(id).isEmpty()) {
            throw new CustomException("해당하는 게시글이 존재하지 않습니다.");
        }

        Board targetBoard = boardRepository.findById(id)
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));
        boardRepository.delete(targetBoard);

        return BoardDeleteServiceResponseDto.builder()
                .id(targetBoard.getId())
                .build();
    }

    // 카테고리별 조회
    @Transactional(readOnly = true)
    public List<BoardServiceResponseDto> findBoardsByCategory(Integer id) {
        List<Board> targetBoards = boardRepository.findBoardsByCategory(id)
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));
        List<BoardServiceResponseDto> dtoList = new ArrayList<>();

        for (Board boards : targetBoards) {
            BoardServiceResponseDto dto = BoardServiceResponseDto.builder()
                    .id(boards.getId())
                    .title(boards.getTitle())
                    .content(boards.getContent())
                    .img(boards.getImg())
                    .type(boards.getType())
                    .createdDateTime(boards.getCreatedDateTime())
                    .updatedDateTime(boards.getUpdatedDateTime())
                    .category(boards.getCategory().getName())
                    .user(boards.getUser().getName())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }

}
