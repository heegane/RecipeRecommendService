package com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardAddServiceRequestDto {
    private String title;
    private String content;
    private String img;
    private String type;
    private Category category;
    private User user;

    public Board toEntity(Category category, User user) {
        return Board.builder()
                .title(title)
                .content(content)
                .img(img)
                .type(type)
                .category(category)
                .user(user)
                .build();
    }
}
