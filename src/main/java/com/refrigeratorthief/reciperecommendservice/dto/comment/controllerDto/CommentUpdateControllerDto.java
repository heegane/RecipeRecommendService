package com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto;

import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentUpdateServiceDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateControllerDto {
    private Integer id;
    private Integer upperId;
    private String content;
    private String user;
    private Integer board;

    public CommentUpdateServiceDto toServiceDto() {
        return CommentUpdateServiceDto.builder()
                .id(id)
                .upperId(upperId)
                .content(content)
                .user(user)
                .board(board)
                .build();
    }
}
