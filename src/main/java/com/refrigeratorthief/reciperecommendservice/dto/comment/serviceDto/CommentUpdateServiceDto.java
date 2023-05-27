package com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentUpdateControllerDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateServiceDto {
    private Integer id;
    private Integer upperId;
    private String content;
    private String user;
    private Integer board;

    public CommentUpdateControllerDto toControllerDto() {
        return CommentUpdateControllerDto.builder()
                .id(id)
                .upperId(upperId)
                .content(content)
                .user(user)
                .board(board)
                .build();
    }
}
