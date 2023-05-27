package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardAddControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardAddControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardUpdateControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.dto.general.MessageDto;
import com.refrigeratorthief.reciperecommendservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@RestController
public class BoardController {

    private final BoardService boardService;

    // 게시글 저장
    @PostMapping()
    public ResponseEntity<BoardAddControllerResponseDto> addBoard(@RequestBody BoardAddControllerRequestDto boardAddControllerRequestDto) {
        BoardAddServiceResponseDto boardAddServiceResponseDto = boardService.addBoard(boardAddControllerRequestDto.toServiceDto(boardAddControllerRequestDto));
        return ResponseEntity.ok(boardAddServiceResponseDto.toControllerDto(boardAddServiceResponseDto));
    }

    // 게시글 상세 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardControllerResponseDto> findById(@PathVariable Integer id) {
        BoardServiceResponseDto boardServiceResponseDto = boardService.getBoard(id);
        return ResponseEntity.ok(boardServiceResponseDto.toControllerDto(boardServiceResponseDto));
    }

    // 게시글 수정
    @PutMapping()
    public ResponseEntity<MessageDto> updateBoard(@RequestBody BoardUpdateControllerRequestDto boardUpdateControllerRequestDto) {
        BoardUpdateServiceResponseDto boardUpdateServiceResponseDto = boardService.updateBoard(boardUpdateControllerRequestDto.toServiceDto(boardUpdateControllerRequestDto));
        return ResponseEntity.ok(new MessageDto("해당 게시글을 성공적으로 수정했습니다."));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteBoard(@PathVariable Integer id) {
        BoardDeleteServiceResponseDto boardDeleteServiceResponseDto = boardService.deleteBoard(id);
        return ResponseEntity.ok(new MessageDto("해당 게시글을 성공적으로 삭제했습니다."));
    }

    // 카테고리별 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BoardServiceResponseDto>> findBoardsByCategory(@PathVariable Integer categoryId) {
        List<BoardServiceResponseDto> boardServiceResponseDtoList = boardService.findBoardsByCategory(categoryId);
        return ResponseEntity.ok(boardServiceResponseDtoList);
    }

}
