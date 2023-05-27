package com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentResponseControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentUpdateRequestControllerDto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateServiceRequestDto {
    private Integer id;
    private String content;
    private User user;
}
