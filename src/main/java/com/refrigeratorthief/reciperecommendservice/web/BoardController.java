package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientResponseControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientAddRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.*;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.dto.general.MessageDto;
import com.refrigeratorthief.reciperecommendservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @PostMapping()
    public ResponseEntity<BoardControllerResponseDto> addBoard(
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "content", required = true) String content,
            @RequestParam(value = "img", required = false) MultipartFile multipartFile,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "category_id", required = true) Integer categoryId,
            @RequestParam(value = "user_id", required = true) String userId
    ) throws IOException {

        Board board = Board.builder()
                .title(title)
                .content(content)
                .type(type)
                .category(Category.builder().id(categoryId).build())
                .user(User.builder().id(userId).build())
                .build();

        boardService.imgSave(board, multipartFile);

        BoardAddServiceRequestDto boardAddServiceRequestDto = BoardAddServiceRequestDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .img(board.getImg())
                .type(board.getType())
                .category(board.getCategory())
                .user(board.getUser())
                .build();

        BoardServiceResponseDto boardServiceResponseDto = boardService.addBoard(boardAddServiceRequestDto);

        return ResponseEntity.ok(boardServiceResponseDto.toControllerDto());
    }

    // 게시글 상세 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardControllerResponseDto> findById(@PathVariable Integer id) {
        BoardServiceResponseDto boardServiceResponseDto = boardService.getBoard(id);
        return ResponseEntity.ok(boardServiceResponseDto.toControllerDto());
    }

    @PutMapping()
    public ResponseEntity<MessageDto> updateBoard(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "content", required = true) String content,
            @RequestParam(value = "img", required = false) MultipartFile multipartFile,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "category_id", required = true) Integer categoryId,
            @RequestParam(value = "user_id", required = true) String userId
    ) throws IOException {

        Board board = boardRepository.findById(id).orElseThrow();

        boardService.imgSave(board, multipartFile);

        BoardUpdateServiceRequestDto boardUpdateServiceRequestDto = BoardUpdateServiceRequestDto.builder()
                .id(board.getId())
                .title(title)
                .content(content)
                .img(board.getImg())
                .type(type)
                .category(Category.builder().id(categoryId).build())
                .user(User.builder().id(userId).build())
                .build();

        boardService.updateBoard(boardUpdateServiceRequestDto);

        return ResponseEntity.ok(new MessageDto("해당 게시글을 성공적으로 수정했습니다."));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteBoard(@PathVariable Integer id) throws IOException {
        Board board = boardRepository.findById(id).orElseThrow();

        boardService.deleteBoard(id);
        boardService.imgDelete(board);
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
