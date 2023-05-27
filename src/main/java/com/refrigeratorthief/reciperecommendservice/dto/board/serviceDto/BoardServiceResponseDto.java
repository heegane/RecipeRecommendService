package com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardCategoryResponseControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardControllerResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardServiceResponseDto {
    private Integer id;
    private String title;
    private String content;
    private String img;
    private String type;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Category category;
    private User user;

    public BoardControllerResponseDto toControllerDto() {
        return BoardControllerResponseDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .img(img)
                .type(type)
                .createdDateTime(createdDateTime)
                .updatedDateTime(updatedDateTime)
                .categoryId(category.getId())
                .categoryName(category.getName())
                .userId(user.getId())
                .userName(user.getName())
                .build();
    }

    public BoardServiceResponseDto(Board board) {
        id = board.getId();
        title = board.getTitle();
        content = board.getContent();
        img = board.getImg();
        type = board.getType();
        createdDateTime = board.getCreatedDateTime();
        updatedDateTime = board.getUpdatedDateTime();
        category = board.getCategory();
        user = board.getUser();
    }

    public BoardCategoryResponseControllerDto toCategoryControllerDto() {
        return BoardCategoryResponseControllerDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .img(img)
                .type(type)
                .createdDateTime(createdDateTime)
                .updatedDateTime(updatedDateTime)
                .userId(user.getId())
                .userName(user.getName())
                .build();
    }
}
