package com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto;

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
    private String user;
    private Integer board;

    public CommentResponseControllerDto toControllerDto() {
        return CommentResponseControllerDto.builder()
                .id(id)
                .upperId(upperId)
                .content(content)
                .createdDateTime(createdDateTime)
                .updatedDateTime(updatedDateTime)
                .userName(user)
                .boardId(board)
                .build();
    }
}
