package com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.comment.Comment;
import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentResponseControllerDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentServiceResponseDto {
    private Integer id;
    private Integer upperId;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String userName;
    private Integer boardId;

    public CommentServiceResponseDto (Comment comment) {
        id = comment.getId();
        upperId = comment.getUpperId();
        content = comment.getContent();
        createdDateTime = comment.getCreatedDateTime();
        updatedDateTime = comment.getUpdatedDateTime();
        userName = comment.getUser().getName();
        boardId = comment.getBoard().getId();
    }

    public CommentResponseControllerDto toControllerDto() {
        return CommentResponseControllerDto.builder()
                .id(id)
                .upperId(upperId)
                .content(content)
                .createdDateTime(createdDateTime)
                .updatedDateTime(updatedDateTime)
                .userName(userName)
                .boardId(boardId)
                .build();
    }
}
