package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.comment.Comment;
import com.refrigeratorthief.reciperecommendservice.domain.comment.CommentRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentAddServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentDeleteServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentUpdateServiceDto;
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
public class CommentService {

    @Autowired
    private final BoardRepository boardRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public CommentAddServiceResponseDto addComment(CommentAddServiceResponseDto commentAddServiceResponseDto) {

        User targetUser = userRepository.findUserById(commentAddServiceResponseDto.getUser())
                .orElseThrow(()->new CustomException("해당하는 id의 유저가 존재하지 않습니다."));
        Board targetBoard = boardRepository.findById(commentAddServiceResponseDto.getBoard())
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));

        Comment targetComment = Comment.builder()
                .id(commentAddServiceResponseDto.getId())
                .upperId(commentAddServiceResponseDto.getUpperId())
                .content(commentAddServiceResponseDto.getContent())
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .user(targetUser)
                .board(targetBoard)
                .build();

        commentRepository.save(targetComment);

        return CommentAddServiceResponseDto.builder()
                .id(targetComment.getId())
                .upperId(targetComment.getUpperId())
                .content(targetComment.getContent())
                .createdDateTime(targetComment.getCreatedDateTime())
                .updatedDateTime(targetComment.getUpdatedDateTime())
                .user(targetComment.getUser().getName())
                .board(targetComment.getBoard().getId())
                .build();
    }

    @Transactional
    public CommentUpdateServiceDto updateComment(CommentUpdateServiceDto commentUpdateServiceDto) {
        User targetUser = userRepository.findUserById(commentUpdateServiceDto.getUser())
                .orElseThrow(()->new CustomException("해당하는 id의 유저가 존재하지 않습니다."));
        Board targetBoard = boardRepository.findById(commentUpdateServiceDto.getBoard())
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));
        Comment targetComment = commentRepository.findById(commentUpdateServiceDto.getId())
                .orElseThrow(()->new CustomException("해당하는 댓글이 존재하지 않습니다."));

        Comment resultComment = Comment.builder()
                .id(targetComment.getId())
                .upperId(targetComment.getUpperId())
                .content(commentUpdateServiceDto.getContent())
                .createdDateTime(targetComment.getCreatedDateTime())
                .updatedDateTime(LocalDateTime.now())
                .user(targetUser)
                .board(targetBoard)
                .build();
        // 댓글 수정시 변경될 수 있는 건 내용 뿐임

        targetComment.updateComment(resultComment);

        return CommentUpdateServiceDto.builder()
                .id(resultComment.getId())
                .upperId(resultComment.getUpperId())
                .content(resultComment.getContent())
                .user(resultComment.getUser().getName())
                .board(resultComment.getBoard().getId())
                .build();
    }

    @Transactional
    public CommentDeleteServiceDto deleteComment(Integer id) {
        if (commentRepository.findById(id).isEmpty()) {
            throw new CustomException("해당하는 댓글이 존재하지 않습니다.");
        }

        Comment targetComment = commentRepository.findById(id)
                .orElseThrow(()->new CustomException("해당하는 댓글이 존재하지 않습니다."));

        commentRepository.delete(targetComment);

        return CommentDeleteServiceDto.builder()
                .id(targetComment.getId())
                .user(targetComment.getUser().getName())
                .board(targetComment.getBoard().getId())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentServiceDto> findCommentsByBoard(Integer id) {
        List<Comment> targetComments = commentRepository.findCommentsByBoard(id)
                .orElseThrow(()->new CustomException("해당하는 댓글이 존재하지 않습니다."));
        List<CommentServiceDto> dtoList = new ArrayList<>();

        for (Comment comments : targetComments) {
            CommentServiceDto dto = CommentServiceDto.builder()
                    .id(comments.getId())
                    .upperId(comments.getUpperId())
                    .content(comments.getContent())
                    .createdDateTime(comments.getCreatedDateTime())
                    .updatedDateTime(comments.getUpdatedDateTime())
                    .user(comments.getUser().getName())
                    .board(comments.getBoard().getId())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }

}
