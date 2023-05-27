package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.*;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.dto.general.MessageDto;
import com.refrigeratorthief.reciperecommendservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    // 게시글 저장
    @PostMapping()
    public ResponseEntity<BoardControllerResponseDto> addBoard(@RequestBody BoardAddControllerRequestDto boardAddControllerRequestDto) {
        BoardServiceResponseDto boardServiceResponseDto = boardService.addBoard(boardAddControllerRequestDto.toServiceDto());
        return ResponseEntity.ok(boardServiceResponseDto.toControllerDto());
    }

    // 게시글 상세 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardControllerResponseDto> findById(@PathVariable Integer id) {
        BoardServiceResponseDto boardServiceResponseDto = boardService.getBoard(id);
        return ResponseEntity.ok(boardServiceResponseDto.toControllerDto());
    }

    // 게시글 수정
    @PutMapping()
    public ResponseEntity<MessageDto> updateBoard(@RequestBody BoardUpdateControllerRequestDto boardUpdateControllerRequestDto) {
        boardService.updateBoard(boardUpdateControllerRequestDto.toServiceDto());
        return ResponseEntity.ok(new MessageDto("해당 게시글을 성공적으로 수정했습니다."));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteBoard(@PathVariable Integer id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok(new MessageDto("해당 게시글을 성공적으로 삭제했습니다."));
    }

    // 카테고리별 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BoardCategoryResponseControllerDto>> findBoardsByCategory(@PathVariable Integer categoryId) {

        List<BoardCategoryResponseControllerDto> results;

        results = boardService.findBoardsByCategory(categoryId)
                .stream()
                .map(BoardServiceResponseDto::toCategoryControllerDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }
}
