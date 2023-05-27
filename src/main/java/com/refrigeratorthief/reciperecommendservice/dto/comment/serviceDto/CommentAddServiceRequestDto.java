package com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddServiceRequestDto {
    private Integer upperId;
    private String content;
    private User user;
    private Board board;
}
