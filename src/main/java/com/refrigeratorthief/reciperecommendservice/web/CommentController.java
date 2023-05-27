package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentAddControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentResponseControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentUpdateRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.general.MessageDto;
import com.refrigeratorthief.reciperecommendservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping()
    public ResponseEntity<CommentResponseControllerDto> addComment(@RequestBody CommentAddControllerRequestDto commentAddControllerRequestDto) {
        CommentServiceResponseDto commentServiceResponseDto = commentService.addComment(commentAddControllerRequestDto.toServiceDto());
        return ResponseEntity.ok(commentServiceResponseDto.toControllerDto());
    }

    // 댓글 수정
    @PutMapping()
    public ResponseEntity<MessageDto> updateComment(@RequestBody CommentUpdateRequestControllerDto commentUpdateRequestControllerDto) {
        commentService.updateComment(commentUpdateRequestControllerDto.toServiceDto());
        return ResponseEntity.ok(new MessageDto("해당 댓글을 성공적으로 수정했습니다."));
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(new MessageDto("해당 댓글을 성공적으로 삭제했습니다."));
    }

    // 게시판에 해당하는 댓글들 조회
    @GetMapping("/{id}")
    public ResponseEntity<List<CommentResponseControllerDto>> findCommentsByBoard(@PathVariable Integer id) {

        List<CommentResponseControllerDto> results;
        results = commentService.findCommentsByBoard(id)
                .stream()
                .map(CommentServiceResponseDto::toControllerDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

}
