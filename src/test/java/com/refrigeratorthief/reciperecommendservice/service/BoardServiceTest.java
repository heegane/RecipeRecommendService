package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardAddServiceRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardServiceResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void addBoard() {
//        //given
//        BoardAddServiceRequestDto boardAddServiceRequestDto = BoardAddServiceRequestDto
//                .builder()
//                .title("제목")
//                .content("내용")
//                .img("이미지")
//                .type("거래")
//                .createdDateTime(LocalDateTime.now())
//                .updatedDateTime(LocalDateTime.now())
//                .category("살게요")
//                .user("Choi")
//                .build();
//
//        //when
//        boardService.addBoard(boardAddServiceRequestDto);
//
//        //then
    }

    @Test
    void getBoard() {
    }

    @Test
    void updateBoard() {
    }

    @Test
    void deleteBoard() {
    }

    @Test
    void findBoardsByCategory() {
    }
}