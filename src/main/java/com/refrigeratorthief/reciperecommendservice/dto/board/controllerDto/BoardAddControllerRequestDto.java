package com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardAddServiceRequestDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BoardAddControllerRequestDto {
    private String title;
    private String content;
    private String img;
    private String type;
    private Integer categoryId;
    private String userId;

    public BoardAddServiceRequestDto toServiceDto() {
        return BoardAddServiceRequestDto.builder()
                .title(title)
                .content(content)
                .img(img)
                .type(type)
                .category(Category.builder().id(categoryId).build())
                .user(User.builder().id(userId).build())
                .build();
    }
}
