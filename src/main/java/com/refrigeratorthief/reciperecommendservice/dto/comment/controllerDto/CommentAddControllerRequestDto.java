package com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto;

import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentAddServiceResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddControllerRequestDto {
    private Integer upperId;
    private String content;
    private String user;
    private Integer board;

    public CommentAddServiceResponseDto toServiceDto() {
        return CommentAddServiceResponseDto.builder()
                .upperId(upperId)
                .content(content)
                .user(user)
                .board(board)
                .build();
    }
}
