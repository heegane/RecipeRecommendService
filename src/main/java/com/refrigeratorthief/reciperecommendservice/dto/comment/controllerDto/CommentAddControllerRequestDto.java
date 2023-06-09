package com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentAddServiceRequestDto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommentAddControllerRequestDto {
    private Integer upperId;
    private String content;
    private String userId;
    private Integer boardId;

    public CommentAddServiceRequestDto toServiceDto() {
        return CommentAddServiceRequestDto.builder()
                .upperId(upperId)
                .content(content)
                .user(User.builder().id(userId).build())
                .board(Board.builder().id(boardId).build())
                .build();
    }
}
