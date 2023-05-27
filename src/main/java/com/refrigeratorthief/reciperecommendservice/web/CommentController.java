package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentAddControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentAddControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentUpdateControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentAddServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentDeleteServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentUpdateServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.general.MessageDto;
import com.refrigeratorthief.reciperecommendservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping()
    public ResponseEntity<CommentAddControllerResponseDto> addComment(@RequestBody CommentAddControllerRequestDto commentAddControllerRequestDto) {
        CommentAddServiceResponseDto commentAddServiceResponseDto = commentService.addComment(commentAddControllerRequestDto.toServiceDto());
        return ResponseEntity.ok(commentAddServiceResponseDto.toControllerDto());
    }

    // 댓글 수정
    @PutMapping()
    public ResponseEntity<MessageDto> updateComment(@RequestBody CommentUpdateControllerDto commentUpdateControllerDto) {
        CommentUpdateServiceDto commentUpdateServiceDto = commentService.updateComment(commentUpdateControllerDto.toServiceDto());
        return ResponseEntity.ok(new MessageDto("해당 댓글을 성공적으로 수정했습니다."));
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteComment(@PathVariable Integer id) {
        CommentDeleteServiceDto commentDeleteServiceDto = commentService.deleteComment(id);
        return ResponseEntity.ok(new MessageDto("해당 댓글을 성공적으로 삭제했습니다."));
    }

    // 게시판에 해당하는 댓글들 조회
    @GetMapping("/{id}")
    public ResponseEntity<List<CommentServiceDto>> findCommentsByBoard(@PathVariable Integer id) {
        List<CommentServiceDto> commentServiceDtoList = commentService.findCommentsByBoard(id);
        return ResponseEntity.ok(commentServiceDtoList);
    }



}
