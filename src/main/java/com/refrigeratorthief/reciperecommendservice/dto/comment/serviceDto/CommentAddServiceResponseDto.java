package com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentAddControllerResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddServiceResponseDto {
    private Integer id;
    private Integer upperId;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String user;
    private Integer board;

    public CommentAddControllerResponseDto toControllerDto() {
        return CommentAddControllerResponseDto.builder()
                .id(id)
                .upperId(upperId)
                .content(content)
                .createdDateTime(createdDateTime)
                .updatedDateTime(updatedDateTime)
                .user(user)
                .board(board)
                .build();
    }
}
