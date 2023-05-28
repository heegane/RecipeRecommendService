package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.comment.Comment;
import com.refrigeratorthief.reciperecommendservice.domain.comment.CommentRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CommentService {

    @Autowired
    private final BoardRepository boardRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public CommentServiceResponseDto addComment(CommentAddServiceRequestDto commentAddServiceRequestDto) {

        User targetUser = userRepository.findUserById(commentAddServiceRequestDto.getUser().getId())
                .orElseThrow(()->new CustomException("해당하는 id의 유저가 존재하지 않습니다."));
        Board targetBoard = boardRepository.findById(commentAddServiceRequestDto.getBoard().getId())
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));

        Comment targetComment = Comment.builder()
                .upperId(commentAddServiceRequestDto.getUpperId())
                .content(commentAddServiceRequestDto.getContent())
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .user(targetUser)
                .board(targetBoard)
                .build();

        commentRepository.save(targetComment);

        return CommentServiceResponseDto.builder()
                .id(targetComment.getId())
                .upperId(targetComment.getUpperId())
                .content(targetComment.getContent())
                .createdDateTime(targetComment.getCreatedDateTime())
                .updatedDateTime(targetComment.getUpdatedDateTime())
                .userName(targetComment.getUser().getName())
                .boardId(targetComment.getBoard().getId())
                .build();
    }

    @Transactional
    public void updateComment(CommentUpdateServiceRequestDto commentUpdateServiceRequestDto) {

        Comment targetComment = commentRepository.findById(commentUpdateServiceRequestDto.getId())
                .orElseThrow(()->new CustomException("해당하는 댓글이 존재하지 않습니다."));

        if(!Objects.equals(targetComment.getUser().getId(), commentUpdateServiceRequestDto.getUser().getId())) {
            throw new CustomException("댓글 수정 권한이 없습니다.");
        }

        Comment resultComment = Comment.builder()
                .id(targetComment.getId())
                .upperId(targetComment.getUpperId())
                .content(commentUpdateServiceRequestDto.getContent())
                .createdDateTime(targetComment.getCreatedDateTime())
                .updatedDateTime(LocalDateTime.now())
                .user(targetComment.getUser())
                .board(targetComment.getBoard())
                .build();
        // 댓글 수정시 변경될 수 있는 건 내용 뿐임

        commentRepository.save(resultComment);
    }

    @Transactional
    public void deleteComment(Integer id) {

        Comment targetComment = commentRepository.findById(id)
                .orElseThrow(()->new CustomException("해당하는 댓글이 존재하지 않습니다."));

        commentRepository.delete(targetComment);
    }

    @Transactional(readOnly = true)
    public List<CommentServiceResponseDto> findCommentsByBoard(Integer id) {
        List<Comment> targetComments = commentRepository.findCommentsByBoard(id)
                .orElseThrow(()->new CustomException("해당하는 댓글이 존재하지 않습니다."));
        List<CommentServiceResponseDto> dtoList = new ArrayList<>();

        for (Comment comments : targetComments) {
            CommentServiceResponseDto dto = CommentServiceResponseDto.builder()
                    .id(comments.getId())
                    .upperId(comments.getUpperId())
                    .content(comments.getContent())
                    .createdDateTime(comments.getCreatedDateTime())
                    .updatedDateTime(comments.getUpdatedDateTime())
                    .userName(comments.getUser().getName())
                    .boardId(comments.getBoard().getId())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }

}
