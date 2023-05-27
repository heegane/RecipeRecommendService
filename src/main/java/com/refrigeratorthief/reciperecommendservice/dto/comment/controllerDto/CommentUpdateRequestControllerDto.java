package com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentUpdateServiceRequestDto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommentUpdateRequestControllerDto {
    private Integer id;
    private String content;
    private String userId;

    public CommentUpdateServiceRequestDto toServiceDto() {
        return CommentUpdateServiceRequestDto.builder()
                .id(id)
                .content(content)
                .user(User.builder().id(userId).build())
                .build();
    }
}
